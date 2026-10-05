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
@Table(name = "PASAJERO")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPasajero")
    private Integer idPasajero;

    @Column(name = "TipoDocumento")
    private String tipoDocumento;

    @Column(name = "NroDocumento")
    private String nroDocumento;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "FechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "Nacionalidad")
    private String nacionalidad;

    @Column(name = "Genero")
    private String genero;

    @Column(name = "Estado")
    private Integer estado;

}
