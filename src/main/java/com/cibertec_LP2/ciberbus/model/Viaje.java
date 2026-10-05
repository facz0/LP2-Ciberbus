package com.cibertec_LP2.ciberbus.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "VIAJE")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdViaje")
    private Integer idViaje;

    @Column(name = "CodigoViaje")
    private String codigoViaje;

    @ManyToOne
    @JoinColumn(name = "IdRuta")
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "IdBus")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "IdConductor")
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "IdCopiloto")
    private Conductor copiloto;

    @Column(name = "FechaSalida")
    private LocalDate fechaSalida;

    @Column(name = "HoraSalida")
    private LocalTime horaSalida;

    @Column(name = "FechaLlegada")
    private LocalDate fechaLlegada;

    @Column(name = "HoraLlegada")
    private LocalTime horaLlegada;

    @Column(name = "Tarifa")
    private BigDecimal tarifa;

    @Column(name = "Estado")
    private Integer estado;

}
