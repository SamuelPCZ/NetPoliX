package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsuarioController usuarioController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidarDatosRegistro() {
        String email = "test@example.com";
        String contraseña = "password";
        String nombreUsuario = "testUser";
        assertNotNull(email);
        assertFalse(email.isEmpty());
        assertNotNull(contraseña);
        assertFalse(contraseña.isEmpty());
        assertNotNull(nombreUsuario);
        assertFalse(nombreUsuario.isEmpty());
    }

    @Test
    public void testLogin() {
        String email = "test@example.com";
        String password = "password";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setContraseña(passwordEncoder.encode(password));

        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(passwordEncoder.matches(password, usuario.getContraseña())).thenReturn(true);

        String result = authController.showLoginForm(model);
        assertNotNull(result);
    }

    @Test
    public void testCodificacionContraseña() {
        String contraseña = "password";
        String contraseñaCodificada = "encodedPassword";

        when(passwordEncoder.encode(contraseña)).thenReturn(contraseñaCodificada);

        String result = passwordEncoder.encode(contraseña);
        assertNotNull(result);
        assertEquals(contraseñaCodificada, result);
    }
}
