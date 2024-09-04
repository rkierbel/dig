package dig.france.insee.sirene.search.response;

import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;

import java.util.List;

public class SireneSearchResponseFixtures {

    public static SireneSearchResponse createSireneSearchResponse() {
        return new SireneSearchResponse(
                createSirenHeader(),
                createSireneUnits()
        );
    }

    public static SireneSearchResponse empty() {
        return new SireneSearchResponse(null, null);
    }

    static SireneSearchResponse.SireneHeader createSirenHeader() {
        return new SireneSearchResponse.SireneHeader(
                "Sample message",
                123
        );
    }

    static SireneSearchResponse.SireneUnit createSireneUnit() {
        return new SireneSearchResponse.SireneUnit(
                123456789,
                "2024-09-04",
                "Michael",
                "Gary",
                null,
                null,
                "Prison Mike",
                "2024-01-01T09:00:00.000",
                createPeriods()
        );
    }

    // the first period chronologically is the last item of the list
    static List<SireneSearchResponse.Period> createPeriods() {
        return List.of(createPeriod(false), createPeriod(true));
    }

    static SireneSearchResponse.Period createPeriod(boolean isFirst) {
        return new SireneSearchResponse.Period(
                isFirst ? "2023-01-01" : "2024-01-02",
                isFirst ? "2024-01-01" : null,
                isFirst ? AdministrativeStatus.A : AdministrativeStatus.C,
                "Scott",
                "Michael",
                "Dunder Mifflin Inc.",
                "Dunder Mifflin",
                null,
                null,
                "1000",
                "62.01Z",
                !isFirst,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    static List<SireneSearchResponse.SireneUnit> createSireneUnits() {
        return List.of(createSireneUnit());
    }

    static SireneSearchResponse.SireneUnit nullDataSireneUnit() {
        return new SireneSearchResponse.SireneUnit(
                null, null, null, null,
                null, null, null, null, null
        );
    }
}
