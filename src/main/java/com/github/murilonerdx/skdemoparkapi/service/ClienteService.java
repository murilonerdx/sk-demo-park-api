package com.github.murilonerdx.skdemoparkapi.service;

import com.github.murilonerdx.skdemoparkapi.dto.*;
import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import com.github.murilonerdx.skdemoparkapi.exception.CpfUniqueViolationException;
import com.github.murilonerdx.skdemoparkapi.exception.NotFoundException;
import com.github.murilonerdx.skdemoparkapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteResponseDTO save(ClienteCreateDTO ud) {
        Cliente byEmail = repository.findByCpf(ud.getCpf());

        if(byEmail != null){
            throw new CpfUniqueViolationException(String.format("Cliente CPF %s já existe", byEmail.getCpf()));
        }else{

            return repository.save(ud.toModel()).toResponseModel();
        }
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findByCpf(String cpf){
        return repository.findByCpf(cpf).toResponseModel();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO getById(String id) {
        return repository.findById(id).orElseThrow(() ->  new NotFoundException("Cliente não existe")).toResponseModel();
    }

    @Transactional(readOnly = true)
    public Page<Cliente> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorUsuarioId(String id) {
        return repository.findByUsuarioId(id).toResponseModel();
    }
}
