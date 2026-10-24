package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.ConductorFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.Conductor;
import com.cibertec_LP2.ciberbus.repository.ConductorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConductorService {

    private final ConductorRepository conductorRepository;

    public List<Conductor> getAll() {
        return conductorRepository.findAllByOrderByNombreAsc();
    }

    public List<Conductor> search(ConductorFilter filter) {
        String dni = (filter != null && filter.dni() != null) ? filter.dni().trim() : null;
        return conductorRepository.findByFilters(dni);
    }

    public Conductor getOne(Integer id) {
        return conductorRepository.findById(id).orElse(null);
    }

    @Transactional
    public ResultadoResponse create(Conductor conductor) {
        try {
            var registrado = conductorRepository.save(conductor);
            var mensaje = String.format("Conductor con Id %d registrado exitosamente", registrado.getConductorId());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar el conductor");
        }
    }

    @Transactional
    public ResultadoResponse update(Conductor conductor) {
        try {
            var actualizado = conductorRepository.save(conductor);
            var mensaje = String.format("Conductor con Id %d actualizado exitosamente", actualizado.getConductorId());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al actualizar el conductor");
        }
    }

    @Transactional
    public ResultadoResponse delete(Integer id) {
        try {
            conductorRepository.deleteById(id);
            return new ResultadoResponse(true, String.format("Conductor con Id %d eliminado", id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al eliminar el conductor");
        }
    }

    public Conductor guardar(Conductor conductor) {
        return conductorRepository.save(conductor);
    }

    public void eliminar(Integer id) {
        conductorRepository.deleteById(id);
    }

}
