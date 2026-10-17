package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.ViajeAsiento;
import com.cibertec_LP2.ciberbus.repository.ViajeAsientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeAsientoService {

    private final ViajeAsientoRepository viajeAsientoRepository;

    public List<Integer> obtenerAsientosVendidos(Integer idViaje) {
        List<ViajeAsiento> lista = viajeAsientoRepository.findByViajeIdViajeAndEstado(idViaje, 1);
        List<Integer> asientosVendidos = new ArrayList<>();
        for (ViajeAsiento va : lista) {
            asientosVendidos.add(va.getNroAsiento());
        }
        return asientosVendidos;
    }

}
