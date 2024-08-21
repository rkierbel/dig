package dig.france.insee.sirene.messaging.event;

import dig.france.insee.sirene.search.response.SearchReportDto;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Serdeable
public class CompletedSireneSearchEvent {

    private final UUID id = UUID.randomUUID();
    @Setter private SearchReportDto reportDto;
}
