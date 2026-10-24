package com.cibertec_LP2.ciberbus.dto;

import java.time.LocalDate;

public record PasajeroCompraDto(
        Integer nroAsiento,
        String tipoDocumento,
        String nroDocumento,
        String nombre,
        String apellido,
        String correo,
        String telefono,
        LocalDate fechaNacimiento,
        String nacionalidad,
        String genero
) {
}
