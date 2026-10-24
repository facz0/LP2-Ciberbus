package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AutogestionController {

    private final ReservaService reservaService;

    @GetMapping("/autogestion")
    public String autogestion(Model model) {
        return "ventas/autogestion";
    }

    @GetMapping("/autogestion/buscar")
    public String buscarReserva(@RequestParam(name = "codigoReserva", required = false) String codigoReserva, Model model) {
        if (codigoReserva != null && !codigoReserva.trim().isEmpty()) {
            model.addAttribute("lst_reservas", reservaService.buscarPorCodigo(codigoReserva));
            model.addAttribute("codigoBuscado", codigoReserva.trim());
        } else {
            model.addAttribute("lst_reservas", List.of());
            model.addAttribute("codigoBuscado", "");
        }
        return "ventas/autogestion";
    }

}
