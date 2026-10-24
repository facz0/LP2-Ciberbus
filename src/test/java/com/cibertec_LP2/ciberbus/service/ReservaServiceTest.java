package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.CompraRequest;
import com.cibertec_LP2.ciberbus.dto.PasajeroCompraDto;
import com.cibertec_LP2.ciberbus.model.Bus;
import com.cibertec_LP2.ciberbus.model.Reserva;
import com.cibertec_LP2.ciberbus.model.Viaje;
import com.cibertec_LP2.ciberbus.model.ViajeAsiento;
import com.cibertec_LP2.ciberbus.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private DetalleReservaRepository detalleReservaRepository;

    @Mock
    private PasajeroRepository pasajeroRepository;

    @Mock
    private ViajeAsientoRepository viajeAsientoRepository;

    @Mock
    private ViajeRepository viajeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Viaje viajeMock;

    @BeforeEach
    void setUp() {
        viajeMock = new Viaje();
        viajeMock.setIdViaje(1);
        viajeMock.setCodigoViaje("VIAJE-001");
        viajeMock.setTarifa(new BigDecimal("50.00"));
        Bus bus = new Bus();
        bus.setTipoBus("VIP");
        viajeMock.setBus(bus);
    }

    @Test
    @DisplayName("Debe procesar compra exitosamente cuando los asientos están libres")
    void procesarCompra_Exitoso() {
        // Arrange
        when(viajeRepository.findById(1)).thenReturn(Optional.of(viajeMock));
        when(viajeAsientoRepository.findByViajeIdViajeAndNroAsiento(1, 5)).thenReturn(Optional.empty());
        when(usuarioRepository.findByCorreo("carlos@mail.com")).thenReturn(Optional.empty());
        when(usuarioRepository.findByNroDocumento("71234567")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(reservaRepository.findByCodigoReserva(any())).thenReturn(Optional.empty());
        when(reservaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(pasajeroRepository.findByNroDocumento("71234567")).thenReturn(Optional.empty());
        when(pasajeroRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(viajeAsientoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(detalleReservaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PasajeroCompraDto pasajero = new PasajeroCompraDto(
                5, "DNI", "71234567", "Carlos", "Gómez", "carlos@mail.com", "987654321",
                LocalDate.of(1995, 5, 20), "Peruana", "Masculino"
        );
        CompraRequest request = new CompraRequest(1, new BigDecimal("50.00"), "TARJETA", List.of(pasajero));

        // Act
        Reserva resultado = reservaService.procesarCompra(request);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigoReserva());
        assertTrue(resultado.getCodigoReserva().startsWith("RSV"));
        assertEquals("PAGADA", resultado.getEstado());
        assertEquals(new BigDecimal("50.00"), resultado.getMontoTotal());

        verify(viajeRepository).findById(1);
        verify(usuarioRepository).save(any());
        verify(reservaRepository).save(any());
        verify(pasajeroRepository).save(any());
        verify(viajeAsientoRepository).save(any());
        verify(detalleReservaRepository).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el asiento ya se encuentra ocupado")
    void procesarCompra_AsientoOcupado_LanzaExcepcion() {
        // Arrange
        ViajeAsiento ocupado = new ViajeAsiento();
        ocupado.setNroAsiento(5);
        ocupado.setEstado(1); // Ocupado

        when(viajeRepository.findById(1)).thenReturn(Optional.of(viajeMock));
        when(viajeAsientoRepository.findByViajeIdViajeAndNroAsiento(1, 5)).thenReturn(Optional.of(ocupado));

        PasajeroCompraDto pasajero = new PasajeroCompraDto(
                5, "DNI", "71234567", "Carlos", "Gómez", "carlos@mail.com", "987654321",
                LocalDate.of(1995, 5, 20), "Peruana", "Masculino"
        );
        CompraRequest request = new CompraRequest(1, new BigDecimal("50.00"), "TARJETA", List.of(pasajero));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            reservaService.procesarCompra(request);
        });

        assertTrue(exception.getMessage().contains("ya se encuentra ocupado"));
        verify(reservaRepository, never()).save(any());
    }

}
