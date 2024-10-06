package br.com.drianodev.finance_api.controller;

import br.com.drianodev.finance_api.exceptions.BusinessRulesException;
import br.com.drianodev.finance_api.model.dto.LaunchDTO;
import br.com.drianodev.finance_api.model.dto.UpdateStatusDTO;
import br.com.drianodev.finance_api.model.entity.Launch;
import br.com.drianodev.finance_api.model.entity.User;
import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import br.com.drianodev.finance_api.model.enums.LaunchType;
import br.com.drianodev.finance_api.service.LaunchService;
import br.com.drianodev.finance_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/launches")
@RequiredArgsConstructor
public class LaunchController {

    private final UserService userService;
    private final LaunchService launchService;

    @GetMapping
    public ResponseEntity getLaunch (
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "type", required = false) LaunchType launchType,
            @RequestParam("user") String idUser) {
        Launch filterLaunch = new Launch();
        filterLaunch.setDescription(description);
        filterLaunch.setMonth(month);
        filterLaunch.setYear(year);
        filterLaunch.setType(launchType);

        Optional<User> user = userService.getUserById(idUser);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta, usuário nao encontrado!");

        filterLaunch.setUser(user.get());

        List<Launch> launches = launchService.findLaunch(filterLaunch);
        List<Launch> orderedLaunches = launches.stream()
                .sorted(Comparator.comparing(Launch::getYear).reversed()
                        .thenComparing(Comparator.comparing(Launch::getMonth).reversed()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderedLaunches);
    }

    @GetMapping("/{id}")
    public ResponseEntity getLaunch(@PathVariable("id") String id) {
        return launchService.getLaunchById(id).map(launch -> new ResponseEntity(convert(launch), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody LaunchDTO launchDTO) {
        try {
            Launch launch = convert(launchDTO);
            launch = launchService.saveLaunch(launch);
            return new ResponseEntity(launch, HttpStatus.CREATED);
        } catch (BusinessRulesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody LaunchDTO launchDTO) {
        return launchService.getLaunchById(id).map(launchMap -> {
            try {
                Launch launch = convert(launchDTO);
                launch.setId(launchMap.getId());
                launchService.updateLaunch(launch);
                return ResponseEntity.ok(launch);
            } catch (BusinessRulesException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado!", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity updateStatus(@PathVariable("id") String id, @RequestBody UpdateStatusDTO updateStatusDTO) {
        return launchService.getLaunchById(id).map(launchMap -> {
            LaunchStatus selectStatus = LaunchStatus.valueOf(updateStatusDTO.getStatus());
            if (selectStatus == null) {
                return ResponseEntity.badRequest().body("Não foi possivel atualizar!");
            }

            launchMap.setStatus(selectStatus);
            launchService.updateLaunch(launchMap);
            return ResponseEntity.ok(launchMap);
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado!", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        return launchService.getLaunchById(id).map(launchMap -> {
            launchService.deleteLaunch(launchMap);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lancamento não encontrado!", HttpStatus.BAD_REQUEST));
    }

    private LaunchDTO convert(Launch launch) {
        return LaunchDTO.builder().
                id(launch.getId()).
                description(launch.getDescription()).
                value(launch.getValue()).
                month(launch.getMonth()).
                year(launch.getYear()).
                status(launch.getStatus().name()).
                type(launch.getType().name()).
                user(launch.getUser()).
                build();
    }

    private Launch convert(LaunchDTO launchDTO) {
        Launch launch = new Launch();
        launch.setId(launchDTO.getId());
        launch.setDescription(launchDTO.getDescription());
        launch.setYear(launchDTO.getYear());
        launch.setMonth(launchDTO.getMonth());
        launch.setValue(launchDTO.getValue());
        User user = userService.getUserById(launchDTO.getUser().getId())
                .orElseThrow(() -> new BusinessRulesException("Usuário não encontrado!"));
        launch.setUser(user);
        launch.setType(LaunchType.valueOf(launchDTO.getType()));
        if (launchDTO.getStatus() != null) {
            launch.setStatus(LaunchStatus.valueOf(launchDTO.getStatus()));
        }
        return launch;
    }
}
