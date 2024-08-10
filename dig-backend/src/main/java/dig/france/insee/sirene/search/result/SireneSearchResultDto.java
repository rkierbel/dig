package dig.france.insee.sirene.search.result;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record SireneSearchResultDto(List<SireneUnitDto> sireneUnits) {

    record SireneUnitDto(String sireneUnitCreationDate,
                         String firstName,
                         List<PeriodDto> periods) {}

    record PeriodDto() {}
}
