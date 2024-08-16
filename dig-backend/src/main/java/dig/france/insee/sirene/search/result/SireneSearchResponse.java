package dig.france.insee.sirene.search.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dig.france.insee.sirene.SireneConstants.*;


/**
 * The result of a Sirene query is provided in Json format, structured in 2 parts:<br>
 * - the header (not to be confused with the http header or the response header) which contains the return code and potentially an error message ;<br>
 * - the legal units, which include a list of unit-level variables; the list of all periods and, for each period, the list of current and historical variables.<br>
 * The results of non-historical values (current period) are sent before the periods table.
 * The periods array contains a number of periods in descending chronological order.
 */
@Serdeable
@Slf4j
public record SireneSearchResponse(SirenHeader header,
                                   @JsonProperty(SIRENE_UNITS) List<SireneUnit> sireneUnits) {

    @Serdeable
    record SirenHeader(String message,
                       Integer total) {
    }

    /**
     * The legal (Sirene) unit is a legal entity under public or private law.
     * This legal entity may be a legal person, whose existence is recognised by law independently of the persons or institutions that own it or are members of it ;
     * or a natural person, who, as a self-employed person, may carry on an economic activity.
     *
     * @field siren            Sole entrepreneurs, or natural persons, retain the same Siren number until they die.
     *                         Legal entities lose their legal personality when they cease their business activity.
     *                         If the business is resumed at a later date, a new Siren number will be assigned.
     *                         Identification numbers are unique: once a Siren number has been allocated,
     *                         it cannot be reused and allocated to a new Sirene unit, even if the business has ceased its activity.
     * @field creationDate
     * @field names1, 2, 3, 4  Only for natural persons, null if Sirene Unit is a legal entity.
     * @field commonFirstName  Only for natural persons, null if Sirene Unit is a legal entity.
     * @field lastModifiedDate
     * @field periods
     */
    @Serdeable
    protected record SireneUnit(String siren,
                                @JsonProperty(SIRENE_UNIT_CREATION_DATE) String creationDate,
                                @JsonProperty(FIRST_NAME) String firstName,
                                @JsonProperty(MIDDLE_NAME) String middleName,
                                @JsonProperty(THIRD_NAME) String thirdName,
                                @JsonProperty(FOURTH_NAME) String fourthName,
                                @JsonProperty(COMMON_FIRST_NAME) String commonFirstName,
                                @JsonProperty(LAST_MODIFIED_DATE) String lastModifiedDate,
                                @JsonProperty(UNIT_CHANGES) List<Period> periods) {

        public String firstNames() {
            return Stream.of(firstName, middleName, thirdName, fourthName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));
        }

        public UnitType inferUnitType() {
            return this.commonFirstName != null ? UnitType.NATURAL_PERSON : UnitType.LEGAL_ENTITY;
        }
    }

    /**
     * Each period corresponds to a time interval defined by a start date and an end date.
     * A variable that is not known during a period will be set to null.
     * The last period in the history in chronological order corresponds to the current period and has an end date of null.
     * A change of value for a variable in the history implies the creation of a new period.
     * Change indicators (true or false) are attached to each historicized variable and indicate whether the corresponding variable has been modified with respect to the previous period.
     * For the first period of the company's history, in chronological order, all indicators are set to false.
     * For a company whose historical variables have never been modified, the response will only include one period.
     *
     * @field startDate
     * @field endDate
     * @field administrativeStatus       Active or ceased activity.
     * @field naturalPersonLastName      Birth name of the natural person.
     *                                   This variable is set to null for legal entities.
     *                                   The Sirene directory only handles non-accented capital letters.
     *                                   Only the special characters hyphen (-) and apostrophe.
     *                                   The name may be set to null for a natural person (case of purged sireneUnits).
     * @field naturalPersonCommonName    Referred to in French as "nom d'usage".
     *                                   It might differ from the family name mentioned on the birth certificate. Null for legal entities.
     * @field companyName                Name under which the legal unit is declared.
     *                                   This variable is set to null for individuals.
     *                                   The name may sometimes contain a reference to the form of the company.
     * @field companyCommonName1, 2, 3   Name under which the legal entity is known from the public.
     * @field legalCategory              The legal category is an attribute of Sirene sireneUnits.
     *                                   For a natural person, it is always 1000, whether the person is a
     *                                   craftsman, trader, self-employed person, farmer or other, and cannot change.
     *                                   For legal entities, the legal category may change during the life of the company.
     * @field administrativeStatusChange For each period, each historicized variable is accompanied by another one indicating whether it has been modified or not.
     *                                   In other words, this indicates whether a given variable or set of variables is the reason for the creation of the period.
     *                                   These change indicators are prefixed by "changement" in the JSON response.
     *                                   They are suffixed by "Change" in the Period record.
     * @field naturalPersonNameChange
     * @field companyNameChange
     * @field legalCategoryChange
     * @field mainActivityChange
     */
    @Serdeable
    record Period(@JsonProperty(START_DATE) String startDate,
                  @JsonProperty(END_DATE) String endDate,
                  @JsonProperty(ADMIN_STATUS) AdministrativeStatus administrativeStatus,
                  @JsonProperty(NATURAL_PERSON_NAME) String naturalPersonLastName,
                  @JsonProperty(NATURAL_PERSON_COMMON_NAME) String naturalPersonCommonName,
                  @JsonProperty(COMPANY_NAME) String companyName,
                  @JsonProperty(COMPANY_COMMON_NAME_1) String companyCommonName1,
                  @JsonProperty(COMPANY_COMMON_NAME_2) String companyCommonName2,
                  @JsonProperty(COMPANY_COMMON_NAME_3) String companyCommonName3,
                  @JsonProperty(LEGAL_CATEGORY) String legalCategory,
                  @JsonProperty(MAIN_ACTIVITY) String mainActivity,
                  @JsonProperty(ADMIN_STATUS_CHANGE) boolean administrativeStatusChange,
                  @JsonProperty(NATURAL_PERSON_NAME_CHANGE) boolean naturalPersonNameChange,
                  @JsonProperty(NATURAL_PERSON_COMMON_NAME_CHANGE) boolean naturalPersonCommonNameChange,
                  @JsonProperty(COMPANY_NAME_CHANGE) boolean companyNameChange,
                  @JsonProperty(COMPANY_COMMON_NAME_CHANGE) boolean companyCommonNameChange,
                  @JsonProperty(LEGAL_CATEGORY_CHANGE) boolean legalCategoryChange,
                  @JsonProperty(MAIN_ACTIVITY_CHANGE) boolean mainActivityChange) {

        public String companyNames() {
            return Stream.of(companyName, companyCommonName1, companyCommonName2, companyCommonName3)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));
        }

        public List<PeriodChange> getPeriodChanges() {
            return changeMap().entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .toList();
        }

        private Map<PeriodChange, Boolean> changeMap() {
            String companyCommonName = Stream.of(companyCommonName1, companyCommonName2, companyCommonName3)
                    .filter(Objects::nonNull).findFirst().orElse(null);

            Map<PeriodChange, Boolean> changes = HashMap.newHashMap(7);
            changes.put(PeriodChange.of(ADMIN_STATUS_CHANGE, administrativeStatus.name()), administrativeStatusChange);
            changes.put(PeriodChange.of(NATURAL_PERSON_NAME_CHANGE, naturalPersonLastName), naturalPersonNameChange);
            changes.put(PeriodChange.of(NATURAL_PERSON_COMMON_NAME_CHANGE, naturalPersonCommonName), naturalPersonCommonNameChange);
            changes.put(PeriodChange.of(COMPANY_NAME_CHANGE, companyName), companyNameChange);
            changes.put(PeriodChange.of(COMPANY_COMMON_NAME_CHANGE, companyCommonName), companyCommonNameChange);
            changes.put(PeriodChange.of(LEGAL_CATEGORY_CHANGE, legalCategory), legalCategoryChange);
            changes.put(PeriodChange.of(MAIN_ACTIVITY_CHANGE, mainActivity), mainActivityChange);
            return changes;
        }
    }
}
