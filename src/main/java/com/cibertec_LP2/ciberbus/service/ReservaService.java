package com.cibertec_LP2.ciberbus.service;

import com.cibertec_LP2.ciberbus.dto.CompraRequest;
import com.cibertec_LP2.ciberbus.dto.PasajeroCompraDto;
import com.cibertec_LP2.ciberbus.dto.ReservaFilter;
import com.cibertec_LP2.ciberbus.dto.ResultadoResponse;
import com.cibertec_LP2.ciberbus.model.*;
import com.cibertec_LP2.ciberbus.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final DetalleReservaRepository detalleReservaRepository;
    private final PasajeroRepository pasajeroRepository;
    private final ViajeAsientoRepository viajeAsientoRepository;
    private final ViajeRepository viajeRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Reserva> getAll() {
        return reservaRepository.findAll();
    }

    public List<Reserva> search(ReservaFilter filter) {
        String apellido = (filter != null && filter.apellido() != null && !filter.apellido().isBlank()) ? filter.apellido().trim() : null;
        return reservaRepository.findByFilters(apellido);
    }

    public Reserva getOne(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public List<Reserva> buscarPorCodigo(String codigoReserva) {
        return reservaRepository.findByCodigoReservaContainingIgnoreCase(codigoReserva.trim());
    }

    @Transactional
    public ResultadoResponse create(Reserva reserva) {
        try {
            if (reserva.getFechaReserva() == null) {
                reserva.setFechaReserva(LocalDateTime.now());
            }
            var registrado = reservaRepository.save(reserva);
            var mensaje = String.format("Reserva con Id %d registrada exitosamente", registrado.getIdReserva());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar la reserva");
        }
    }

    @Transactional
    public ResultadoResponse update(Reserva reserva) {
        try {
            if (reserva.getIdReserva() != null) {
                var existente = reservaRepository.findById(reserva.getIdReserva()).orElse(null);
                if (existente != null && reserva.getFechaReserva() == null) {
                    reserva.setFechaReserva(existente.getFechaReserva());
                }
            }
            if (reserva.getFechaReserva() == null) {
                reserva.setFechaReserva(LocalDateTime.now());
            }
            var actualizado = reservaRepository.save(reserva);
            var mensaje = String.format("Reserva con Id %d actualizada exitosamente", actualizado.getIdReserva());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al actualizar la reserva");
        }
    }

    @Transactional
    public ResultadoResponse delete(Integer id) {
        try {
            reservaRepository.deleteById(id);
            return new ResultadoResponse(true, String.format("Reserva con Id %d eliminada", id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al eliminar la reserva");
        }
    }

    public Reserva guardar(Reserva reserva) {
        if (reserva.getFechaReserva() == null) {
            reserva.setFechaReserva(LocalDateTime.now());
        }
        return reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        reservaRepository.deleteById(id);
    }

    public Reserva buscarPorCodigoUnico(String codigoReserva) {
        if (codigoReserva == null || codigoReserva.isBlank()) {
            return null;
        }
        return reservaRepository.findByCodigoReserva(codigoReserva.trim()).orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public Reserva procesarCompra(CompraRequest request) {
        if (request == null || request.idViaje() == null) {
            throw new IllegalArgumentException("Los datos de la reserva no son válidos");
        }
        if (request.pasajeros() == null || request.pasajeros().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar al menos un pasajero");
        }

        Viaje viaje = viajeRepository.findById(request.idViaje())
                .orElseThrow(() -> new IllegalArgumentException("El viaje seleccionado no existe"));

        // 1. Validar que ninguno de los asientos esté ocupado
        for (PasajeroCompraDto p : request.pasajeros()) {
            var asientoExistente = viajeAsientoRepository.findByViajeIdViajeAndNroAsiento(viaje.getIdViaje(), p.nroAsiento());
            if (asientoExistente.isPresent() && asientoExistente.get().getEstado() != null && asientoExistente.get().getEstado() == 1) {
                throw new IllegalStateException("El asiento número " + p.nroAsiento() + " ya se encuentra ocupado.");
            }
        }

        // 2. Obtener o crear al Usuario titular (basado en el primer pasajero)
        PasajeroCompraDto titular = request.pasajeros().get(0);
        Usuario usuario = null;

        if (titular.correo() != null && !titular.correo().isBlank()) {
            usuario = usuarioRepository.findByCorreo(titular.correo().trim()).orElse(null);
        }
        if (usuario == null && titular.nroDocumento() != null && !titular.nroDocumento().isBlank()) {
            usuario = usuarioRepository.findByNroDocumento(titular.nroDocumento().trim()).orElse(null);
        }

        if (usuario == null) {
            usuario = new Usuario();
            usuario.setNombre(titular.nombre());
            usuario.setApellido(titular.apellido());
            String correo = (titular.correo() != null && !titular.correo().isBlank())
                    ? titular.correo().trim()
                    : "cliente_" + titular.nroDocumento().trim() + "@ciberbus.pe";
            usuario.setCorreo(correo);
            usuario.setClave("123456");
            usuario.setTipoDocumento(titular.tipoDocumento());
            String nroDoc = titular.nroDocumento().trim();
            if (nroDoc.length() > 9) {
                nroDoc = nroDoc.substring(0, 9);
            }
            usuario.setNroDocumento(nroDoc);
            usuario.setTelefono(titular.telefono());
            usuario.setFechaNacimiento(titular.fechaNacimiento());
            usuario.setNacionalidad(titular.nacionalidad() != null && !titular.nacionalidad().isBlank() ? titular.nacionalidad() : "Peruana");
            usuario.setGenero(titular.genero());
            usuario.setRol("CLIENTE");
            usuario.setEstado(1);
            usuario = usuarioRepository.save(usuario);
        }

        // 3. Generar código de reserva único (ej: RSV48291)
        String codigoReserva;
        do {
            codigoReserva = "RSV" + (int) (10000 + Math.random() * 90000);
        } while (reservaRepository.findByCodigoReserva(codigoReserva).isPresent());

        // 4. Guardar la Reserva
        BigDecimal total = request.totalPagar();
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            total = viaje.getTarifa().multiply(BigDecimal.valueOf(request.pasajeros().size()));
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setViaje(viaje);
        reserva.setCodigoReserva(codigoReserva);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setMontoTotal(total);
        reserva.setMetodoPago(request.metodoPago() != null && !request.metodoPago().isBlank() ? request.metodoPago() : "TARJETA");
        reserva.setEstado("PAGADA");
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // 5. Procesar cada pasajero, actualizar asiento y registrar detalle
        for (PasajeroCompraDto p : request.pasajeros()) {
            // Pasajero
            Pasajero pasajero = pasajeroRepository.findByNroDocumento(p.nroDocumento().trim()).orElse(null);
            if (pasajero == null) {
                pasajero = new Pasajero();
            }
            pasajero.setTipoDocumento(p.tipoDocumento());
            pasajero.setNroDocumento(p.nroDocumento().trim());
            pasajero.setNombre(p.nombre());
            pasajero.setApellido(p.apellido());
            pasajero.setCorreo(p.correo());
            pasajero.setTelefono(p.telefono());
            pasajero.setFechaNacimiento(p.fechaNacimiento());
            pasajero.setNacionalidad(p.nacionalidad() != null && !p.nacionalidad().isBlank() ? p.nacionalidad() : "Peruana");
            pasajero.setGenero(p.genero());
            pasajero.setEstado(1);
            pasajero = pasajeroRepository.save(pasajero);

            // ViajeAsiento
            ViajeAsiento asiento = viajeAsientoRepository.findByViajeIdViajeAndNroAsiento(viaje.getIdViaje(), p.nroAsiento())
                    .orElse(null);
            if (asiento == null) {
                asiento = new ViajeAsiento();
                asiento.setViaje(viaje);
                asiento.setNroAsiento(p.nroAsiento());
                asiento.setPiso(1);
            }
            asiento.setEstado(1); // 1 = Vendido
            asiento = viajeAsientoRepository.save(asiento);

            // DetalleReserva
            DetalleReserva detalle = new DetalleReserva();
            detalle.setReserva(reservaGuardada);
            detalle.setViajeAsiento(asiento);
            detalle.setPasajero(pasajero);
            detalle.setPrecioPagado(viaje.getTarifa());
            detalleReservaRepository.save(detalle);
        }

        return reservaGuardada;
    }

}
