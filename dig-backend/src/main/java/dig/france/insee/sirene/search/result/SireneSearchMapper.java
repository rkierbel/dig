package dig.france.insee.sirene.search.result;

import io.micronaut.context.annotation.Primary;
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
@Primary
public abstract class SireneSearchMapper {

    public abstract SireneSearchResultDto apiResponseToDto(SireneSearchResponse apiResponse);

    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", qualifiedByName = "toInstant"),
            @Mapping(target = "firstNames", expression = "java(sireneUnit.firstNames())"),
            @Mapping(target = "type", expression = "java(sireneUnit.inferUnitType())")
    })
    abstract SireneSearchResultDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit sireneUnit);

    @Mappings({
            @Mapping(target = "changes", expression = "java(unitPeriod.getPeriodChanges())"),
            @Mapping(target = "companyNames", expression = "java(unitPeriod.companyNames())"),
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
