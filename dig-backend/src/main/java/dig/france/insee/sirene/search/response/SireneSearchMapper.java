package dig.france.insee.sirene.search.response;

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

    public abstract SearchReportDto apiResponseToDto(SireneSearchResponse apiResponse);

    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "unit.lastModifiedDate", qualifiedByName = "toInstant"),
            @Mapping(target = "firstNames", expression = "java(unit.firstNames())"),
            @Mapping(target = "type", expression = "java(unit.inferUnitType())")
    })
    abstract SearchReportDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit unit,
                                                           SiretSearchResponse siretResponse); //TODO -> set establishments

    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", qualifiedByName = "toInstant"),
            @Mapping(target = "firstNames", expression = "java(unit.firstNames())"),
            @Mapping(target = "type", expression = "java(unit.inferUnitType())")
    })
    abstract SearchReportDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit unit);

    @Mappings({
            @Mapping(target = "changes", expression = "java(unitPeriod.getPeriodChanges())"),
            @Mapping(target = "companyNames", expression = "java(unitPeriod.companyNames())"),
    })
    abstract SearchReportDto.PeriodDto toPeriodDto(SireneSearchResponse.Period unitPeriod);

    @Named("toInstant")
    protected Instant toInstant(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);

        ZoneId brusselsZone = ZoneId.of("Europe/Brussels");
        return localDateTime.atZone(brusselsZone).toInstant();
    }

    public SearchReportDto toReportDto(SireneSearchResponse apiResponse, SiretSearchResponse siretResponse) {
        return SearchReportDto.builder().build();
    }
}
