package com.cibertec_LP2.ciberbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec_LP2.ciberbus.model.DetalleReserva;
import java.util.List;

public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Integer> {

    List<DetalleReserva> findByReservaIdReserva(Integer idReserva);

}
