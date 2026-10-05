package com.cibertec_LP2.ciberbus.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "RESERVA")
@Getter
@Setter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdReserva")
    private Integer IdReserva;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Integer IdUsuario;

    @ManyToOne
    @JoinColumn(name = "IdViaje")
    private Integer IdViaje;

    @Column(name = "CodigoReserva")
    private String codigoReserva;

    @Column(name = "FechaReserva")
    private LocalDateTime fechaReserva;

    @Column(name = "MontoTotal")
    private BigDecimal montoTotal;

    @Column(name = "MetodoPago")
    private String metodoPago;

    @Column(name = "Estado")
    private String estado;
}
