package br.com.drianodev.finance_api.service;

import br.com.drianodev.finance_api.model.entity.Launch;
import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public interface LaunchService {

    Launch saveLaunch(Launch launch);
    Launch updateLaunch(Launch launch);
    void deleteLaunch(Launch launch);
    List<Launch> findLaunch(Launch filterLaunch);
    void updateStatusLaunch(Launch launch, LaunchStatus status);
    void validateLaunch(Launch launch);
    Optional<Launch> getLaunchById(Long id);
    BigDecimal getBalanceByUser(Long id);
}
