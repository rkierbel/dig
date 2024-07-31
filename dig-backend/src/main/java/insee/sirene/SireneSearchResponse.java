package insee.sirene;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static insee.sirene.SireneConstants.*;


@Serdeable
@Slf4j
public record SireneSearchResponse(SirenHeader header,
                                   @JsonProperty(SIRENE_UNITS) List<SireneUnit> units) {

    void logSirenResponseInfo() {
        units.forEach(
                unit -> {
                    log.info("-> Siren number {}, for natural person with first name {}:", unit.siren, unit.firstName);
                    unit.unitChanges.forEach(Period::logPeriodInfo);
                }
        );
    }

    @Serdeable
    record SirenHeader(String message,
                       Integer total) {
    }

    /**
     * The legal (Sirene) unit is a legal entity under public or private law.
     * This legal entity may be a legal person, whose existence is recognised by law independently of the persons or institutions that own it or are members of it ;
     * or a natural person, who, as a self-employed person, may carry on an economic activity.
     *
     * @param siren                  Sole entrepreneurs, or natural persons, retain the same Siren number until they die.
     *                               Legal entities lose their legal personality when they cease their business activity.
     *                               If the business is resumed at a later date, a new Siren number will be assigned.
     *                               Identification numbers are unique: once a Siren number has been allocated,
     *                               it cannot be reused and allocated to a new Sirene unit, even if the business has ceased its activity.
     * @param sireneUnitCreationDate
     * @param firstName
     * @param middleName
     * @param thirdName
     * @param fourthName
     * @param lastModifiedDate
     * @param unitChanges
     */
    @Serdeable
    record SireneUnit(String siren,
                      @JsonProperty(SIRENE_UNIT_CREATION_DATE) String sireneUnitCreationDate,
                      @JsonProperty(FIRST_NAME) String firstName,
                      @JsonProperty(MIDDLE_NAME) String middleName,
                      @JsonProperty(THIRD_NAME) String thirdName,
                      @JsonProperty(FOURTH_NAME) String fourthName,
                      @JsonProperty(LAST_MODIFIED_DATE) String lastModifiedDate,
                      @JsonProperty(UNIT_CHANGES) List<Period> unitChanges) {

    }

    /**
     * Each period corresponds to a time interval.
     * During this time interval, none of the Sirene unit's historical variables are modified.
     *
     * @param startDate
     * @param endDate
     * @param administrativeStatus
     * @param naturalPersonName          Birth name of the natural person.
     *                                   This variable is set to null for legal entities.
     *                                   The Sirene directory only handles non-accented capital letters.
     *                                   Only the special characters hyphen (-) and apostrophe.
     *                                   The name may be set to null for a natural person (case of purged units).
     * @param legalCategory              The legal category is an attribute of Sirene units.
     *                                   For a natural person, it is always 1000, whether the person is a
     *                                   craftsman, trader, self-employed person, farmer or other, and cannot change.
     *                                   For legal entities, the legal category may change during the life of the company.
     * @param companyName                Name under which the legal unit is declared.
     *                                   This variable is set to null for individuals.
     *                                   The name may sometimes contain a reference to the form of the company.
     * @param administrativeStatusChange For each period, each historicized variable is accompanied by another one indicating whether it has been modified or not.
     *                                   In other words, this indicates whether a given variable or set of variables is the reason for the creation of the period.
     *                                   These change indicators are prefixed by "changement" in the JSON response.
     *                                   They are suffixed by "Change" in the Period record.
     * @param naturalPersonNameChange
     * @param mainActivityChange
     * @param companyNameChange
     * @param legalCategoryChange
     */
    @Serdeable
    record Period(@JsonProperty(START_DATE) String startDate,
                  @JsonProperty(END_DATE) String endDate,
                  @JsonProperty(ADMIN_STATUS) AdministrativeStatus administrativeStatus,
                  @JsonProperty(NATURAL_PERSON_NAME) String naturalPersonName,
                  @JsonProperty(COMPANY_NAME) String companyName,
                  @JsonProperty(LEGAL_CATEGORY) String legalCategory,
                  @JsonProperty(MAIN_ACTIVITY) String mainActivity,
                  @JsonProperty(ADMIN_STATUS_CHANGE) boolean administrativeStatusChange,
                  @JsonProperty(NATURAL_PERSON_NAME_CHANGE) boolean naturalPersonNameChange,
                  @JsonProperty(MAIN_ACTIVITY_CHANGE) boolean mainActivityChange,
                  @JsonProperty(COMPANY_NAME_CHANGE) boolean companyNameChange,
                  @JsonProperty(LEGAL_CATEGORY_CHANGE) boolean legalCategoryChange,
                  Map<String, Boolean> jsonToValue) {

        Period {
            jsonToValue = new HashMap<>();
            jsonToValue.put("administrativeStatusChange", administrativeStatusChange);
            jsonToValue.put("naturalPersonNameChange", naturalPersonNameChange);
            jsonToValue.put("mainActivityChange", mainActivityChange);
            jsonToValue.put("companyNameChange", companyNameChange);
            jsonToValue.put("legalCategoryChange", legalCategoryChange);
        }

        private String logChangeReasons() {
            String changeReasons = jsonToValue.entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
            return changeReasons.isEmpty() ?
                    "This is the first period for the Sirene unit." :
                    String.format("Reason the period was created: %s", changeReasons);
        }

        void logPeriodInfo() {
            log.info("[Period started on {} and {}.", startDate,
                    (endDate != null ? String.format("ended on %s", endDate) : "is ongoing")
            );
            log.info("Sirene unit has a main activity code of {} and is {}.",
                    mainActivity,
                    (companyName != null ?
                            String.format("a legal entity with name %s", companyName) :
                            String.format("a natural person with surname %s", naturalPersonName))
            );
            log.info("{}]", logChangeReasons());
        }
    }
}
