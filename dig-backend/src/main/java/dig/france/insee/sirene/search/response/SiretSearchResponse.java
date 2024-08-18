package dig.france.insee.sirene.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

import static dig.france.insee.sirene.SireneConstants.*;

@Serdeable
public record SiretSearchResponse(
        Header header,
        List<Establishment> etablissements
) {
}

@Serdeable
record Header(
        int statut,
        String message,
        int total,
        int debut,
        int nombre
) {
}

/**
 *
 * @field siren
 * @field nic
 * @field siret
 * @field establishmentCreationDate
 * @field employeeHeadcountBand             Updated once a year in the fall.
 * @field employeeHeadcountValidityYear     Year when the employee head count band was last actualised.
 *                                          For active units that are not updated two years in a row, this variable is set to null.
 * @field tradeRegisterMainActivity         Only concerns establishments in the craft sector (craftsmen, craft traders and craft companies).
 * @field establishmentLastModifiedDate
 * @field isHead                            Determines whether the establishment is the legal unit head or not.
 * @field numberEstablishmentPeriods
 * @field address
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
 * @param complementAdresseEtablissement
 * @param numeroVoieEtablissement
 * @param indiceRepetitionEtablissement
 * @param dernierNumeroVoieEtablissement
 * @param indiceRepetitionDernierNumeroVoieEtablissement
 * @param typeVoieEtablissement
 * @param libelleVoieEtablissement
 * @param codePostalEtablissement
 * @param libelleCommuneEtablissement
 * @param libelleCommuneEtrangerEtablissement
 * @param distributionSpecialeEtablissement
 * @param codeCommuneEtablissement
 * @param codeCedexEtablissement
 * @param libelleCedexEtablissement
 * @param codePaysEtrangerEtablissement
 * @param libellePaysEtrangerEtablissement
 * @param identifiantAdresseEtablissement
 * @param coordonneeLambertAbscisseEtablissement
 * @param coordonneeLambertOrdonneeEtablissement
 */
@Serdeable
record Address(
        String complementAdresseEtablissement,
        String numeroVoieEtablissement,
        String indiceRepetitionEtablissement,
        String dernierNumeroVoieEtablissement,
        String indiceRepetitionDernierNumeroVoieEtablissement,
        String typeVoieEtablissement,
        String libelleVoieEtablissement,
        String codePostalEtablissement,
        String libelleCommuneEtablissement,
        String libelleCommuneEtrangerEtablissement,
        String distributionSpecialeEtablissement,
        String codeCommuneEtablissement,
        String codeCedexEtablissement,
        String libelleCedexEtablissement,
        String codePaysEtrangerEtablissement,
        String libellePaysEtrangerEtablissement,
        String identifiantAdresseEtablissement,
        String coordonneeLambertAbscisseEtablissement,
        String coordonneeLambertOrdonneeEtablissement
) {
}

/**
 *
 * @param dateFin
 * @param dateDebut
 * @param etatAdministratifEtablissement
 * @param changementEtatAdministratifEtablissement
 * @param enseigne1Etablissement
 * @param enseigne2Etablissement
 * @param enseigne3Etablissement
 * @param changementEnseigneEtablissement
 * @param denominationUsuelleEtablissement
 * @param changementDenominationUsuelleEtablissement
 * @param activitePrincipaleEtablissement  The establishment's main activity for the period.
 *  *                                          When an establishment is entered in the Siret register, Insee assigns it a code based on the description of its main activity provided by the registrant.
 *  *                                          This code can be changed during the life of the establishment, depending on the declarations made by the registrant. For each establishment, there is only one  code at any given time.
 * @param nomenclatureActivitePrincipaleEtablissement
 * @param changementActivitePrincipaleEtablissement
 * @param caractereEmployeurEtablissement
 * @param changementCaractereEmployeurEtablissement
 */
@Serdeable
record EstablishmentPeriod(
        String dateFin,
        String dateDebut,
        String etatAdministratifEtablissement,
        boolean changementEtatAdministratifEtablissement,
        String enseigne1Etablissement,
        String enseigne2Etablissement,
        String enseigne3Etablissement,
        boolean changementEnseigneEtablissement,
        String denominationUsuelleEtablissement,
        boolean changementDenominationUsuelleEtablissement,
        String activitePrincipaleEtablissement,
        String nomenclatureActivitePrincipaleEtablissement,
        boolean changementActivitePrincipaleEtablissement,
        String caractereEmployeurEtablissement,
        boolean changementCaractereEmployeurEtablissement
) {
}
