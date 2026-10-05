package com.cibertec_LP2.ciberbus.model;

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
@Table(name = "VIAJEASIENTO")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class ViajeAsiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdViajeAsiento")
    private Integer idViajeAsiento;

    @ManyToOne
    @JoinColumn(name = "IdViaje")
    private Viaje viaje;

    @Column(name = "NroAsiento")
    private Integer nroAsiento;

    @Column(name = "Piso")
    private Integer piso;

    @Column(name = "Estado")
    private Integer estado;

}
