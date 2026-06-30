package gestor.tareas.dto;

import gestor.tareas.entity.EstadoTarea;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarEstadoDTO {

    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoTarea estado;
}
