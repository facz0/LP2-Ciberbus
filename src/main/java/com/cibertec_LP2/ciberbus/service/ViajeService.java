package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.dto.ViajeFilter;
import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.repository.ViajeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public List<Viaje> search(ViajeFilter filter) {
        Integer idRuta = (filter != null) ? filter.idRuta() : null;
        return viajeRepository.findByFilters(idRuta);
    }

    public List<Viaje> listarViajes(Integer idRuta, LocalDate fechaSalida) {
        return viajeRepository.findByRutaIdRutaAndFechaSalida(idRuta, fechaSalida);
    }

    public Viaje buscarPorId(Integer idViaje) {
        return viajeRepository.findById(idViaje).orElse(null);
    }

    public Viaje getOne(Integer id) {
        return buscarPorId(id);
    }

    @Transactional
    public ResultadoResponse create(Viaje viaje) {
        try {
            var registrado = viajeRepository.save(viaje);
            var mensaje = String.format("Viaje con Id %d programado exitosamente", registrado.getIdViaje());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al programar el viaje");
        }
    }

    @Transactional
    public ResultadoResponse update(Viaje viaje) {
        try {
            var actualizado = viajeRepository.save(viaje);
            var mensaje = String.format("Viaje con Id %d actualizado exitosamente", actualizado.getIdViaje());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al actualizar el viaje");
        }
    }

    @Transactional
    public ResultadoResponse delete(Integer id) {
        try {
            viajeRepository.deleteById(id);
            return new ResultadoResponse(true, String.format("Viaje con Id %d eliminado", id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al eliminar el viaje");
        }
    }

    public Viaje guardar(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public void eliminar(Integer id) {
        viajeRepository.deleteById(id);
    }

}
