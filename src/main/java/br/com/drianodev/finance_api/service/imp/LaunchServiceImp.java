package br.com.drianodev.finance_api.service.imp;

import br.com.drianodev.finance_api.exceptions.BusinessRulesException;
import br.com.drianodev.finance_api.model.entity.Launch;
import br.com.drianodev.finance_api.model.entity.User;
import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import br.com.drianodev.finance_api.model.enums.LaunchType;
import br.com.drianodev.finance_api.model.repository.LaunchRepository;
import br.com.drianodev.finance_api.service.LaunchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class LaunchServiceImp implements LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @Override
    @Transactional
    public Launch saveLaunch(Launch launch) {
        launch.setStatus(LaunchStatus.PENDING);
        validateLaunch(launch);
        return launchRepository.save(launch);
    }

    @Override
    @Transactional
    public Launch updateLaunch(Launch launch) {
        Objects.requireNonNull(launch.getIdLaunch());
        return launchRepository.save(launch);
    }

    @Override
    @Transactional
    public void deleteLaunch(Launch launch) {
        Objects.requireNonNull(launch.getIdLaunch());
        launchRepository.delete(launch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Launch> findLaunch(Launch filterLaunch) {
        Example example = Example.of(filterLaunch, ExampleMatcher.matching()
                .withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return launchRepository.findAll(example);
    }

    @Override
    public void updateStatusLaunch(Launch launch, LaunchStatus status) {
        // TODO: implementar
    }

    @Override
    public void validateLaunch(Launch launch) {
        if (isNullOrEmpty(launch.getDescription())) throw new BusinessRulesException("Digite uma descrição!");

        if (!isValidMonth(launch.getMonth())) throw new BusinessRulesException("Digite um mês válido!");

        if (!isValidYear(launch.getYear())) throw new BusinessRulesException("Digite um ano válido!");

        if (isInvalidUser(launch.getUser())) throw new BusinessRulesException("Usuário inválido!");

        if (isInvalidValue(launch.getValue())) throw new BusinessRulesException("Digite um valor válido!");

        if (launch.getType() == null) throw new BusinessRulesException("Informe um tipo de lançamento.");
    }

    @Override
    public Optional<Launch> getLaunchById(Long id) {
        return launchRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalanceByUser(Long id) {
        BigDecimal income = launchRepository.getBalanceByBillingTypeAndUserStatus(id, LaunchType.INCOME, LaunchStatus.EFFECTIVE);
        BigDecimal expense = launchRepository.getBalanceByBillingTypeAndUserStatus(id, LaunchType.EXPENSE, LaunchStatus.EFFECTIVE);

        income = (income == null) ? BigDecimal.ZERO : income;
        expense = (expense == null) ? BigDecimal.ZERO : expense;

        return income.subtract(expense);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidMonth(Integer month) {
        return month != null && month >= 1 && month <= 12;
    }

    private boolean isValidYear(Integer year) {
        return year != null && year.toString().length() == 4;
    }

    private boolean isInvalidUser(User user) {
        return user == null || user.getId() == null;
    }

    private boolean isInvalidValue(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) <= 0;
    }
}
