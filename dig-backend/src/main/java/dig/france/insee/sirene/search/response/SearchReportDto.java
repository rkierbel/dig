package dig.france.insee.sirene.search.response;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Serdeable
@Builder(toBuilder = true)
public record SearchReportDto(List<SireneUnitDto> sireneUnits) {


    public static SearchReportDto emptyReport() {
        return SearchReportDto.builder().build();
    }

    @Override
    public String toString() {
        return ResponseLoggingHelper.instance().toString(this);
    }

    @Serdeable
    record SireneUnitDto(Integer siren,
                         LocalDate creationDate,
                         Instant lastModifiedDate,
                         String type,
                         String commonFirstName,
                         String firstNames,
                         List<PeriodDto> periods,
                         List<EstablishmentDto> establishments) {

        public void addEstablishment(EstablishmentDto dto) {
            establishments.add(dto);
        }

        @Override
        public String toString() {
            return ResponseLoggingHelper.instance().toString(this);
        }
    }

    @Serdeable
    record PeriodDto(List<PeriodChange> changes,
                     String administrativeStatus,
                     String naturalPersonLastName,
                     String naturalPersonCommonName,
                     String companyNames,
                     String legalCategory,
                     String mainActivity,
                     LocalDate startDate,
                     LocalDate endDate) {

        @Override
        public String toString() {
            return ResponseLoggingHelper.instance().toString(this);
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
            return ResponseLoggingHelper.instance().toString(this);
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
            return ResponseLoggingHelper.instance().toString(this);
        }
    }

    @Serdeable
    record EstablishmentPeriodDto(List<PeriodChange> changes,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  String administrativeStatus,
                                  String sign,
                                  String commonName,
                                  String mainActivity,
                                  String employerType) {
        @Override
        public String toString() {
            return ResponseLoggingHelper.instance().toString(this);
        }
    }
}
