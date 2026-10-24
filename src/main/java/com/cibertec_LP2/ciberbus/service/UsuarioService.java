package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.Usuario;
import com.cibertec_LP2.ciberbus.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getOne(Integer id) {
        return (id != null) ? usuarioRepository.findById(id).orElse(null) : null;
    }

    public Usuario autenticar(String correo, String clave) {
        if (correo == null || correo.isBlank() || clave == null || clave.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo.trim()).orElse(null);
        if (usuario == null) {
            return null;
        }

        // Si el usuario está inactivo (0 = inactivo)
        if (usuario.getEstado() != null && usuario.getEstado() == 0) {
            return null;
        }

        String claveIngresada = clave.trim();
        String hashSha256 = sha256(claveIngresada);

        // Valida contra hash SHA-256 o texto plano
        if (hashSha256.equalsIgnoreCase(usuario.getClave()) || claveIngresada.equals(usuario.getClave())) {
            return usuario;
        }

        return null;
    }

    private String sha256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "";
        }
    }

}
