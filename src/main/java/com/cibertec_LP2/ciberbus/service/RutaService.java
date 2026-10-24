package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.dto.RutaFilter;
import com.cibertec_LP2.ciberbus.model.Ruta;
import com.cibertec_LP2.ciberbus.repository.RutaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepository;

    public List<Ruta> getAll() {
        return rutaRepository.findAll();
    }

    public List<Ruta> search(RutaFilter filter) {
        Integer idCiudadOrigen = (filter != null) ? filter.idCiudadOrigen() : null;
        return rutaRepository.findByFilters(idCiudadOrigen);
    }

    public Ruta getOne(Integer id) {
        return rutaRepository.findById(id).orElse(null);
    }

    public List<Ruta> getDestinosPorOrigen(Integer idCiudadOrigen) {
        return rutaRepository.findDestinosPorOrigen(idCiudadOrigen);
    }

    @Transactional
    public ResultadoResponse create(Ruta ruta) {
        try {
            var registrado = rutaRepository.save(ruta);
            var mensaje = String.format("Ruta con Id %d registrada exitosamente", registrado.getIdRuta());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar la ruta");
        }
    }

    @Transactional
    public ResultadoResponse update(Ruta ruta) {
        try {
            var actualizado = rutaRepository.save(ruta);
            var mensaje = String.format("Ruta con Id %d actualizada exitosamente", actualizado.getIdRuta());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al actualizar la ruta");
        }
    }

    @Transactional
    public ResultadoResponse delete(Integer id) {
        try {
            rutaRepository.deleteById(id);
            return new ResultadoResponse(true, String.format("Ruta con Id %d eliminada", id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al eliminar la ruta");
        }
    }

    public Ruta guardar(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    public void eliminar(Integer id) {
        rutaRepository.deleteById(id);
    }

}
