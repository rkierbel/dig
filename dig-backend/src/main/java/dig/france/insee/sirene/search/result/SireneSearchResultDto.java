package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;
import java.util.List;

@Serdeable
public record SireneSearchResultDto(List<SireneUnitDto> sireneUnits) {

    record SireneUnitDto(LocalDate creationDate,
                         LocalDate lastModifiedDate,
                         String firstName,
                         List<PeriodDto> periods) {
    }

    record PeriodDto(List<PeriodChange> changes,
                     LocalDate startDate,
                     LocalDate endDate) {
    }

    record PeriodChange(String reason,
                        String value) {

    }
}
