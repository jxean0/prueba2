package gestor.tareas.dto;

import gestor.tareas.entity.EstadoTarea;
import gestor.tareas.entity.PrioridadTarea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    private PrioridadTarea prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
