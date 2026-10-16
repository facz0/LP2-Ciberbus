package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.model.Ciudad;
import com.cibertec_LP2.ciberbus.repository.CiudadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadService {

    private final CiudadRepository ciudadRepository;

    public List<Ciudad> getAll(){
        return ciudadRepository.findCiudadesconRuta();
    }

}
