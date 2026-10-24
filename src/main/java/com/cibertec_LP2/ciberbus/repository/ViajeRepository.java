package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    List<Viaje> findByRutaIdRutaAndFechaSalida(Integer idRuta, LocalDate fechaSalida);

    @Query("""
            select v from Viaje v
            where (:idRuta is null or v.ruta.idRuta = :idRuta)
            order by v.idViaje desc
            """)
    List<Viaje> findByFilters(@Param("idRuta") Integer idRuta);

}
