package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.model.Usuario;
import com.cibertec_LP2.ciberbus.service.UsuarioService;
import com.cibertec_LP2.ciberbus.util.Alert;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam(value = "correo", required = false) String correo,
                                @RequestParam(value = "clave", required = false) String clave,
                                HttpSession session,
                                RedirectAttributes flash) {

        Usuario usuario = usuarioService.autenticar(correo, clave);

        if (usuario == null) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Correo o contraseña incorrectos"));
            return "redirect:/login";
        }

        // Guardar datos en la sesión HTTP
        session.setAttribute("usuario", usuario);
        String nombreCompleto = usuario.getNombre() + (usuario.getApellido() != null ? " " + usuario.getApellido() : "");
        session.setAttribute("nombreUsuario", nombreCompleto);
        session.setAttribute("rolUsuario", usuario.getRol());

        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            flash.addFlashAttribute("alert", Alert.sweetToast("¡Bienvenido, " + usuario.getNombre() + "!", "success", 3000));
            return "redirect:/admin/dashboard";
        } else {
            flash.addFlashAttribute("alert", Alert.sweetToast("¡Bienvenido, " + usuario.getNombre() + "!", "success", 3000));
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes flash) {
        session.invalidate();
        flash.addFlashAttribute("alert", Alert.sweetToast("Sesión cerrada correctamente", "info", 3000));
        return "redirect:/login";
    }

}
