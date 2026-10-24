package com.cibertec_LP2.ciberbus.dto;

import java.math.BigDecimal;
import java.util.List;

public record CompraRequest(
        Integer idViaje,
        BigDecimal totalPagar,
        String metodoPago,
        List<PasajeroCompraDto> pasajeros
) {
}
