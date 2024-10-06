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
    String id;

    @Field("description")
    String description;

    @Field("month")
    Integer month;

    @Field("year")
    Integer year;

    @Field("value")
    BigDecimal value;

    @Field("type")
    LaunchType type;

    @Field("status")
    LaunchStatus status;

    @DBRef
    @Field("user")
    User user;

    @Field("registration_date")
    LocalDate registrationDate;
}
