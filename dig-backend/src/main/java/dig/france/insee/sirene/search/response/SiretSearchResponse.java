package dig.france.insee.sirene.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;
import io.micronaut.serde.annotation.Serdeable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dig.france.insee.sirene.SireneConstants.*;
import dig.france.insee.sirene.search.response.PeriodChange.Reason;

@Serdeable
public record SiretSearchResponse(Header header,
                                  @JsonProperty(ESTABLISHMENTS) List<Establishment> establishments) {

    @Serdeable
    record Header(int statut, String message, int total, int nombre) {
    }

    /**
     *
     * @field nic
     * @field siret
     * @field establishmentCreationDate
     * @field employeeHeadcountBand             Updated once a year in the fall.
     * @field employeeHeadcountValidityYear     Year when the employee head count band was last actualised.
     *                                          For active units that are not updated two years in a row, this variable is set to null.
     * @field tradeRegisterMainActivity         Only concerns establishments in the craft sector (craftsmen, craft traders and craft companies).
     * @field establishmentLastModifiedDate
     * @field isHead                            Determines whether the establishment is the legal unit head or not.
     * @field address2
     * @field establishmentPeriods
     */
    @Serdeable
    record Establishment(String siren,
                         String nic,
                         String siret,
                         @JsonProperty(ESTABLISHMENT_CREATION_DATE) String establishmentCreationDate,
                         @JsonProperty(EMPLOYEE_HEADCOUNT_BAND) String employeeHeadcountBand,
                         @JsonProperty(EMPLOYEE_HEADCOUNT_VALIDITY_YEAR) String employeeHeadcountValidityYear,
                         @JsonProperty(TRADE_REGISTER_MAIN_ACTIVITY) String tradeRegisterMainActivity,
                         @JsonProperty(ESTABLISHMENT_LAST_MODIFIED_DATE) String establishmentLastModifiedDate,
                         @JsonProperty(IS_HEAD) boolean isHead,
                         @JsonProperty(NUMBER_ESTABLISHMENT_PERIODS) int numberEstablishmentPeriods,
                         @JsonProperty(ADDRESS) Address address,
                         @JsonProperty(ADDRESS_2) Address address2,
                         @JsonProperty(ESTABLISHMENT_PERIODS) List<EstablishmentPeriod> establishmentPeriods) {
    }

    /**
     *
     * @field repetitionIndex                  B -> bis, T -> ter, etc.
     */
    @Serdeable
    record Address(@JsonProperty(ADDRESS_SUPPLEMENT) String addressSupplement,
                   @JsonProperty(ROAD_NUMBER) String roadNumber,
                   @JsonProperty(REPETITION_INDEX) String repetitionIndex,
                   @JsonProperty(LAST_ROAD_NUMBER) String lastRoadNumber,
                   @JsonProperty(REPETITION_INDEX_LAST_ROAD_NUMBER) String repetitionIndexLastRoadNumber,
                   @JsonProperty(ROAD_TYPE) String roadType,
                   @JsonProperty(ROAD_NAME) String roadName,
                   @JsonProperty(POSTAL_CODE) String postalCode,
                   @JsonProperty(MUNICIPALITY_NAME) String municipalityName,
                   @JsonProperty(FOREIGN_MUNICIPALITY_NAME) String foreignMunicipalityName,
                   @JsonProperty(SPECIAL_DISTRIBUTION) String specialDistribution,
                   @JsonProperty(MUNICIPALITY_CODE) String municipalityCode,
                   @JsonProperty(CODE_CEDEX) String codeCedex,
                   @JsonProperty(WORDED_CEDEX) String wordedCedex,
                   @JsonProperty(FOREIGN_COUNTRY_CODE) String foreignCountryCode,
                   @JsonProperty(FOREIGN_COUNTRY_NAME) String foreignCountryName,
                   @JsonProperty(ADDRESS_IDENTIFIER) String addressIdentifier,
                   @JsonProperty(COORDINATE_ABSCISSA) String coordinateAbscissa,
                   @JsonProperty(COORDINATE_ORDINATE) String coordinateOrdinate) {
    }

    /**
     *
     * @field sign1                         The sign identifies the location or premises where the activity is carried out.
     *                                      An establishment may have one sign, several signs or none at all.
     *                                      If sign 1 is set to null, the other two are also set to null; if sign 2 is set to null, sign 3 is also set to null.
     *                                      The analysis of the signs and its division into three variables in Sirene shows two possible cases: either the 3 fields concern 3 quite distinct signs;
     *                                      or these three fields correspond to the division of the sign with continuity of the three fields.
     *                                      This variable is optional.
     * @field sign2
     * @field sign3
     * @field commonName                    Designates the name by which the establishment is known to the public (trade name of the establishment).
     * @field mainActivity                  The establishment's main activity for the period.
     *                                      When an establishment is entered in the Siret register, Insee assigns it a code based on the description of its main activity provided by the registrant.
     *                                      This code can be changed during the life of the establishment, depending on the declarations made by the registrant.
     *                                      For each establishment, there is only one  code at any given time.
     * @field mainActivityNomenclature
     * @field employerType
     */
    @Serdeable
    record EstablishmentPeriod(@JsonProperty(START_DATE) String startDate,
                               @JsonProperty(END_DATE) String endDate,
                               @JsonProperty(ESTABLISHMENT_ADMIN_STATUS) AdministrativeStatus administrativeStatus,
                               @JsonProperty(ESTABLISHMENT_SIGN_1) String sign1,
                               @JsonProperty(ESTABLISHMENT_SIGN_2) String sign2,
                               @JsonProperty(ESTABLISHMENT_SIGN_3) String sign3,
                               @JsonProperty(ESTABLISHMENT_COMMON_NAME) String commonName,
                               @JsonProperty(ESTABLISHMENT_MAIN_ACTIVITY) String mainActivity,
                               @JsonProperty(ESTABLISHMENT_MAIN_ACTIVITY_NOMENCLATURE) String mainActivityNomenclature,
                               @JsonProperty(EMPLOYER_TYPE) String employerType,
                               @JsonProperty(ESTABLISHMENT_ADMIN_STATUS_CHANGE) boolean administrativeStatusChange,
                               @JsonProperty(ESTABLISHMENT_NAME_CHANGE) boolean signChange,
                               @JsonProperty(ESTABLISHMENT_COMMON_NAME_CHANGE) boolean commonNameChange,
                               @JsonProperty(ESTABLISHMENT_MAIN_ACTIVITY_CHANGE) boolean mainActivityChange,
                               @JsonProperty(EMPLOYER_TYPE_CHANGE) boolean employerTypeChange) {

        public String sign() {
            return Stream.of(sign1, sign2, sign3)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));
        }

        public String adminStatusMeaning() {
            return administrativeStatus.meaning();
        }

        //TODO -> abstract
        public List<PeriodChange> getPeriodChanges() {
            return changeMap().entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();
        }

        private Map<PeriodChange, Boolean> changeMap() {
            String sign = sign();
            String adminStatus = administrativeStatus != null ? administrativeStatus.meaning() : null;

            Map<PeriodChange, Boolean> changes = HashMap.newHashMap(7);
            changes.put(PeriodChange.of(Reason.ESTABLISHMENT_ADMIN_STATUS_CHANGE, adminStatus), administrativeStatusChange);
            changes.put(PeriodChange.of(Reason.ESTABLISHMENT_NAME_CHANGE, sign), signChange);
            changes.put(PeriodChange.of(Reason.ESTABLISHMENT_COMMON_NAME_CHANGE, commonName), commonNameChange);
            changes.put(PeriodChange.of(Reason.ESTABLISHMENT_MAIN_ACTIVITY_CHANGE, mainActivity), mainActivityChange);
            changes.put(PeriodChange.of(Reason.EMPLOYER_TYPE_CHANGE, employerType), employerTypeChange);
            return changes;
        }
    }
}