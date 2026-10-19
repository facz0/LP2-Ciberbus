package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReservaController {

    private final ViajeService viajeService;

    @PostMapping("/reserva/pasajeros")
    public String listarPasajeros(
            @RequestParam("idViaje") Integer idViaje,
            @RequestParam("asientosSeleccionados") String asientosSeleccionados,
            @RequestParam("cantidadSeleccionados") Integer cantidadSeleccionados,
            @RequestParam("totalPagar") Double totalPagar,
            Model model) {

        Viaje viaje = viajeService.buscarPorId(idViaje);
        if (viaje == null) {
            return "redirect:/";
        }

        String[] listaAsientos = asientosSeleccionados.split(",");

        model.addAttribute("viaje", viaje);
        model.addAttribute("asientosSeleccionados", asientosSeleccionados);
        model.addAttribute("listaAsientos", listaAsientos);
        model.addAttribute("cantidadSeleccionados", cantidadSeleccionados);
        model.addAttribute("totalPagar", totalPagar);

        return "ventas/reserva";
    }

}
