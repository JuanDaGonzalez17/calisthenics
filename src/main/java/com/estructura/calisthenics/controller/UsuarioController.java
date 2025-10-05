package com.estructura.calisthenics.controller;

import com.estructura.calisthenics.dto.EjercicioDTO;
import com.estructura.calisthenics.dto.UsuarioDTO;
import com.estructura.calisthenics.entity.Ejercicio;
import com.estructura.calisthenics.entity.Usuario;
import com.estructura.calisthenics.service.EjercicioService;
import com.estructura.calisthenics.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<?>saveUsuario(@RequestBody UsuarioDTO dto) {
        try{
            Usuario usuario = new Usuario(
                    dto.getNombre(),
                    dto.getEmail(),
                    dto.getTelefono(),
                    dto.getPeso(),
                    dto.getAltura(),
                    dto.getFechanacimiento()
            );
            return ResponseEntity.ok().body(usuarioService.saveUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/listAll")
    public ResponseEntity<List<Usuario>> listAllTasks() {
        return ResponseEntity.ok().body(usuarioService.listAllUsuarios());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEjercicio(@PathVariable Long id, @RequestBody UsuarioDTO usuariodto) {

        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuariodto);
        return ResponseEntity.ok(updatedUsuario);
    }
}
