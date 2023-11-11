package com.github.murilonerdx.skdemoparkapi.repository;

import com.github.murilonerdx.skdemoparkapi.entity.Vaga;
import com.github.murilonerdx.skdemoparkapi.entity.enums.StatusVaga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends MongoRepository<Vaga, String> {
    List<Vaga> findByStatus(StatusVaga status);
    List<Vaga> findByDateCreatedIsAfter(LocalDateTime dateCreated);
    List<Vaga> findByDataUpdatedIsAfter(LocalDateTime dateCreated);
    List<Vaga> findByCreateBy(String createdBy);
    Vaga findByCodigo(String codigo);
}
