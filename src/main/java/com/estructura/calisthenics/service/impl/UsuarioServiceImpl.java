package com.estructura.calisthenics.service.impl;

import com.estructura.calisthenics.dto.UsuarioDTO;
import com.estructura.calisthenics.entity.Usuario;
import com.estructura.calisthenics.repository.UsuarioRepository;
import com.estructura.calisthenics.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario updateUsuario(Long id, UsuarioDTO usuariodto) {
        Usuario usuarioUpdated = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: "+ id));

        usuarioUpdated.setNombre(usuariodto.getNombre());
        usuarioUpdated.setEmail(usuariodto.getEmail());
        usuarioUpdated.setTelefono(usuariodto.getTelefono());
        usuarioUpdated.setPeso(usuariodto.getPeso());
        usuarioUpdated.setAltura(usuariodto.getAltura());
        usuarioUpdated.setFechanacimiento(usuariodto.getFechanacimiento());



        return usuarioRepository.save(usuarioUpdated);
    }
}
