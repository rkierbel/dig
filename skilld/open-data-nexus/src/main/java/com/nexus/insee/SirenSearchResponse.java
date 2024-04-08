package com.nexus.insee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


@Serdeable
@Slf4j
public record SirenSearchResponse(@JsonProperty("header") SirenHeader header,
                                  @JsonProperty("unitesLegales") List<SireneUnit> units) {

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
                      @JsonProperty("dateCreationUniteLegale") String sireneUnitCreationDate,
                      @JsonProperty("prenom1UniteLegale") String firstName,
                      @JsonProperty("prenom2UniteLegale") String middleName,
                      @JsonProperty("prenom3UniteLegale") String thirdName,
                      @JsonProperty("prenom4UniteLegale") String fourthName,
                      @JsonProperty("dateDernierTraitementUniteLegale") String lastModifiedDate,
                      @JsonProperty("periodesUniteLegale") List<Period> unitChanges) {

    }

    /**
     * Each period corresponds to a time interval
     * During this time interval, none of the Sirene unit's historical variables are modified.
     *
     * @param startDate
     * @param endDate
     * @param administrativeStatus
     * @param naturalPersonName    Birth name of the natural person.
     *                             This variable is set to null for legal entities.
     *                             The Sirene directory only handles non-accented capital letters.
     *                             Only the special characters hyphen (-) and apostrophe.
     *                             The name may be set to null for a natural person (case of purged units).
     * @param legalCategory        The legal category is an attribute of Sirene units.
     *                             For a natural person, it is always 1000, whether the person is a
     *                             craftsman, trader, self-employed person, farmer or other, and cannot change.
     *                             For legal entities, the legal category may change during the life of the company.
     * @param companyName          Name under which the legal unit is declared.
     *                             This variable is set to null for individuals.
     *                             The name may sometimes contain a reference to the form of the company.
     */
    @Serdeable
    record Period(@JsonProperty("dateDebut") String startDate,
                  @JsonProperty("dateFin") String endDate,
                  @JsonProperty("etatAdministratifUniteLegale") AdministrativeStatus administrativeStatus,
                  @JsonProperty("nomUniteLegale") String naturalPersonName,
                  @JsonProperty("denominationUniteLegale") String companyName,
                  @JsonProperty("categorieJuridiqueUniteLegale") String legalCategory,
                  @JsonProperty("activitePrincipaleUniteLegale") String mainActivity) {

        void logPeriodInfo() {
            log.info("[Period started on {} and {}.", startDate,
                    (endDate != null ? "ended on " + endDate : "is ongoing"));
            log.info("Sirene unit has a main activity code of {} and is {}.]", mainActivity,
                    (companyName != null ?
                            "a legal entity with name " + companyName :
                            "a natural person with surname " + naturalPersonName));

        }
    }

    /**
     * A -> active
     * C -> ceased business activity
     */
    @Serdeable
    enum AdministrativeStatus {
        A,
        C;

        @JsonCreator
        static AdministrativeStatus fromName(String name) {
            String upperCaseName = name.toUpperCase();

            return Arrays.stream(values())
                    .filter(value -> value.name().equals(upperCaseName))
                    .findFirst()
                    .orElseThrow(() -> SirenSearchException.administrativeStatusNotFound(name));
        }
    }
}
