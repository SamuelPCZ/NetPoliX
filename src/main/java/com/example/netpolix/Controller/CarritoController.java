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

import java.security.Principal;
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

    @GetMapping("/carrito")
    @Transactional
    public String verCarrito(Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<CarritoItems> carritoItems = carritoItemsRepository.findByIdUsuario(usuario.getId());

        // Fetch prices and names for each item
        List<CarritoItemDTO> carritoItemDTOs = carritoItems.stream().map(item -> {
            Video video = videoRepository.findById(item.getIsan()).orElse(null);
            String nombreVideo = video != null ? video.getTitulo() : "Unknown";
            Double precio = video != null ? video.getPrecio() : 0.0;
            return new CarritoItemDTO(item, precio, nombreVideo);
        }).collect(Collectors.toList());

        // Calculate total value considering the quantity of each item
        double totalValue = carritoItemDTOs.stream()
                .mapToDouble(itemDTO -> itemDTO.getPrecio() * itemDTO.getItem().getCantidad())
                .sum();

        model.addAttribute("carritoItems", carritoItemDTOs);
        model.addAttribute("totalValue", totalValue);
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
            return new CarritoItemDTO(item, precio, nombreVideo);
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

    // DTO class to hold CarritoItems and price
    public static class CarritoItemDTO {
        private CarritoItems item;
        private Double precio;
        private String nombreVideo;

        public CarritoItemDTO(CarritoItems item, Double precio, String nombreVideo) {
            this.item = item;
            this.precio = precio;
            this.nombreVideo = nombreVideo;
        }

        public CarritoItems getItem() {
            return item;
        }

        public Double getPrecio() {
            return precio;
        }

        public String getNombreVideo() {
            return nombreVideo;
        }
    }
}