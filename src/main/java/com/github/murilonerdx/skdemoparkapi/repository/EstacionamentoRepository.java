package com.github.murilonerdx.skdemoparkapi.repository;

import com.github.murilonerdx.skdemoparkapi.entity.ClienteVaga;
import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamentoRepository extends MongoRepository<ClienteVaga, String> {
}
