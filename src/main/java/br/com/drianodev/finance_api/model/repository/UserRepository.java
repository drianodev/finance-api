package br.com.drianodev.finance_api.model.repository;

import java.util.Optional;

import br.com.drianodev.finance_api.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Verifica se o email existe
    boolean existsByEmail(String email);

    // Busca um usuário pelo email informado
    Optional<User> findByEmail(String email);
}
