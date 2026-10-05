package com.cibertec_LP2.ciberbus.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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
@Table(name = "CIUDAD")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCiudad")
    private Integer idCiudad;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Departamento")
    private String departamento;

    @Column(name = "Estado")
    @Generated(event = EventType.INSERT)
    private Integer estado;

}
