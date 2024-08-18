package dig.france.insee.sirene.search.response;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

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

@Serdeable
record Establishment(
        String siren,
        String nic,
        String siret,
        String statutDiffusionEtablissement,
        String dateCreationEtablissement,
        String trancheEffectifsEtablissement,
        String anneeEffectifsEtablissement,
        String activitePrincipaleRegistreMetiersEtablissement,
        String dateDernierTraitementEtablissement,
        boolean etablissementSiege,
        int nombrePeriodesEtablissement,
        SireneSearchResponse.SireneUnit uniteLegale,
        Address address,
        Address adresse2Etablissement,
        List<EstablishmentPeriod> periodesEtablissement
) {
}

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
