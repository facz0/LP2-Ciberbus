package com.cibertec_LP2.ciberbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec_LP2.ciberbus.model.Bus;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Integer> {

    @Query("""
            select b from Bus b
            where (:tipo is null or :tipo = '' or b.tipoBus = :tipo)
            order by b.idBus desc
            """)
    List<Bus> findByFilters(@Param("tipo") String tipo);

}
