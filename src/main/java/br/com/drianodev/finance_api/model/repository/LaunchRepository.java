package br.com.drianodev.finance_api.model.repository;

import br.com.drianodev.finance_api.model.entity.Launch;
import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import br.com.drianodev.finance_api.model.enums.LaunchType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LaunchRepository extends MongoRepository<Launch, String> {

    @Query(value = "{ 'user.id' : ?0, 'type' : ?1, 'status' : ?2 }")
    Double getBalanceByTypeLaunchAndUserStatus(String idUser, LaunchType type, LaunchStatus status);
}

