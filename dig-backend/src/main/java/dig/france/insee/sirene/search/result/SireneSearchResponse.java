package dig.france.insee.sirene.search.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import dig.france.insee.sirene.AdministrativeStatus;
import io.micronaut.serde.annotation.Serdeable;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dig.france.insee.sirene.SireneConstants.*;


@Serdeable
@Slf4j
public record SireneSearchResponse(SirenHeader header,
                                   @JsonProperty(SIRENE_UNITS) List<SireneUnit> sireneUnits) {

    void logSirenResponseInfo() {
        sireneUnits.forEach(
                unit -> {
                    log.info("-> Siren number {}, for natural person with first name {}:", unit.siren, unit.firstName);
                    unit.periods.forEach(Period::logPeriodInfo);
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
     * @param siren            Sole entrepreneurs, or natural persons, retain the same Siren number until they die.
     *                         Legal entities lose their legal personality when they cease their business activity.
     *                         If the business is resumed at a later date, a new Siren number will be assigned.
     *                         Identification numbers are unique: once a Siren number has been allocated,
     *                         it cannot be reused and allocated to a new Sirene unit, even if the business has ceased its activity.
     * @param creationDate
     * @param firstName
     * @param middleName
     * @param thirdName
     * @param fourthName
     * @param lastModifiedDate
     * @param periods
     */
    @Serdeable
    record SireneUnit(String siren,
                      @JsonProperty(SIRENE_UNIT_CREATION_DATE) String creationDate,
                      @JsonProperty(FIRST_NAME) String firstName,
                      @JsonProperty(MIDDLE_NAME) String middleName,
                      @JsonProperty(THIRD_NAME) String thirdName,
                      @JsonProperty(FOURTH_NAME) String fourthName,
                      @JsonProperty(LAST_MODIFIED_DATE) String lastModifiedDate,
                      @JsonProperty(UNIT_CHANGES) List<Period> periods) {

    }

    /**
     * Interrogation unitaire du siren : résultat
     * Le résultat est fourni au format Json.
     * Le retour est structuré en 2 parties :
     *
     * • le header (à ne pas confondre avec l'en-tête http ni l'en-tête de réponse) qui contient le code retour et le message d'erreur ;
     * • l'unité légale, qui comprend :
     * ◦ Toutes les variables courantes
     * ◦ La liste de toutes les périodes et, pour chaque période :
     * ▪ La liste des variables historisées.
     * Les résultats des valeurs non historisées (période courante) sont envoyés avant le tableau periodes.
     * Le tableau periodes entre […] comprend un nombre de périodes (variable nombrePeriodesUniteLegale) entre {…} par ordre chronologique décroissant :
     * • une période est définie par une date de début et une date de fin ;
     * • les valeurs des variables historisées sont celles observées dans la période ;
     * • une variable non connue sur une période sera à null ;
     * • la dernière période de l'historique dans l'ordre chronologique correspond à la période courante et a une date de fin à null ;
     * • un changement de valeur pour une variable historisée implique la création d'une période ;
     * • des indicatrices de changement (true ou false) sont attachées à chaque variable historisée et indiquent si la variable correspondante a été modifiée par rapport à la période précédente ;
     * • pour la première période de l'historique de l'entreprise dans l'ordre chronologique toutes les indicatrices sont à false ;
     * • pour une entreprise dont les variables historisées n'ont jamais été modifiées, la réponse ne comportera qu'une seule période.
     */
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
     *                                   The name may be set to null for a natural person (case of purged sireneUnits).
     * @param legalCategory              The legal category is an attribute of Sirene sireneUnits.
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
                  @JsonProperty(MAIN_ACTIVITY_CHANGE) boolean mainActivityChange,
                  Map<String, Boolean> jsonToValue) {

        Period {
            jsonToValue = new HashMap<>();
            jsonToValue.put("administrativeStatusChange", administrativeStatusChange);
            jsonToValue.put("naturalPersonNameChange", naturalPersonNameChange);
            jsonToValue.put("naturalPersonCommonNameChange", naturalPersonCommonNameChange);
            jsonToValue.put("companyNameChange", companyNameChange);
            jsonToValue.put("companyCommonNameChange", companyCommonNameChange);
            jsonToValue.put("mainActivityChange", mainActivityChange);
            jsonToValue.put("legalCategoryChange", legalCategoryChange);
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
            changes.put(PeriodChange.of(NATURAL_PERSON_NAME_CHANGE, naturalPersonName), naturalPersonNameChange);
            changes.put(PeriodChange.of(NATURAL_PERSON_COMMON_NAME_CHANGE, naturalPersonCommonName), naturalPersonCommonNameChange);
            changes.put(PeriodChange.of(COMPANY_NAME_CHANGE, companyName), companyNameChange);
            changes.put(PeriodChange.of(COMPANY_COMMON_NAME_CHANGE, companyCommonName), companyCommonNameChange);
            changes.put(PeriodChange.of(LEGAL_CATEGORY_CHANGE, legalCategory), legalCategoryChange);
            changes.put(PeriodChange.of(MAIN_ACTIVITY_CHANGE, mainActivity), mainActivityChange);
            return changes;
        }

        private String logChangeReasons() {
            String changeReasons = jsonToValue.entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
            return changeReasons.isEmpty() ?
                    "This is the first period for the Sirene unit" :
                    "Reason the period was created: %s".formatted(changeReasons);
        }

        void logPeriodInfo() {
            log.info("[Period started on {} and {}", startDate,
                    (endDate != null ? "ended on %s".formatted(endDate) : "is ongoing")
            );
            log.info("Sirene unit has a main activity code of {} and is {}",
                    mainActivity,
                    (companyName != null ?
                            "a legal entity with name %s".formatted(companyName) :
                            "a natural person with surname %s".formatted(naturalPersonName))
            );
            log.info("{}]", logChangeReasons());
        }
    }
}
