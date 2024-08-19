package dig.france.insee.sirene.search.response;

import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;
import dig.france.insee.sirene.search.response.enumerated.UnitType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Serdeable
@Builder(toBuilder = true)
public record SearchReportDto(List<SireneUnitDto> sireneUnits) {

    private static final String COMMA_BR = ",\n";

    public static SearchReportDto emptyReport() {
        return SearchReportDto.builder().build();
    }

    @Override
    public String toString() {
        return "SireneSearchResultDto {\n" +
                "\tsireneUnits: " + (sireneUnits == null ? "null" :
                sireneUnits.stream()
                        .map(unit -> "\n\t\t" + unit.toString().replace("\n", "\n\t\t"))
                        .collect(Collectors.joining(",")) + "\n\t") +
                "\n}";
    }

    @Serdeable
    record SireneUnitDto(Integer siren,
                         LocalDate creationDate,
                         Instant lastModifiedDate,
                         UnitType type,
                         String commonFirstName,
                         String firstNames,
                         List<PeriodDto> periods,
                         List<EstablishmentDto> establishments) {

        public void addEstablishment(EstablishmentDto dto) {
            establishments.add(dto);
        }

        @Override
        public String toString() {
            return "SireneUnitDto {\n" +
                    "\tsiren=" + siren + COMMA_BR +
                    "\tcreationDate: " + creationDate + COMMA_BR +
                    "\tlastModifiedDate: " + lastModifiedDate + COMMA_BR +
                    "\ttype: " + type + COMMA_BR +
                    "\tcommonFirstName: " + (commonFirstName != null ? commonFirstName : "null - legal entity") + COMMA_BR +
                    "\tfirstNames: " + (firstNames != null ? firstNames : "null - legal entity") + COMMA_BR +
                    "\tperiods: " + (periods == null ? "null" :
                    periods.stream()
                            .map(period -> "\n\t\t" + period.toString().replace("\n", "\n\t\t"))
                            .collect(Collectors.joining(",")) + "\n\t") +
                    "\n}";
        }
    }

    @Serdeable
    record PeriodDto(List<PeriodChange> changes,
                     String naturalPersonLastName,
                     String companyNames,
                     String legalCategory,
                     String mainActivity,
                     LocalDate startDate,
                     LocalDate endDate) {
        @Override
        public String toString() {
            return "PeriodDto {\n" +
                    "\t\tchanges: " + (changes == null ? "null" :
                    changes.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ", "[", "]"))) + COMMA_BR +
                    "\t\tnaturalPersonLastName: " + naturalPersonLastName + COMMA_BR +
                    "\t\tlegalCategory: " + legalCategory + COMMA_BR +
                    "\t\tmainActivity: " + mainActivity + COMMA_BR +
                    "\t\tcompanyNames: " + (companyNames != null && !companyNames.isEmpty() ? companyNames : "null - natural person") + COMMA_BR +
                    "\t\tstartDate: " + startDate + COMMA_BR +
                    "\t\tendDate: " + endDate + "\n" +
                    "}";
        }
    }

    @Serdeable
    record EstablishmentDto(Integer siret,
                            Instant establishmentCreationDate,
                            String employeeHeadcountBand,
                            String employeeHeadcountValidityYear,
                            String tradeRegisterMainActivity,
                            Instant establishmentLastModifiedDate,
                            boolean isHead,
                            EstablishmentAddressDto address,
                            EstablishmentAddressDto address2,
                            List<EstablishmentPeriodDto> establishmentPeriods) {
        @Override
        public String toString() {
            return "EstablishmentDto {\n" +
                    "\t\t\tsiret: " + siret + COMMA_BR +
                    "\t\t\testablishmentCreationDate: " + establishmentCreationDate + COMMA_BR +
                    "\t\t\temployeeHeadcountBand: " + employeeHeadcountBand + COMMA_BR +
                    "\t\t\temployeeHeadcountValidityYear: " + employeeHeadcountValidityYear + COMMA_BR +
                    "\t\t\ttradeRegisterMainActivity: " + tradeRegisterMainActivity + COMMA_BR +
                    "\t\t\testablishmentLastModifiedDate: " + establishmentLastModifiedDate + COMMA_BR +
                    "\t\t\tisHead: " + isHead + COMMA_BR +
                    "\t\t\taddress: " + (address != null ? address.toString() : "null") + COMMA_BR +
                    "\t\t\taddress2: " + (address2 != null ? address2.toString() : "null") + COMMA_BR +
                    "\t\t\testablishmentPeriods: " + (establishmentPeriods == null ? "null" :
                    establishmentPeriods.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ", "[", "]"))) + "\n" +
                    "}";
        }

        @Serdeable
        record EstablishmentAddressDto(String addressSupplement,
                                       String roadNumber,
                                       String repetitionIndex,
                                       String roadType,
                                       String roadName,
                                       String postalCode,
                                       String municipalityName,
                                       String foreignMunicipalityName,
                                       String municipalityCode,
                                       String codeCedex,
                                       String wordedCedex,
                                       String foreignCountryCode,
                                       String foreignCountryName) {
            @Override
            public String toString() {
                return "EstablishmentAddressDto {\n" +
                        "\t\t\taddressSupplement: " + addressSupplement + COMMA_BR +
                        "\t\t\troadNumber: " + roadNumber + COMMA_BR +
                        "\t\t\trepetitionIndex: " + repetitionIndex + COMMA_BR +
                        "\t\t\troadType: " + roadType + COMMA_BR +
                        "\t\t\troadName: " + roadName + COMMA_BR +
                        "\t\t\tpostalCode: " + postalCode + COMMA_BR +
                        "\t\t\tmunicipalityName: " + municipalityName + COMMA_BR +
                        "\t\t\tforeignMunicipalityName: " + foreignMunicipalityName + COMMA_BR +
                        "\t\t\tmunicipalityCode: " + municipalityCode + COMMA_BR +
                        "\t\t\tcodeCedex: " + codeCedex + COMMA_BR +
                        "\t\t\twordedCedex: " + wordedCedex + COMMA_BR +
                        "\t\t\tforeignCountryCode: " + foreignCountryCode + COMMA_BR +
                        "\t\t\tforeignCountryName: " + foreignCountryName + "\n" +
                        "}";
            }
        }

        @Serdeable
        record EstablishmentPeriodDto(List<PeriodChange> changes,
                                      LocalDate startDate,
                                      LocalDate endDate,
                                      AdministrativeStatus administrativeStatus,
                                      String sign,
                                      String commonName,
                                      String mainActivity,
                                      String employerType) {
            @Override
            public String toString() {
                return "EstablishmentPeriodDto {\n" +
                        "\t\t\t\tchanges: " + (changes == null ? "null" :
                        changes.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", ", "[", "]"))) + COMMA_BR +
                        "\t\t\t\tstartDate: " + startDate + COMMA_BR +
                        "\t\t\t\tendDate: " + endDate + COMMA_BR +
                        "\t\t\t\tadministrativeStatus: " + administrativeStatus + COMMA_BR +
                        "\t\t\t\tsign: " + sign + COMMA_BR +
                        "\t\t\t\tcommonName: " + commonName + COMMA_BR +
                        "\t\t\t\tmainActivity: " + mainActivity + COMMA_BR +
                        "\t\t\t\temployerType: " + employerType + "\n" +
                        "}";
            }
        }
    }
}
