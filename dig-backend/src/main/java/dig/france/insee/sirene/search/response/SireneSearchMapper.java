package dig.france.insee.sirene.search.response;

import dig.common.util.DateTimeUtil;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static dig.common.util.DateTimeUtil.ZONE_BRUSSELS;

@Mapper(componentModel = "jsr330") //TODO -> explain
@Singleton
@Primary
public abstract class SireneSearchMapper {

    public SearchReportDto toReport(SireneSearchResponse sireneResponse,
                                    SiretSearchResponse siretResponse) {
        if (sireneResponse == null || siretResponse == null) {
            return SearchReportDto.emptyReport();
        }
        return SearchReportDto.builder()
                .sireneUnits(
                        Optional.ofNullable(sireneResponse.sireneUnits())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(unit -> toSireneUnitDto(unit, siretResponse))
                                .toList())
                .build();
    }

    @Mappings({
            @Mapping(target = "lastModifiedDate", source = "unit.lastModifiedDate", qualifiedByName = "toInstant"),
            @Mapping(target = "firstNames", expression = "java(unit != null ? unit.firstNames() : null)"),
            @Mapping(target = "type", expression = "java(unit != null ? unit.inferUnitType() : null)"),
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
        return Optional.ofNullable(input)
                .map(str -> LocalDateTime.parse(str, DateTimeUtil.FORMATTER).atZone(ZONE_BRUSSELS).toInstant())
                .orElse(null);
    }

    @AfterMapping
    void addEstablishments(@MappingTarget SearchReportDto.SireneUnitDto unitDto,
                           SiretSearchResponse siretResponse) {
        if (siretResponse == null || unitDto == null || unitDto.siren() == null) return;

        Integer siren = unitDto.siren();
        Optional.ofNullable(siretResponse.establishments())
                .orElse(Collections.emptyList())
                .stream()
                .filter(e -> sirenMatch(e, siren))
                .forEach(e -> unitDto.addEstablishment(this.toEstablishmentDto(e)));
    }

    private static boolean sirenMatch(SiretSearchResponse.Establishment establishment, Integer unitSiren) {
        return Integer.parseInt(establishment.siren()) == unitSiren; // TODO -> handle NumberFormatException
    }
}
