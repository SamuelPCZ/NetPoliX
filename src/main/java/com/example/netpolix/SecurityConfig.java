package com.example.netpolix;

import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/IniciarSesion", "/Registrarse", "/static/**", "/Imagenes/**", "/Styles.css", "/StylesInicioSesion.css", "/StylesRegistro.css").permitAll()
                        .requestMatchers("/usuarioPrincipal","referidosPuntos", "/consultarSaldo", "/ingresarSaldo").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/InicioSesion")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/usuarioPrincipal", true)
                        .failureUrl("/InicioSesion?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> {
            log.debug("Attempting to load user by email: " + email);
            Usuario usuario = userRepository.findByEmail(email);
            if (usuario == null) {
                log.debug("User not found: " + email);
                throw new UsernameNotFoundException("User not found");
            }
            log.debug("User found: " + usuario.getEmail());
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getContraseña())
                    .roles(usuario.getRol())
                    .build();
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider)
                .build();
    }
}