package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.BusFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.Bus;
import com.cibertec_LP2.ciberbus.repository.BusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    public List<Bus> search(BusFilter filter) {
        String tipo = (filter != null && filter.tipo() != null && !filter.tipo().isBlank()) ? filter.tipo().trim() : null;
        return busRepository.findByFilters(tipo);
    }

    public Bus getOne(Integer id) {
        return busRepository.findById(id).orElse(null);
    }

    @Transactional
    public ResultadoResponse create(Bus bus) {
        try {
            var registrado = busRepository.save(bus);
            var mensaje = String.format("Bus con Id %d registrado exitosamente", registrado.getIdBus());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar el bus");
        }
    }

    @Transactional
    public ResultadoResponse update(Bus bus) {
        try {
            var actualizado = busRepository.save(bus);
            var mensaje = String.format("Bus con Id %d actualizado exitosamente", actualizado.getIdBus());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al actualizar el bus");
        }
    }

    @Transactional
    public ResultadoResponse delete(Integer id) {
        try {
            busRepository.deleteById(id);
            return new ResultadoResponse(true, String.format("Bus con Id %d eliminado", id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al eliminar el bus");
        }
    }

    public Bus guardar(Bus bus) {
        return busRepository.save(bus);
    }

    public void eliminar(Integer id) {
        busRepository.deleteById(id);
    }

}
