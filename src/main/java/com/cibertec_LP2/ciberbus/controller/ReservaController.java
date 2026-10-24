package com.cibertec_LP2.ciberbus.controller;

import com.cibertec_LP2.ciberbus.dto.CompraRequest;
import com.cibertec_LP2.ciberbus.dto.PasajeroCompraDto;
import com.cibertec_LP2.ciberbus.model.DetalleReserva;
import com.cibertec_LP2.ciberbus.model.Reserva;
import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.repository.DetalleReservaRepository;
import com.cibertec_LP2.ciberbus.service.ReservaService;
import com.cibertec_LP2.ciberbus.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReservaController {

    private final ViajeService viajeService;
    private final ReservaService reservaService;
    private final DetalleReservaRepository detalleReservaRepository;

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

    @PostMapping("/reserva/pagar")
    public String pagarReserva(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        try {
            Integer idViaje = Integer.parseInt(params.get("idViaje"));
            int cantidadSeleccionados = Integer.parseInt(params.getOrDefault("cantidadSeleccionados", "1"));
            BigDecimal totalPagar = new BigDecimal(params.getOrDefault("totalPagar", "0"));

            List<PasajeroCompraDto> pasajeros = new ArrayList<>();
            for (int i = 0; i < cantidadSeleccionados; i++) {
                String asientoStr = params.get("asiento_" + i);
                if (asientoStr == null) {
                    continue;
                }
                Integer nroAsiento = Integer.parseInt(asientoStr);
                String tipoDoc = params.getOrDefault("tipoDocumento_" + i, "DNI");
                String nroDoc = params.getOrDefault("nroDocumento_" + i, "");
                String nombre = params.getOrDefault("nombre_" + i, "");
                String apellido = params.getOrDefault("apellido_" + i, "");
                String correo = params.getOrDefault("correo_" + i, "");
                String telefono = params.getOrDefault("telefono_" + i, "");
                String fechaNacStr = params.get("fechaNacimiento_" + i);
                LocalDate fechaNac = (fechaNacStr != null && !fechaNacStr.isBlank()) ? LocalDate.parse(fechaNacStr) : LocalDate.of(2000, 1, 1);
                String nacionalidad = params.getOrDefault("nacionalidad_" + i, "Peruana");
                String genero = params.getOrDefault("genero_" + i, "Masculino");

                pasajeros.add(new PasajeroCompraDto(
                        nroAsiento, tipoDoc, nroDoc, nombre, apellido, correo, telefono, fechaNac, nacionalidad, genero
                ));
            }

            CompraRequest request = new CompraRequest(idViaje, totalPagar, "TARJETA", pasajeros);
            Reserva reserva = reservaService.procesarCompra(request);

            return "redirect:/reserva/confirmacion/" + reserva.getCodigoReserva();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la reserva: " + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/reserva/confirmacion/{codigoReserva}")
    public String verConfirmacion(@PathVariable("codigoReserva") String codigoReserva, Model model) {
        Reserva reserva = reservaService.buscarPorCodigoUnico(codigoReserva);
        if (reserva == null) {
            return "redirect:/";
        }

        List<DetalleReserva> detalles = detalleReservaRepository.findByReservaIdReserva(reserva.getIdReserva());

        model.addAttribute("reserva", reserva);
        model.addAttribute("detalles", detalles);
        model.addAttribute("viaje", reserva.getViaje());

        return "ventas/confirmacion";
    }

}
