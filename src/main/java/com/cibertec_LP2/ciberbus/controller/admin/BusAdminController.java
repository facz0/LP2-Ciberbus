package com.cibertec_LP2.ciberbus.controller.admin;

import com.cibertec_LP2.ciberbus.dto.BusFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.Bus;
import com.cibertec_LP2.ciberbus.service.BusService;
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
@RequestMapping("/admin/buses")
public class BusAdminController {

    private final BusService busService;

    @GetMapping
    public String listar(@ModelAttribute BusFilter filter, Model model) {
        model.addAttribute("lst_buses", busService.search(filter));
        model.addAttribute("filter", filter);
        return "administrador/buses_mant";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "redirect:/admin/buses";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Bus obtenerParaEditar(@PathVariable Integer id) {
        return busService.getOne(id);
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Bus bus, RedirectAttributes flash) {
        if (bus.getIdBus() != null && bus.getIdBus() <= 0) {
            bus.setIdBus(null);
        }
        ResultadoResponse response = busService.create(bus);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/buses";
    }

    @PostMapping({"/guardar", "/actualizar"})
    public String guardar(@ModelAttribute Bus bus, RedirectAttributes flash) {
        if (bus.getIdBus() != null && bus.getIdBus() <= 0) {
            bus.setIdBus(null);
        }
        ResultadoResponse response;
        if (bus.getIdBus() == null) {
            response = busService.create(bus);
        } else {
            response = busService.update(bus);
        }
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/buses";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id, RedirectAttributes flash) {
        ResultadoResponse response = busService.delete(id);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/buses";
    }

}
