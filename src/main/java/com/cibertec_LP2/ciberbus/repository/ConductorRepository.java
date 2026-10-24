package com.cibertec_LP2.ciberbus.repository;

import com.cibertec_LP2.ciberbus.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConductorRepository extends JpaRepository<Conductor, Integer> {

    List<Conductor> findAllByOrderByNombreAsc();

    @Query("""
            select c from Conductor c
            where (:dni is null or :dni = '' or cast(c.numeroDocumento as string) like concat('%', :dni, '%'))
            order by c.conductorId asc
            """)
    List<Conductor> findByFilters(@Param("dni") String dni);

}
