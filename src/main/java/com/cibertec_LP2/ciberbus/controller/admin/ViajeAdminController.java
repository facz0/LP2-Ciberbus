package com.cibertec_LP2.ciberbus.controller.admin;

import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.dto.ViajeFilter;
import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.service.BusService;
import com.cibertec_LP2.ciberbus.service.ConductorService;
import com.cibertec_LP2.ciberbus.service.RutaService;
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
@RequestMapping("/admin/viajes")
public class ViajeAdminController {

    private final ViajeService viajeService;
    private final RutaService rutaService;
    private final BusService busService;
    private final ConductorService conductorService;

    @GetMapping
    public String listar(@ModelAttribute ViajeFilter filter, Model model) {
        model.addAttribute("lst_viajes", viajeService.search(filter));
        model.addAttribute("lst_rutas", rutaService.getAll());
        model.addAttribute("lst_buses", busService.getAll());
        model.addAttribute("lst_conductores", conductorService.getAll());
        model.addAttribute("filter", filter);
        return "administrador/viaje_mant";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "redirect:/admin/viajes";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Viaje obtenerParaEditar(@PathVariable Integer id) {
        return viajeService.getOne(id);
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Viaje viaje, RedirectAttributes flash) {
        if (viaje.getIdViaje() != null && viaje.getIdViaje() <= 0) {
            viaje.setIdViaje(null);
        }
        if (viaje.getCopiloto() != null && (viaje.getCopiloto().getConductorId() == null || viaje.getCopiloto().getConductorId() <= 0)) {
            viaje.setCopiloto(null);
        }
        ResultadoResponse response = viajeService.create(viaje);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/viajes";
    }

    @PostMapping({"/guardar", "/actualizar"})
    public String guardar(@ModelAttribute Viaje viaje, RedirectAttributes flash) {
        if (viaje.getIdViaje() != null && viaje.getIdViaje() <= 0) {
            viaje.setIdViaje(null);
        }
        if (viaje.getCopiloto() != null && (viaje.getCopiloto().getConductorId() == null || viaje.getCopiloto().getConductorId() <= 0)) {
            viaje.setCopiloto(null);
        }
        ResultadoResponse response;
        if (viaje.getIdViaje() == null) {
            response = viajeService.create(viaje);
        } else {
            response = viajeService.update(viaje);
        }
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/viajes";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id, RedirectAttributes flash) {
        ResultadoResponse response = viajeService.delete(id);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/viajes";
    }

}
