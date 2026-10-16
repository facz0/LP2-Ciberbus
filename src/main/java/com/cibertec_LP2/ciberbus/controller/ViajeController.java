package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeService viajeService;

    @PostMapping("/viajes/listar")
    public String listarViajes(Integer rutaId, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaViaje,
            Model model) {
        model.addAttribute("lst_viajes", viajeService.listarViajes(rutaId, fechaViaje));
        return "ventas/viaje";
    }

}
