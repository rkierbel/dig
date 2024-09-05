package dig.france.insee.sirene.search.response;

import dig.common.util.DateTimeUtil;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static dig.common.util.DateTimeUtil.ZONE_BRUSSELS;

@Mapper(
        componentModel = "jsr330", //TODO -> explain
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
@Singleton
@Primary
@Slf4j
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
            @Mapping(target = "establishments", expression = "java(initEstablishments())")
    })
    abstract SearchReportDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit unit,
                                                           SiretSearchResponse siretResponse);

    ArrayList<SearchReportDto.EstablishmentDto> initEstablishments() {
        return new ArrayList<>();
    }

    @Mappings({
            @Mapping(target = "establishmentLastModifiedDate", qualifiedByName = "toInstant")
    })
    abstract SearchReportDto.EstablishmentDto toEstablishmentDto(SiretSearchResponse.Establishment establishment);

    @Mappings({
            @Mapping(target = "changes", expression = "java(establishmentPeriod.getPeriodChanges())"),
            @Mapping(target = "sign", expression = "java(establishmentPeriod.sign())"),
    })
    abstract SearchReportDto.EstablishmentPeriodDto toEstablishmentPeriodDto(
            SiretSearchResponse.EstablishmentPeriod establishmentPeriod);

    @Mappings({
            @Mapping(target = "changes", expression = "java(unitPeriod.getPeriodChanges())"),
            @Mapping(target = "companyNames", expression = "java(unitPeriod.companyNames())"),
    })
    abstract SearchReportDto.PeriodDto toPeriodDto(SireneSearchResponse.Period unitPeriod);

    @Named("toInstant")
    Instant toInstant(String input) {
        try {
            return Optional.ofNullable(input)
                    .map(str -> LocalDateTime.parse(str, DateTimeUtil.FORMATTER).atZone(ZONE_BRUSSELS).toInstant())
                    .orElseThrow(DateTimeUtil::nullOrEmptyParseEx);
        } catch (DateTimeParseException dateTimeParseEx) {
            log.warn("[SireneSearchMapper::toInstant] threw DateTimeParseException on input {} at index {}",
                    input, dateTimeParseEx.getErrorIndex());
            return null;
        }
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
        try {
            return Integer.parseInt(establishment.siren()) == unitSiren;
        } catch (NumberFormatException numFmtEx) {
            log.warn("[SireneSearchMapper::sirenMatch] threw NumberFormatException on input {}", establishment.siren());
            return false;
        }
    }
}
