package com.github.murilonerdx.skdemoparkapi.repository;

import com.github.murilonerdx.skdemoparkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>, JpaSpecificationExecutor<Usuario> {
    Usuario findByUsername(String username);
}
