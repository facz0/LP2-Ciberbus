package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.service.ViajeAsientoService;
import com.cibertec_LP2.ciberbus.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeService viajeService;
    private final ViajeAsientoService viajeAsientoService;

    @PostMapping("/viajes/listar")
    public String listarViajes(
            Integer rutaId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaViaje,
            Model model) {
        model.addAttribute("lst_viajes", viajeService.listarViajes(rutaId, fechaViaje));
        return "ventas/viaje";
    }

    @GetMapping("/viaje/asientos")
    public String verAsientos(@RequestParam("idViaje") Integer idViaje, Model model) {
        Viaje viaje = viajeService.buscarPorId(idViaje);
        if(viaje == null){
            return "redirect:/";
        }
        model.addAttribute("viaje", viaje); //contiene el viaje
        model.addAttribute("asientosVendidos", viajeAsientoService.obtenerAsientosVendidos(idViaje));//contiene los vendidos
        return "ventas/asiento";
    }

}
