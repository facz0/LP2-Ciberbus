package com.cibertec_LP2.ciberbus.controller.admin;

import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.dto.RutaFilter;
import com.cibertec_LP2.ciberbus.model.Ruta;
import com.cibertec_LP2.ciberbus.service.CiudadService;
import com.cibertec_LP2.ciberbus.service.RutaService;
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
@RequestMapping("/admin/rutas")
public class RutaAdminController {

    private final RutaService rutaService;
    private final CiudadService ciudadService;

    @GetMapping
    public String listar(@ModelAttribute RutaFilter filter, Model model) {
        model.addAttribute("lst_rutas", rutaService.search(filter));
        model.addAttribute("lst_ciudades", ciudadService.listarTodas());
        model.addAttribute("filter", filter);
        return "administrador/ruta_mant";
    }

    @GetMapping("/nuevo")
    public String nuevo() {
        return "redirect:/admin/rutas";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Ruta obtenerParaEditar(@PathVariable Integer id) {
        return rutaService.getOne(id);
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Ruta ruta, RedirectAttributes flash) {
        if (ruta.getIdRuta() != null && ruta.getIdRuta() <= 0) {
            ruta.setIdRuta(null);
        }
        ResultadoResponse response = rutaService.create(ruta);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/rutas";
    }

    @PostMapping({"/guardar", "/actualizar"})
    public String guardar(@ModelAttribute Ruta ruta, RedirectAttributes flash) {
        if (ruta.getIdRuta() != null && ruta.getIdRuta() <= 0) {
            ruta.setIdRuta(null);
        }
        ResultadoResponse response;
        if (ruta.getIdRuta() == null) {
            response = rutaService.create(ruta);
        } else {
            response = rutaService.update(ruta);
        }
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/rutas";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id, RedirectAttributes flash) {
        ResultadoResponse response = rutaService.delete(id);
        if (response.success()) {
            flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 3000));
        } else {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje()));
        }
        return "redirect:/admin/rutas";
    }

}
