package com.cibertec_LP2.ciberbus.model;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BUS")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdBus")
    private Integer idBus;

    @Column(name = "NroPlaca")
    private String nroPlaca;

    @Column(name = "Marca")
    private String marca;

    @Column(name = "TipoBus")
    private String tipoBus;

    @Column(name = "CantidadPisos")
    private Integer cantidadPisos;

    @Column(name = "NroAsientos")
    private Integer nroAsientos;

    @Column(name = "Estado")
    private Integer estado;

}
