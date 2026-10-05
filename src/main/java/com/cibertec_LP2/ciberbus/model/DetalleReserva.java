package com.cibertec_LP2.ciberbus.model;

import java.math.BigDecimal;

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
@Table(name = "DETALLERESERVA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDetalleReserva")
    private Integer IdDetalleReserva;

    @ManyToOne
    @JoinColumn(name = "IdReserva")
    private Integer IdReserva;

    @ManyToOne
    @JoinColumn(name = "IdViajeAsiento")
    private Integer IdViajeAsiento;

    @ManyToOne
    @JoinColumn(name = "IdPasajero")
    private Integer IdPasajero;

    @Column(name = "PrecioPagado")
    private BigDecimal precioPagado;

}
