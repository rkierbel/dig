package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Serdeable
public record SireneSearchResultDto(List<SireneUnitDto> sireneUnits) {

    record SireneUnitDto(Instant creationDate,
                         Instant lastModifiedDate,
                         String firstName,
                         List<PeriodDto> periods) {
        @Override
        public String toString() {
            return "[\nSirene Unit %s: created on %s and last modified on %s. History: %s\n]"
                    .formatted(firstName, creationDate, lastModifiedDate, periods);
        }
    }

    record PeriodDto(List<PeriodChange> changes,
                     LocalDate startDate,
                     LocalDate endDate) {

        @Override
        public String toString() {
            boolean isCurrent = endDate == null;
            return isCurrent ?
                    "\n\t{Period created on %s}".formatted(startDate) :
                    "\n\t{Period created on %s and ended on %s. Change reasons: %s}".formatted(startDate, endDate, changes);
        }
    }
}
