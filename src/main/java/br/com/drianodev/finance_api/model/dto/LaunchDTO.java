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

    private String id;
    private String description;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private User user;
    private String type;
    private String status;
}
