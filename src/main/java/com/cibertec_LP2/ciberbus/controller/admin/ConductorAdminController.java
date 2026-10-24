package com.cibertec_LP2.ciberbus.controller.admin;

import com.cibertec_LP2.ciberbus.dto.ConductorFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.Conductor;
import com.cibertec_LP2.ciberbus.service.ConductorService;
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
@RequestMapping("/admin/conductores")
public class ConductorAdminController {

    private final ConductorService conductorService;

    @GetMapping
    public String listar(@ModelAttribute ConductorFilter filter, Model model) {
        model.addAttribute("lst_conductores", conductorService.search(filter));
        model.addAttribute("filter", filter);
        return "administrador/conductores_mant";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "redirect:/admin/conductores";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Conductor obtenerParaEditar(@PathVariable Integer id) {
        return conductorService.getOne(id);
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Conductor conductor, RedirectAttributes flash) {
        if (conductor.getConductorId() != null && conductor.getConductorId() <= 0) {
            conductor.setConductorId(null);
        }
        ResultadoResponse response = conductorService.create(conductor);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/conductores";
    }

    @PostMapping({"/guardar", "/actualizar"})
    public String guardar(@ModelAttribute Conductor conductor, RedirectAttributes flash) {
        if (conductor.getConductorId() != null && conductor.getConductorId() <= 0) {
            conductor.setConductorId(null);
        }
        ResultadoResponse response;
        if (conductor.getConductorId() == null) {
            response = conductorService.create(conductor);
        } else {
            response = conductorService.update(conductor);
        }
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/conductores";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id, RedirectAttributes flash) {
        ResultadoResponse response = conductorService.delete(id);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/conductores";
    }

}
