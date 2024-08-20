package dig.france.insee.sirene.search.response;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "jsr330") //TODO -> explain
@Singleton
@Primary
public abstract class SireneSearchMapper {

    public SearchReportDto toReport(SireneSearchResponse sireneResponse,
                                    SiretSearchResponse siretResponse) {
        return SearchReportDto.builder()
                .sireneUnits(
                        sireneResponse.sireneUnits()
                                .stream()
                                .map(unit -> toSireneUnitDto(unit, siretResponse))
                                .toList())
                .build();
    }

    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "unit.lastModifiedDate", qualifiedByName = "toInstant"),
            @Mapping(target = "firstNames", expression = "java(unit.firstNames())"),
            @Mapping(target = "type", expression = "java(unit.inferUnitType())"),
            @Mapping(target = "establishments", qualifiedByName = "initEstablishments")
    })
    abstract SearchReportDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit unit,
                                                           SiretSearchResponse siretResponse);

    @Named("initEstablishments")
    ArrayList<SearchReportDto.EstablishmentDto> initEstablishments(List<SiretSearchResponse.Establishment> establishments) {
        return new ArrayList<>(establishments.size());
    }

    @Mappings({
            @Mapping(target = "establishmentLastModifiedDate", qualifiedByName = "toInstant")
    })
    abstract SearchReportDto.EstablishmentDto toEstablishmentDto(SiretSearchResponse.Establishment establishment);

    @Mappings({
            @Mapping(target = "changes", expression = "java(establishmentPeriod.getPeriodChanges())"),
            @Mapping(target = "sign", expression = "java(establishmentPeriod.sign())"),
    })
    abstract SearchReportDto.EstablishmentDto.EstablishmentPeriodDto toEstablishmentPeriodDto(
            SiretSearchResponse.EstablishmentPeriod establishmentPeriod);

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

    @AfterMapping
    void addEstablishments(@MappingTarget SearchReportDto.SireneUnitDto unitDto,
                           SiretSearchResponse siretResponse) {
        Integer siren = unitDto.siren();
        Optional.ofNullable(siretResponse.establishments())
                .orElse(Collections.emptyList())
                .stream()
                .filter(e -> sirenMatch(e, siren))
                .forEach(e -> unitDto.addEstablishment(this.toEstablishmentDto(e)));
    }

    private static boolean sirenMatch(SiretSearchResponse.Establishment establishment, Integer unitSiren) {
        return Integer.parseInt(establishment.siren()) == unitSiren;
    }
}
