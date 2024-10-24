package br.com.drianodev.finance_api.model.repository;

import br.com.drianodev.finance_api.model.entity.Launch;
import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import br.com.drianodev.finance_api.model.enums.LaunchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

public interface LaunchRepository extends JpaRepository<Launch, Long> {

    @Query(value="SELECT SUM(l.value) FROM Launch l JOIN l.user u "
            + "WHERE u.id = :idUser AND l.type = :tipo AND l.status = :status GROUP BY u")
    BigDecimal getBalanceByBillingTypeAndUserStatus(@Param("idUser") Long idUser,
                                                    @Param("tipo") LaunchType tipo,
                                                    @Param("status") LaunchStatus status);
}
