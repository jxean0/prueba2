package gestor.tareas.service;

import gestor.tareas.dto.CambiarEstadoDTO;
import gestor.tareas.dto.TareaRequestDTO;
import gestor.tareas.dto.TareaResponseDTO;
import gestor.tareas.entity.EstadoTarea;
import gestor.tareas.entity.PrioridadTarea;
import gestor.tareas.entity.Tarea;
import gestor.tareas.exception.GlobalExceptionHandler.TareaNoEncontradaException;
import gestor.tareas.exception.GlobalExceptionHandler.TransicionEstadoInvalidaException;
import gestor.tareas.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaResponseDTO crear(TareaRequestDTO dto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setPrioridad(dto.getPrioridad());
        tarea.setEstado(EstadoTarea.PENDIENTE);

        Tarea guardada = tareaRepository.save(tarea);
        return toResponseDTO(guardada);
    }

    public List<TareaResponseDTO> listarTodas() {
        return tareaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TareaResponseDTO obtenerPorId(Long id) {
        Tarea tarea = buscarOFallar(id);
        return toResponseDTO(tarea);
    }

    public TareaResponseDTO actualizar(Long id, TareaRequestDTO dto) {
        Tarea tarea = buscarOFallar(id);

        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setPrioridad(dto.getPrioridad());

        Tarea actualizada = tareaRepository.save(tarea);
        return toResponseDTO(actualizada);
    }

    public TareaResponseDTO cambiarEstado(Long id, CambiarEstadoDTO dto) {
        Tarea tarea = buscarOFallar(id);
        EstadoTarea actual = tarea.getEstado();
        EstadoTarea nuevo = dto.getEstado();

        validarTransicion(actual, nuevo);

        tarea.setEstado(nuevo);
        Tarea actualizada = tareaRepository.save(tarea);
        return toResponseDTO(actualizada);
    }

    private void validarTransicion(EstadoTarea actual, EstadoTarea nuevo) {
        if (actual == EstadoTarea.CANCELADA) {
            throw new TransicionEstadoInvalidaException(
                    "Una tarea CANCELADA no puede cambiar de estado");
        }
        if (actual == EstadoTarea.COMPLETADA
                && (nuevo == EstadoTarea.PENDIENTE || nuevo == EstadoTarea.EN_PROGRESO)) {
            throw new TransicionEstadoInvalidaException(
                    "Una tarea COMPLETADA no puede volver a " + nuevo);
        }
    }

    public void eliminar(Long id) {
        Tarea tarea = buscarOFallar(id);
        tareaRepository.delete(tarea);
    }

    public List<TareaResponseDTO> filtrarPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TareaResponseDTO> filtrarPorPrioridad(PrioridadTarea prioridad) {
        return tareaRepository.findByPrioridad(prioridad)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<TareaResponseDTO> buscarPorTitulo(String texto) {
        return tareaRepository.findByTituloContainingIgnoreCase(texto)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Map<String, Object> estadisticas() {
        Map<EstadoTarea, Long> conteoPorEstado = new EnumMap<>(EstadoTarea.class);
        long total = 0;
        for (EstadoTarea estado : EstadoTarea.values()) {
            long cantidad = tareaRepository.countByEstado(estado);
            conteoPorEstado.put(estado, cantidad);
            total += cantidad;
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("porEstado", conteoPorEstado);
        resultado.put("total", total);
        return resultado;
    }

    private Tarea buscarOFallar(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new TareaNoEncontradaException(id));
    }

    private TareaResponseDTO toResponseDTO(Tarea tarea) {
        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getPrioridad(),
                tarea.getFechaCreacion(),
                tarea.getFechaActualizacion()
        );
    }
}
