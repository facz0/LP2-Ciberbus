package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.Ruta;
import com.cibertec_LP2.ciberbus.repository.RutaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepository;

    public List<Ruta> getDestinosPorOrigen(Integer idCiudadOrigen) {
        return rutaRepository.findDestinosPorOrigen(idCiudadOrigen);
    }

}
