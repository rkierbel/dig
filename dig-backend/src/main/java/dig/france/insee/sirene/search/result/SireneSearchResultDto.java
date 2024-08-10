package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Serdeable
public record SireneSearchResultDto(List<SireneUnitDto> sireneUnits) {

    @Serdeable
    record SireneUnitDto(LocalDate creationDate,
                         Instant lastModifiedDate,
                         String commonFirstName,
                         List<PeriodDto> periods) {

        @Override
        public String toString() {
            return "\nSirene Unit %s: created on %s and last modified on %s. \nHistory: %s"
                    .formatted(creationDate, creationDate, lastModifiedDate, periods);
        }
    }

    @Serdeable
    record PeriodDto(List<PeriodChange> changes,
                     LocalDate startDate,
                     LocalDate endDate) {

        @Override
        public String toString() {
            boolean isCurrent = endDate == null;
            return isCurrent ?
                    "\n\t- Current period, started on %s".formatted(startDate) :
                    "\n\t- Period started on %s and ended on %s \n\tChange reasons: %s".formatted(startDate, endDate, changes);
        }
    }
}
