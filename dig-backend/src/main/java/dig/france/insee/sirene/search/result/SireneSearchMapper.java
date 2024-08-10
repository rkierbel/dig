package dig.france.insee.sirene.search.result;

import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "jsr330") //TODO -> explain
@Singleton
public abstract class SireneSearchMapper {

    public abstract SireneSearchResultDto apiResponseToDto(SireneSearchResponse apiResponse);

    @Mapping(target="lastModifiedDate", source="lastModifiedDate", qualifiedByName = "toInstant")
    abstract SireneSearchResultDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit sireneUnit);

    @Mappings({
            @Mapping(target = "changes", expression = "java(unitPeriod.getPeriodChanges())")
    })
    abstract SireneSearchResultDto.PeriodDto toPeriodDto(SireneSearchResponse.Period unitPeriod);

    @Named("toInstant")
    protected Instant toInstant(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);

        ZoneId brusselsZone = ZoneId.of("Europe/Brussels");
        return localDateTime.atZone(brusselsZone).toInstant();
    }
}
