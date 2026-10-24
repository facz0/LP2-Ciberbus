package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.ViajeAsiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViajeAsientoRepository extends JpaRepository<ViajeAsiento, Integer> {

    List<ViajeAsiento> findByViajeIdViajeAndEstado(Integer idViaje, Integer estado);

    Optional<ViajeAsiento> findByViajeIdViajeAndNroAsiento(Integer idViaje, Integer nroAsiento);

}
