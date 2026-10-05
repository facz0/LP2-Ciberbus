package com.cibertec_LP2.ciberbus.model;

import java.math.BigDecimal;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RUTA")
@Setter
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRuta")
    private Integer idRuta;

    @Column(name = "codigo_ruta")
    private String codigoRuta;

    @ManyToOne
    @JoinColumn(name = "CiudadPartida")
    private Ciudad ciudadPartida;

    @ManyToOne
    @JoinColumn(name = "CiudadLlegada")
    private Ciudad ciudadLlegada;

    @Column(name = "HorasEstimadas")
    private BigDecimal horasEstimadas;

    @Column(name = "Estado")
    private Integer estado;

}
