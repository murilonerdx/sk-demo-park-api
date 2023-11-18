package com.github.murilonerdx.skdemoparkapi.repository;

import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteVagaRepository extends MongoRepository<ClienteVaga, Long> {
    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVaga> findAllByClienteCpf(String cpf, Pageable pageable);
    List<ClienteVaga> buscarTodosPorClienteCpf(String cpf);

    Page<ClienteVaga> findAllByClienteUsuarioId(String id, Pageable pageable);
}
