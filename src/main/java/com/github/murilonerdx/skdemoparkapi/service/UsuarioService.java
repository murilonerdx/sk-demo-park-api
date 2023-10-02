package com.github.murilonerdx.skdemoparkapi.service;

import com.github.murilonerdx.skdemoparkapi.dto.PasswordChangeDTO;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioCreateDTO;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioDTO;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import com.github.murilonerdx.skdemoparkapi.exception.NotFoundException;
import com.github.murilonerdx.skdemoparkapi.exception.UsernameExistException;
import com.github.murilonerdx.skdemoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Transactional
    public UsuarioDTO save(UsuarioCreateDTO ud) {
        Usuario usuario = new Usuario();
        Usuario byUsername = usuarioRepository.findByUsername(ud.getUsername());

        if(byUsername != null){
            throw new UsernameExistException(String.format("Username %s já existe", byUsername.getUsername()));
        }else{
            BeanUtils.copyProperties(ud, usuario);
            return usuarioRepository.save(usuario).toDTO();
        }
    }

    @Transactional(readOnly = true)
    public UsuarioDTO getById(String id) {
        return usuarioRepository.findById(id).orElseThrow(() ->  new NotFoundException("Usuario não existe")).toDTO();
    }

    public UsuarioDTO updatePassword(String id, PasswordChangeDTO passwordChangeDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario não existe"));

        usuario.setPassword(passwordChangeDTO.getPassword());
        return usuarioRepository.save(usuario).toDTO();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll().stream().map(Usuario::toDTO).collect(Collectors.toList());
    }
}
