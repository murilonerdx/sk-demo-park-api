package com.github.murilonerdx.skdemoparkapi.repository;

import com.github.murilonerdx.skdemoparkapi.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Cliente findByCpf(String cpf);
    Cliente findByUsuarioId(String id);
}
