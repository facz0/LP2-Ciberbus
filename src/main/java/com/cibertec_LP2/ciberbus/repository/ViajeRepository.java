package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    List<Viaje> findByRutaIdRutaAndFechaSalida(Integer idRuta, LocalDate fechaSalida);

}
