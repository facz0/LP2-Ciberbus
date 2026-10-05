package com.cibertec_LP2.ciberbus.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CONDUCTOR")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdConductor")
    private Integer conductorId;

    @Column(name = "tipoDocumento")
    private String tipoDocumento;

    @Column(name = "NroDocumento")
    private Integer numeroDocumento;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellidos;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Telefono")
    private Integer telefono;

    @Column(name = "CategoriaLicencia")
    private String categoriaLicencia;

    @Column(name = "VencimientoLicencia")
    private LocalDate vencimientoLicencia;

    @Column(name = "DiaDescanso")
    private String diaDescanso;

    @Column(name = "Estado")
    private Integer estado;

}
