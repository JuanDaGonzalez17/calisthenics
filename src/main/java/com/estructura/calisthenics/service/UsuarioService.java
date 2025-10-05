package com.estructura.calisthenics.service;

import com.estructura.calisthenics.dto.UsuarioDTO;
import com.estructura.calisthenics.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    public Usuario saveUsuario(Usuario usuario);
    public List<Usuario> listAllUsuarios();
    public Usuario updateUsuario(Long id, UsuarioDTO usuariodto);
}
