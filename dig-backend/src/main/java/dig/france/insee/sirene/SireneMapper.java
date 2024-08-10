package dig.france.insee.sirene;

import dig.france.insee.sirene.search.result.SireneResponseMapper;
import dig.france.insee.sirene.search.result.SireneSearchResponse;
import dig.france.insee.sirene.search.result.SireneSearchResultDto;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@Singleton
public abstract class SireneMapper {

    private final SireneResponseMapper responseMapper = Mappers.getMapper(SireneResponseMapper.class);

    public SireneSearchResultDto apiResponseToDto(SireneSearchResponse apiResponse) {
        return responseMapper.apiResponseToDto(apiResponse);
    }
}
