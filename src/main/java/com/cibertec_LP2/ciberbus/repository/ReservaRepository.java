package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    Optional<Reserva> findByCodigoReserva(String codigoReserva);

    List<Reserva> findByCodigoReservaContainingIgnoreCase(String codigoReserva);

    @Query("""
            select r from Reserva r
            where (:apellido is null or :apellido = '' or lower(r.usuario.apellido) like lower(concat('%', :apellido, '%')))
            order by r.idReserva desc
            """)
    List<Reserva> findByFilters(@Param("apellido") String apellido);

}
