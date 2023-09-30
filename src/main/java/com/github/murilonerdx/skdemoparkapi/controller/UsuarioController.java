package com.github.murilonerdx.skdemoparkapi.controller;

import com.github.murilonerdx.skdemoparkapi.dto.PasswordChangeDTO;
import com.github.murilonerdx.skdemoparkapi.dto.UsuarioDTO;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import com.github.murilonerdx.skdemoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getById(id));
    }


    @PatchMapping("/recovery/password/{id}")
    public ResponseEntity<UsuarioDTO> updatePassword(@PathVariable("id") String id, @RequestBody PasswordChangeDTO passwordChangeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updatePassword(id, passwordChangeDTO));
    }
}
