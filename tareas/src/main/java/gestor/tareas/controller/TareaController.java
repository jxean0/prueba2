package gestor.tareas.controller;

import gestor.tareas.dto.ApiResponse;
import gestor.tareas.dto.CambiarEstadoDTO;
import gestor.tareas.dto.TareaRequestDTO;
import gestor.tareas.dto.TareaResponseDTO;
import gestor.tareas.entity.EstadoTarea;
import gestor.tareas.entity.PrioridadTarea;
import gestor.tareas.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TareaResponseDTO>>> listar() {
        List<TareaResponseDTO> tareas = tareaService.listarTodas();
        return ResponseEntity.ok(ApiResponse.ok("Tareas obtenidas exitosamente", tareas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TareaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        TareaResponseDTO tarea = tareaService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponse.ok("Tarea encontrada", tarea));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TareaResponseDTO>> crear(@Valid @RequestBody TareaRequestDTO dto) {
        TareaResponseDTO creada = tareaService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tarea creada exitosamente", creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TareaResponseDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TareaRequestDTO dto) {
        TareaResponseDTO actualizada = tareaService.actualizar(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Tarea actualizada exitosamente", actualizada));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<TareaResponseDTO>> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoDTO dto) {
        TareaResponseDTO actualizada = tareaService.cambiarEstado(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado exitosamente", actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Tarea eliminada exitosamente", null));
    }

    @GetMapping("/filtrar/estado")
    public ResponseEntity<ApiResponse<List<TareaResponseDTO>>> filtrarPorEstado(
            @RequestParam EstadoTarea estado) {
        List<TareaResponseDTO> tareas = tareaService.filtrarPorEstado(estado);
        return ResponseEntity.ok(ApiResponse.ok("Tareas filtradas por estado", tareas));
    }

    @GetMapping("/filtrar/prioridad")
    public ResponseEntity<ApiResponse<List<TareaResponseDTO>>> filtrarPorPrioridad(
            @RequestParam PrioridadTarea prioridad) {
        List<TareaResponseDTO> tareas = tareaService.filtrarPorPrioridad(prioridad);
        return ResponseEntity.ok(ApiResponse.ok("Tareas filtradas por prioridad", tareas));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<TareaResponseDTO>>> buscar(@RequestParam String q) {
        List<TareaResponseDTO> tareas = tareaService.buscarPorTitulo(q);
        return ResponseEntity.ok(ApiResponse.ok("Resultados de búsqueda", tareas));
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> estadisticas() {
        Map<String, Object> stats = tareaService.estadisticas();
        return ResponseEntity.ok(ApiResponse.ok("Estadísticas generadas exitosamente", stats));
    }
}
