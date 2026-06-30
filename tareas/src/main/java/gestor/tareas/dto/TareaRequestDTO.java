package gestor.tareas.dto;

import gestor.tareas.entity.PrioridadTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TareaRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 150, message = "El título debe tener al menos 3 caracteres")
    private String titulo;

    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadTarea prioridad;
}
