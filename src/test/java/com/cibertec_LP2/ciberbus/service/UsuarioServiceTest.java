package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.Usuario;
import com.cibertec_LP2.ciberbus.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Debe autenticar correctamente con hash SHA-256")
    void autenticar_ConHashSha256_Exitoso() {
        Usuario admin = new Usuario();
        admin.setIdUsuario(1);
        admin.setNombre("Administrador");
        admin.setCorreo("admin@ciberbus.com");
        // Hash de admin123
        admin.setClave("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
        admin.setRol("ADMIN");
        admin.setEstado(1);

        when(usuarioRepository.findByCorreo("admin@ciberbus.com")).thenReturn(Optional.of(admin));

        Usuario autenticado = usuarioService.autenticar("admin@ciberbus.com", "admin123");

        assertNotNull(autenticado);
        assertEquals("Administrador", autenticado.getNombre());
        assertEquals("ADMIN", autenticado.getRol());
    }

    @Test
    @DisplayName("Debe fallar la autenticación si la clave es incorrecta")
    void autenticar_ClaveIncorrecta_RetornaNull() {
        Usuario admin = new Usuario();
        admin.setCorreo("admin@ciberbus.com");
        admin.setClave("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
        admin.setEstado(1);

        when(usuarioRepository.findByCorreo("admin@ciberbus.com")).thenReturn(Optional.of(admin));

        Usuario autenticado = usuarioService.autenticar("admin@ciberbus.com", "clave_cualquiera");

        assertNull(autenticado);
    }

    @Test
    @DisplayName("Debe fallar si el usuario está inactivo")
    void autenticar_UsuarioInactivo_RetornaNull() {
        Usuario inactivo = new Usuario();
        inactivo.setCorreo("bloqueado@ciberbus.com");
        inactivo.setClave("123456");
        inactivo.setEstado(0); // Inactivo

        when(usuarioRepository.findByCorreo("bloqueado@ciberbus.com")).thenReturn(Optional.of(inactivo));

        Usuario autenticado = usuarioService.autenticar("bloqueado@ciberbus.com", "123456");

        assertNull(autenticado);
    }

}
