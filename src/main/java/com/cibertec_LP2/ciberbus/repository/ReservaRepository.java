package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    
    // Método para buscar reservas por código (ignorando mayúsculas/minúsculas y por coincidencia parcial)
    List<Reserva> findByCodigoReservaContainingIgnoreCase(String codigoReserva);

}