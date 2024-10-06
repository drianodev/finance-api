package br.com.drianodev.finance_api.model.entity;

import br.com.drianodev.finance_api.model.enums.LaunchStatus;
import br.com.drianodev.finance_api.model.enums.LaunchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "launches")
public class Launch {

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("month")
    private Integer month;

    @Field("year")
    private Integer year;

    @Field("value")
    private BigDecimal value;

    @Field("type")
    private LaunchType type;

    @Field("status")
    private LaunchStatus status;

    @DBRef
    @Field("user")
    private User user;

    @Field("registration_date")
    private LocalDate registrationDate;
}
