package dig.france.insee.sirene.messaging.event;

import dig.france.insee.sirene.search.response.SearchResponseDto;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.util.UUID;

@Serdeable
@Builder(toBuilder = true)
public record SireneSearchCompletedEvent(String id,
                                         SearchResponseDto reportDto) {

    public static SireneSearchCompletedEvent emptyWithId() {
        return SireneSearchCompletedEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .reportDto(SearchResponseDto.emptyReport())
                .build();
    }

    public static SireneSearchCompletedEvent withReport(SearchResponseDto report) {
        return SireneSearchCompletedEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .reportDto(report)
                .build();
    }

    @Override
    public String toString() {
        return "{\nId: %s;\nReportDto: %s\n}".formatted(id, reportDto);
    }
}
