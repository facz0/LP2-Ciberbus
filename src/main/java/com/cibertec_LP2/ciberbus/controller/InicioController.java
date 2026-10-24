package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.service.CiudadService;
import com.cibertec_LP2.ciberbus.service.RutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class InicioController {

    private final CiudadService ciudadService;
    private final RutaService rutaService;
    
  
    @GetMapping({"/", "/inicio"})
    public String listar(Model model) {
        model.addAttribute("lst_ciudadesOrigen", ciudadService.getAll());
        return "index";
    }

    @PostMapping("/inicio/buscarDestinos")
    public String buscarDestinos(Integer origenId, String fechaViaje, Model model) {
        model.addAttribute("lst_ciudadesOrigen", ciudadService.getAll());
        model.addAttribute("origenId", origenId);
        model.addAttribute("fechaViaje", fechaViaje);

        if (origenId != null) {
            model.addAttribute("lst_ciudadesDestino", rutaService.getDestinosPorOrigen(origenId));
        }

        return "index";
    }

}
