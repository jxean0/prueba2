package gestor.tareas.repository;

import gestor.tareas.entity.EstadoTarea;
import gestor.tareas.entity.PrioridadTarea;
import gestor.tareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByEstado(EstadoTarea estado);
    List<Tarea> findByPrioridad(PrioridadTarea prioridad);
    List<Tarea> findByTituloContainingIgnoreCase(String texto);
    long countByEstado(EstadoTarea estado);
}
