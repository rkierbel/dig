package dig.france.insee.sirene.search.result;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class SireneResponseMapper {

    public abstract SireneSearchResultDto apiResponseToDto(SireneSearchResponse apiResponse);

    abstract SireneSearchResultDto.SireneUnitDto toSireneUnitDto(SireneSearchResponse.SireneUnit sireneUnit);

    @Mappings({
            @Mapping(target = "", expression = "")
    })
    abstract SireneSearchResultDto.PeriodDto toPeriodDto(SireneSearchResponse.Period unitPeriod);

}
