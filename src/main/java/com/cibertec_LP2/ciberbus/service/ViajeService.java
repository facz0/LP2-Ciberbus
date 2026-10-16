package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public List<Viaje> listarViajes(Integer idRuta, LocalDate fechaSalida) {
        return viajeRepository.findByRutaIdRutaAndFechaSalida(idRuta, fechaSalida);
    }

}
