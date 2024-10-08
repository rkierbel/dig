package dig.france.insee.sirene.search.response;

import java.util.List;
import java.util.stream.Collectors;

class ResponseLoggingHelper {

    private static final ResponseLoggingHelper INSTANCE = new ResponseLoggingHelper();
    private final String COMMA_BR = ",\n";
    private final String TAB_2 = "\t\t";
    private final String TAB_3 = "\t\t\t";

    public static ResponseLoggingHelper instance() {
        return INSTANCE;
    }

    String toString(SearchResponseDto dto) {
        return "SireneSearchResultDto {\n" +
                "\tsireneUnits: " + (dto.sireneUnits() == null ? "null" :
                dto.sireneUnits().stream()
                        .map(unit -> "\n" + TAB_2 + unit.toString().replace("\n", "\n" + TAB_2))
                        .collect(Collectors.joining(",")) + "\n\t") +
                "\n}";
    }

    String toString(SearchResponseDto.SireneUnitDto dto) {
        String TAB = "\t";

        return "SireneUnitDto {\n" +
                TAB + "siren=" + dto.siren() + COMMA_BR +
                TAB + "creationDate: " + dto.creationDate() + COMMA_BR +
                TAB + "lastModifiedDate: " + dto.lastModifiedDate() + COMMA_BR +
                TAB + "type: " + dto.type() + COMMA_BR +
                TAB + "commonFirstName: " + (dto.commonFirstName() != null ? dto.commonFirstName() : "null - legal entity") + COMMA_BR +
                TAB + "firstNames: " + (dto.firstNames() != null ? dto.firstNames() : "null - legal entity") + COMMA_BR +
                TAB + "periods: " + formatList(dto.periods()) +
                TAB + "establishments: " + formatEstablishments(dto.establishments()) +
                "\n}";
    }

    String toString(SearchResponseDto.PeriodDto dto) {
        return "PeriodDto {\n" +
                TAB_2 + "changes: " + formatChanges(dto.changes(), TAB_2) + COMMA_BR +
                TAB_2 + "administrative status: " + dto.administrativeStatus() + COMMA_BR +
                TAB_2 + "naturalPersonLastName: " + dto.naturalPersonLastName() + COMMA_BR +
                TAB_2 + "legalCategory: " + dto.legalCategory() + COMMA_BR +
                TAB_2 + "mainActivity: " + dto.mainActivity() + COMMA_BR +
                TAB_2 + "companyNames: " + (dto.companyNames() != null && !dto.companyNames().isEmpty() ? dto.companyNames() : "null - natural person") + COMMA_BR +
                TAB_2 + "startDate: " + dto.startDate() + COMMA_BR +
                TAB_2 + "endDate: " + dto.endDate() + "\n" +
                "}";
    }

    String toString(SearchResponseDto.EstablishmentDto dto) {
        return "EstablishmentDto {\n" +
                TAB_2 + "siret: " + dto.siret() + COMMA_BR +
                TAB_2 + "establishmentCreationDate: " + dto.establishmentCreationDate() + COMMA_BR +
                TAB_2 + "employeeHeadcountBand: " + dto.employeeHeadcountBand() + COMMA_BR +
                TAB_2 + "employeeHeadcountValidityYear: " + dto.employeeHeadcountValidityYear() + COMMA_BR +
                TAB_2 + "tradeRegisterMainActivity: " + dto.tradeRegisterMainActivity() + COMMA_BR +
                TAB_2 + "establishmentLastModifiedDate: " + dto.establishmentLastModifiedDate() + COMMA_BR +
                TAB_2 + "isHead: " + dto.isHead() + COMMA_BR +
                TAB_2 + "address: " + (dto.address() != null ? toString(dto.address()) : "null") + COMMA_BR +
                TAB_2 + "address2: " + (dto.address2() != null ? toString(dto.address2()) : "null") + COMMA_BR +
                TAB_2 + "establishmentPeriods: " + formatList(dto.establishmentPeriods()) +
                "}";
    }

    String toString(SearchResponseDto.EstablishmentAddressDto dto) {
        return "EstablishmentAddressDto {\n" +
                TAB_3 + "addressSupplement: " + dto.addressSupplement() + COMMA_BR +
                TAB_3 + "roadNumber: " + dto.roadNumber() + COMMA_BR +
                TAB_3 + "repetitionIndex: " + dto.repetitionIndex() + COMMA_BR +
                TAB_3 + "roadType: " + dto.roadType() + COMMA_BR +
                TAB_3 + "roadName: " + dto.roadName() + COMMA_BR +
                TAB_3 + "postalCode: " + dto.postalCode() + COMMA_BR +
                TAB_3 + "municipalityName: " + dto.municipalityName() + COMMA_BR +
                TAB_3 + "foreignMunicipalityName: " + dto.foreignMunicipalityName() + COMMA_BR +
                TAB_3 + "municipalityCode: " + dto.municipalityCode() + COMMA_BR +
                TAB_3 + "codeCedex: " + dto.codeCedex() + COMMA_BR +
                TAB_3 + "wordedCedex: " + dto.wordedCedex() + COMMA_BR +
                TAB_3 + "foreignCountryCode: " + dto.foreignCountryCode() + COMMA_BR +
                TAB_3 + "foreignCountryName: " + dto.foreignCountryName() + "\n" +
                TAB_2 + "}";
    }

    String toString(SearchResponseDto.EstablishmentPeriodDto dto) {
        String TAB_4 = "\t\t\t\t";

        return "EstablishmentPeriodDto {\n" +
                TAB_4 + "changes: " + formatChanges(dto.changes(), TAB_4) + COMMA_BR +
                TAB_4 + "startDate: " + dto.startDate() + COMMA_BR +
                TAB_4 + "endDate: " + dto.endDate() + COMMA_BR +
                TAB_4 + "administrativeStatus: " + dto.administrativeStatus() + COMMA_BR +
                TAB_4 + "sign: " + dto.sign() + COMMA_BR +
                TAB_4 + "commonName: " + dto.commonName() + COMMA_BR +
                TAB_4 + "mainActivity: " + dto.mainActivity() + COMMA_BR +
                TAB_4 + "employerType: " + dto.employerType() + "\n" +
                "}";
    }

    private <ELEMENT> String formatList(List<ELEMENT> elements) {
        if (elements == null) return "null\n";
        return elements.stream()
                .map(e -> switch (e) {
                    case SearchResponseDto.PeriodDto p ->
                            "\n" + TAB_2 + toString(p).replace("\n", "\n" + TAB_2);
                    case SearchResponseDto.EstablishmentPeriodDto ep ->
                            "\n" + TAB_3 + toString(ep).replace("\n", "\n" + TAB_3);
                    default -> "null";
                })
                .collect(Collectors.joining(",")) + "\n";
    }

    private String formatEstablishments(List<SearchResponseDto.EstablishmentDto> establishments) {
        if (establishments == null) return "null\n";
        return establishments.stream()
                .map(e -> "\n" + TAB_2 + toString(e).replace("\n", "\n" + TAB_2))
                .collect(Collectors.joining(",")) + "\n";
    }

    private String formatChanges(List<PeriodChange> changes, String indent) {
        if (changes == null) return "null";
        return changes.stream()
                .map(change -> change.toChangeString(indent))
                .collect(Collectors.joining(", ", "[", "\n" + indent + "]"));
    }
}
