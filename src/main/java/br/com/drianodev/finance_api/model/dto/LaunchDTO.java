package br.com.drianodev.finance_api.model.dto;

import br.com.drianodev.finance_api.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaunchDTO {

    private Long idLaunch;
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private Long user;
    private String type;
    private String status;
}
