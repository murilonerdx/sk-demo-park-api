package com.github.murilonerdx.skdemoparkapi.service;

import com.github.murilonerdx.skdemoparkapi.dto.VagaDTO;
import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import com.github.murilonerdx.skdemoparkapi.exception.NotFoundException;
import com.github.murilonerdx.skdemoparkapi.exception.VagaExistException;
import com.github.murilonerdx.skdemoparkapi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository repository;

    public List<VagaDTO> findVagaLivre() {
        return repository.findByStatus(StatusVaga.LIVRE).stream().map(Vaga::toDTO).toList();
    }

    public Vaga getByCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }

    public List<Vaga> findByCreatedBy(String createdBy){
        return repository.findByCreateBy(createdBy);
    }

    public List<Vaga> findByUpdated() {
        return repository.findByDateCreatedIsAfter(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    }

    public List<Vaga> findByDataUpdatedIsAfter() {
        return repository.findByDataUpdatedIsAfter(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    }

    public List<VagaDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(Vaga::toDTO).toList();
    }

    public Vaga getById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Vaga com id %s não encontrada", id)));
    }

    public Vaga updateStatusVaga(String id, StatusVaga statusVaga, String modifyBy) {
        Vaga byId = getById(id);
        byId.setModifyBy(modifyBy);
        byId.setStatus(statusVaga);
        byId.setDataUpdated(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return repository.save(byId);
    }

    public VagaDTO create(Vaga vaga) {
        Vaga byCodigo = getByCodigo(vaga.getCodigo());

        if(byCodigo != null){
            throw new VagaExistException("Vaga já cadastrada");
        }
        return repository.save(vaga).toDTO();
    }

    public void delete(String id) {
        repository.delete(getById(id));
    }

    public Vaga update(String id, Vaga vaga) {
        Vaga vagaDB = getById(id);
        vagaDB.setCodigo(vaga.getCodigo());
        vagaDB.setDataUpdated(LocalDateTime.now());
        vagaDB.setStatus(vaga.getStatus());
        return repository.save(vagaDB);
    }

}
