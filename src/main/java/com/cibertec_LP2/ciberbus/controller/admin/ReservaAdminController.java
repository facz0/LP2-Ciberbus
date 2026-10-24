package com.cibertec_LP2.ciberbus.controller.admin;

import com.cibertec_LP2.ciberbus.dto.ReservaFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.Reserva;
import com.cibertec_LP2.ciberbus.service.ReservaService;
import com.cibertec_LP2.ciberbus.service.UsuarioService;
import com.cibertec_LP2.ciberbus.service.ViajeService;
import com.cibertec_LP2.ciberbus.util.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reservas")
public class ReservaAdminController {

    private final ReservaService reservaService;
    private final ViajeService viajeService;
    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(@ModelAttribute ReservaFilter filter, Model model) {
        model.addAttribute("lst_reservas", reservaService.search(filter));
        model.addAttribute("lst_viajes", viajeService.getAll());
        model.addAttribute("lst_usuarios", usuarioService.getAll());
        model.addAttribute("filter", filter);
        return "administrador/reserva_mant";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "redirect:/admin/reservas";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Reserva obtenerParaEditar(@PathVariable Integer id) {
        return reservaService.getOne(id);
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Reserva reserva, RedirectAttributes flash) {
        if (reserva.getIdReserva() != null && reserva.getIdReserva() <= 0) {
            reserva.setIdReserva(null);
        }
        if (reserva.getUsuario() == null || reserva.getUsuario().getIdUsuario() == null || reserva.getUsuario().getIdUsuario() <= 0) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Debe seleccionar un cliente/usuario registrado"));
            return "redirect:/admin/reservas";
        }
        ResultadoResponse response = reservaService.create(reserva);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/reservas";
    }

    @PostMapping({"/guardar", "/actualizar"})
    public String guardar(@ModelAttribute Reserva reserva, RedirectAttributes flash) {
        if (reserva.getIdReserva() != null && reserva.getIdReserva() <= 0) {
            reserva.setIdReserva(null);
        }
        if (reserva.getUsuario() == null || reserva.getUsuario().getIdUsuario() == null || reserva.getUsuario().getIdUsuario() <= 0) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Debe seleccionar un cliente/usuario registrado"));
            return "redirect:/admin/reservas";
        }
        ResultadoResponse response;
        if (reserva.getIdReserva() == null) {
            response = reservaService.create(reserva);
        } else {
            response = reservaService.update(reserva);
        }
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/reservas";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id, RedirectAttributes flash) {
        ResultadoResponse response = reservaService.delete(id);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/reservas";
    }

}
