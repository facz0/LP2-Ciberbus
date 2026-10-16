package com.cibertec_LP2.ciberbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec_LP2.ciberbus.model.Ciudad;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    @Query("SELECT DISTINCT r.ciudadPartida FROM Ruta r WHERE r.estado = 1 AND r.ciudadPartida.estado = 1 ORDER BY r.ciudadPartida.ciudad ASC")
    List<Ciudad> findCiudadesconRuta();

}
