package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {

    @Query("SELECT r FROM Ruta r WHERE r.ciudadPartida.idCiudad = ?1 AND r.estado = 1 ORDER BY r.ciudadLlegada.departamento ASC")
    List<Ruta> findDestinosPorOrigen(Integer idCiudad);

}
