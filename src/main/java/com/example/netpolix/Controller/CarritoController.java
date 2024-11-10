package com.example.netpolix.Controller;

import com.example.netpolix.Repository.*;
import com.example.netpolix.model.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.DecimalFormat;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarritoController {

    @Autowired
    private CarritoItemsRepository carritoItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @GetMapping("/carrito")
    @Transactional
    public String verCarrito(Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<CarritoItems> carritoItems = carritoItemsRepository.findByIdUsuario(usuario.getId());

        // Fetch prices and names for each item
        List<CarritoItemDTO> carritoItemDTOs = carritoItems.stream().map(item -> {
            Video video = videoRepository.findById(item.getIsan()).orElse(null);
            String nombreVideo = video != null ? video.getTitulo() : "Unknown";
            double precio = video != null ? video.getPrecio() : 0.0;
            return new CarritoItemDTO(item, nombreVideo, precio);
        }).collect(Collectors.toList());

        // Calculate subtotal
        double subtotal = carritoItemDTOs.stream()
                .mapToDouble(item -> item.getPrecio() * item.getItem().getCantidad())
                .sum();

        // Apply promotions and calculate total value
        LocalDateTime now = LocalDateTime.now();
        List<Promocion> promociones = promocionRepository.findByFechaInicioBeforeAndFechaFinAfter(now, now);
        double totalValue = subtotal;
        for (Promocion promocion : promociones) {
            totalValue -= totalValue * (promocion.getDescuento() / 100);
        }

        // Format the total value
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedTotalValue = decimalFormat.format(totalValue);

        model.addAttribute("carritoItems", carritoItemDTOs);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalValue", formattedTotalValue);
        return "plantillas/carrito";
    }

    @PostMapping("/eliminarDelCarrito")
    @Transactional
    public String eliminarDelCarrito(@RequestParam("idItem") Integer idItem) {
        carritoItemsRepository.deleteById(idItem);
        return "redirect:/carrito";
    }

    @PostMapping("/agregarAlCarrito")
    @Transactional
    public String agregarAlCarrito(@RequestParam("isan") int isan, Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        CarritoItems item = carritoItemsRepository.findByIdUsuarioAndIsan(usuario.getId().intValue(), isan);

        if (item != null) {
            item.setCantidad(item.getCantidad() + 1); // Update the quantity
        } else {
            item = new CarritoItems();
            item.setIdUsuario(usuario.getId().intValue());
            item.setIsan(isan);
            item.setCantidad(1); // Set initial quantity
            item.setTipo("video"); // Assuming the type is video
        }

        carritoItemsRepository.save(item);
        model.addAttribute("mensaje", "Video agregado al carrito.");
        return "redirect:/buscarVideos";
    }

    @PostMapping("/realizarCompra")
    @Transactional
    public String realizarCompra(Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<CarritoItems> carritoItems = carritoItemsRepository.findByIdUsuario(usuario.getId());

        // Fetch prices for each item
        List<CarritoItemDTO> carritoItemDTOs = carritoItems.stream().map(item -> {
            Video video = videoRepository.findById(item.getIsan()).orElse(null);
            double precio = video != null ? video.getPrecio() : 0.0;
            String nombreVideo = video != null ? video.getTitulo() : "Unknown";
            return new CarritoItemDTO(item, nombreVideo, precio);
        }).collect(Collectors.toList());

        // Calculate total value considering the quantity of each item
        double totalValue = carritoItemDTOs.stream()
                .mapToDouble(itemDTO -> itemDTO.getPrecio() * itemDTO.getItem().getCantidad())
                .sum();

        if (usuario.getSaldo() < totalValue) {
            model.addAttribute("mensaje", "Saldo insuficiente para completar la compra.");
            return "redirect:/carrito";
        }

        // Deduct total value from user's balance
        usuario.setSaldo(usuario.getSaldo() - totalValue);
        // Add points to user's account
        int puntosGanados = (int) (totalValue / 100) * 10;
        usuario.setPuntos(usuario.getPuntos() + puntosGanados);
        userRepository.save(usuario);

        // Save transaction
        Transaccion transaccion = new Transaccion();
        transaccion.setIdUsuario(usuario.getId());
        transaccion.setTotal(totalValue);
        transaccionRepository.save(transaccion);

        // Save purchase history and update inventory
        for (CarritoItemDTO itemDTO : carritoItemDTOs) {
            Historial historial = new Historial();
            historial.setIdUsuario(usuario.getId());
            historial.setIsan(itemDTO.getItem().getIsan());
            historial.setIdTransaccion(transaccion.getIdTransaccion());
            historial.setTipoTransaccion("compra");
            historial.setCantidad(itemDTO.getItem().getCantidad()); // Set the quantity
            historialRepository.save(historial);

            // Update inventory
            Inventario inventario = inventarioRepository.findByIdUsuarioAndIsan(usuario.getId(), itemDTO.getItem().getIsan());
            if (inventario != null) {
                inventario.setCantidad(inventario.getCantidad() + itemDTO.getItem().getCantidad());
            } else {
                inventario = new Inventario();
                inventario.setIdUsuario(usuario.getId());
                inventario.setIsan(itemDTO.getItem().getIsan());
                inventario.setCantidad(itemDTO.getItem().getCantidad());
            }
            inventarioRepository.save(inventario);
        }

        // Clear the cart
        carritoItemsRepository.deleteAll(carritoItems);

        model.addAttribute("mensaje", "Compra realizada con éxito.");
        return "redirect:/carrito";
    }

    public static class CarritoItemDTO {
        private CarritoItems item;
        private String nombreVideo;
        private double precio;

        public CarritoItemDTO(CarritoItems item, String nombreVideo, double precio) {
            this.item = item;
            this.nombreVideo = nombreVideo;
            this.precio = precio;
        }

        public CarritoItems getItem() {
            return item;
        }

        public String getNombreVideo() {
            return nombreVideo;
        }

        public double getPrecio() {
            return precio;
        }
    }
}