package dig.france.insee.sirene.search.result;

import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "jsr330")
@Singleton
public abstract class SireneSearchMapper {

    public abstract SireneSearchResultDto apiResponseToDto(SireneSearchResponse apiResponse);

    abstract SireneSearchResultDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit sireneUnit);

    @Mappings({
            @Mapping(target = "changes", expression = "java(unitPeriod.getPeriodChanges())")
    })
    abstract SireneSearchResultDto.PeriodDto toPeriodDto(SireneSearchResponse.Period unitPeriod);
}
