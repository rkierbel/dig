package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Serdeable
public record SireneSearchResultDto(List<SireneUnitDto> sireneUnits) {

    @Override
    public String toString() {
        return "SireneSearchResultDto {\n" +
                "\tsireneUnits: " + (sireneUnits == null ? "null" :
                sireneUnits.stream()
                        .map(unit -> "\n\t\t" + unit.toString().replace("\n", "\n\t\t"))
                        .collect(Collectors.joining(",")) + "\n\t") +
                "\n}";
    }

    @Serdeable
    record SireneUnitDto(LocalDate creationDate,
                         Instant lastModifiedDate,
                         UnitType type,
                         String commonFirstName,
                         String firstNames,
                         List<PeriodDto> periods) {

        @Override
        public String toString() {
            return "SireneUnitDto {\n" +
                    "\tcreationDate: " + creationDate + ",\n" +
                    "\tlastModifiedDate: " + lastModifiedDate + ",\n" +
                    "\ttype: " + type + ",\n" +
                    "\tcommonFirstName: " + (commonFirstName != null ? commonFirstName : "null - legal entity") + ",\n" +
                    "\tfirstNames: " + (firstNames != null ? firstNames : "null - legal entity") + ",\n" +
                    "\tperiods: " + (periods == null ? "null" :
                    periods.stream()
                            .map(period -> "\n\t\t" + period.toString().replace("\n", "\n\t\t"))
                            .collect(Collectors.joining(",")) + "\n\t") +
                    "\n}";
        }
    }

    @Serdeable
    record PeriodDto(List<PeriodChange> changes,
                     String naturalPersonLastName,
                     String companyNames,
                     LocalDate startDate,
                     LocalDate endDate) {
        @Override
        public String toString() {
            return "PeriodDto {\n" +
                    "\t\tchanges: " + (changes == null ? "null" :
                    changes.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ", "[", "]"))) + ",\n" +
                    "\t\tnaturalPersonLastName: " + naturalPersonLastName + ",\n" +
                    "\t\tcompanyNames: " + (companyNames != null ? companyNames : "null - natural person") + ",\n" +
                    "\t\tstartDate: " + startDate + ",\n" +
                    "\t\tendDate: " + endDate + "\n" +
                    "}";
        }
    }
}
