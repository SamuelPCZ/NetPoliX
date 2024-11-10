package com.example.netpolix.Controller;

import com.example.netpolix.Controller.AuthController;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        Model model = new ConcurrentModel();
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String viewName = authController.registerUser("test@example.com", "password", "testUser", null, model);
        assertEquals("redirect:/InicioSesion", viewName);
        verify(userRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testShowLoginForm() {
        Model model = new ConcurrentModel();
        String viewName = authController.showLoginForm(model);
        assertEquals("plantillas/InicioSesion", viewName);
    }
}
