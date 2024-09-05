package dig.france.insee.sirene.search.response;

import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;
import dig.france.insee.sirene.search.response.enumerated.UnitType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static dig.common.util.LogUtil.COMMA_BR;
import static dig.common.util.LogUtil.TAB_2;
import static dig.common.util.LogUtil.TAB_3;
import static dig.common.util.LogUtil.TAB_4;

@Serdeable
@Builder(toBuilder = true)
public record SearchReportDto(List<SireneUnitDto> sireneUnits) {


    public static SearchReportDto emptyReport() {
        return SearchReportDto.builder().build();
    }

    @Override
    public String toString() {
        return "SireneSearchResultDto {\n" +
                "\tsireneUnits: " + (sireneUnits == null ? "null" :
                sireneUnits.stream()
                        .map(unit -> "\n"+ TAB_2 + unit.toString().replace("\n", "\n"+ TAB_2))
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
                            .map(period -> "\n"+ TAB_2 + period.toString().replace("\n", "\n"+ TAB_2))
                            .collect(Collectors.joining(",")) + "\n") +
                    "\testablishments: " + (establishments == null ? "null" :
                    establishments.stream()
                            .map(e -> "\n"+ TAB_2 + e.toString().replace("\n", "\n"+ TAB_2))
                            .collect(Collectors.joining(","))) +
                    "\n}";
        }
    }

    @Serdeable
    record PeriodDto(List<PeriodChange> changes,
                     String naturalPersonLastName,
                     String naturalPersonCommonName,
                     String companyNames,
                     String legalCategory,
                     String mainActivity,
                     LocalDate startDate,
                     LocalDate endDate) {

        @Override
        public String toString() {
            return "PeriodDto {\n" +
                    TAB_2 + "changes: " + (changes == null ? "null" :
                    changes.stream()
                            .map(change -> change.toChangeString(TAB_2))
                            .collect(Collectors.joining(", ", "[", "\n" + TAB_2 + "]"))) + COMMA_BR +
                    TAB_2 + "naturalPersonLastName: " + naturalPersonLastName + COMMA_BR +
                    TAB_2 + "legalCategory: " + legalCategory + COMMA_BR +
                    TAB_2 + "mainActivity: " + mainActivity + COMMA_BR +
                    TAB_2 + "companyNames: " + (companyNames != null && !companyNames.isEmpty() ? companyNames : "null - natural person") + COMMA_BR +
                    TAB_2 + "startDate: " + startDate + COMMA_BR +
                    TAB_2 + "endDate: " + endDate + "\n" +
                    "}";
        }
    }

    @Serdeable
    record EstablishmentDto(Long siret,
                            LocalDate establishmentCreationDate,
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
                    TAB_2 + "siret: " + siret + COMMA_BR +
                    TAB_2 + "establishmentCreationDate: " + establishmentCreationDate + COMMA_BR +
                    TAB_2 + "employeeHeadcountBand: " + employeeHeadcountBand + COMMA_BR +
                    TAB_2 + "employeeHeadcountValidityYear: " + employeeHeadcountValidityYear + COMMA_BR +
                    TAB_2 + "tradeRegisterMainActivity: " + tradeRegisterMainActivity + COMMA_BR +
                    TAB_2 + "establishmentLastModifiedDate: " + establishmentLastModifiedDate + COMMA_BR +
                    TAB_2 + "isHead: " + isHead + COMMA_BR +
                    TAB_2 + "address: " + (address != null ? address.toString() : "null") + COMMA_BR +
                    TAB_2 + "address2: " + (address2 != null ? address2.toString() : "null") + COMMA_BR +
                    TAB_2 + "establishmentPeriods: " + (establishmentPeriods == null ? "null" :
                    establishmentPeriods.stream()
                            .map(period -> "\n" + TAB_3 + period.toString().replace("\n", "\n" + TAB_3))
                            .collect(Collectors.joining(",")) + "\n") +
                    "}";
        }
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
                    TAB_3 + "addressSupplement: " + addressSupplement + COMMA_BR +
                    TAB_3 + "roadNumber: " + roadNumber + COMMA_BR +
                    TAB_3 + "repetitionIndex: " + repetitionIndex + COMMA_BR +
                    TAB_3 + "roadType: " + roadType + COMMA_BR +
                    TAB_3 + "roadName: " + roadName + COMMA_BR +
                    TAB_3 + "postalCode: " + postalCode + COMMA_BR +
                    TAB_3 + "municipalityName: " + municipalityName + COMMA_BR +
                    TAB_3 + "foreignMunicipalityName: " + foreignMunicipalityName + COMMA_BR +
                    TAB_3 + "municipalityCode: " + municipalityCode + COMMA_BR +
                    TAB_3 + "codeCedex: " + codeCedex + COMMA_BR +
                    TAB_3 + "wordedCedex: " + wordedCedex + COMMA_BR +
                    TAB_3 + "foreignCountryCode: " + foreignCountryCode + COMMA_BR +
                    TAB_3 + "foreignCountryName: " + foreignCountryName + "\n" +
                    TAB_2 + "}";
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
                    TAB_4 + "changes: " + (changes == null ? "null" :
                    changes.stream()
                            .map(change -> change.toChangeString(TAB_4))
                            .collect(Collectors.joining(", ", "[", "\n" + TAB_4 + "]"))) + COMMA_BR +
                    TAB_4 + "startDate: " + startDate + COMMA_BR +
                    TAB_4 + "endDate: " + endDate + COMMA_BR +
                    TAB_4 + "administrativeStatus: " + administrativeStatus + COMMA_BR +
                    TAB_4 + "sign: " + sign + COMMA_BR +
                    TAB_4 + "commonName: " + commonName + COMMA_BR +
                    TAB_4 + "mainActivity: " + mainActivity + COMMA_BR +
                    TAB_4 + "employerType: " + employerType + "\n" +
                    "}";
        }
    }
}
