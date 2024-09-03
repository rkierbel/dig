package dig.france.insee.sirene;

public interface SireneConstants {

    String HIDE_NULL_VALUES = "&masquerValeursNulles=true";

    /**
     * Main sirene response components
     */
    String SIRENE_UNITS = "unitesLegales";

    /**
     * Non-historical variables
     */
    String SIREN = "siren";
    String SIRENE_UNIT_CREATION_DATE = "dateCreationUniteLegale";
    String FIRST_NAME = "prenom1UniteLegale";
    String MIDDLE_NAME = "prenom2UniteLegale";
    String THIRD_NAME = "prenom3UniteLegale";
    String FOURTH_NAME = "prenom4UniteLegale";
    String COMMON_FIRST_NAME = "prenomUsuelUniteLegale";
    String LAST_MODIFIED_DATE = "dateDernierTraitementUniteLegale";
    String UNIT_CHANGES = "periodesUniteLegale";

    /**
     * Historicized variables
     */
    String START_DATE = "dateDebut";
    String END_DATE = "dateFin";
    String ADMIN_STATUS = "etatAdministratifUniteLegale";
    String NATURAL_PERSON_NAME = "nomUniteLegale";
    String NATURAL_PERSON_COMMON_NAME = "nomUsageUniteLegale";
    String COMPANY_NAME = "raisonSociale";
    String LEGAL_UNIT_NAME = "denominationUniteLegale";
    String COMPANY_COMMON_NAME_1 = "denominationUsuelle1UniteLegale";
    String COMPANY_COMMON_NAME_2 = "denominationUsuelle2UniteLegale";
    String COMPANY_COMMON_NAME_3 = "denominationUsuelle3UniteLegale";
    String LEGAL_CATEGORY = "categorieJuridiqueUniteLegale";
    String MAIN_ACTIVITY = "activitePrincipaleUniteLegale";
    String ADMIN_STATUS_CHANGE = "changementEtatAdministratifUniteLegale";
    String NATURAL_PERSON_NAME_CHANGE = "changementNomUniteLegale";
    String NATURAL_PERSON_COMMON_NAME_CHANGE = "changementNomUsageUniteLegale";
    String COMPANY_NAME_CHANGE = "changementDenominationUniteLegale";
    String COMPANY_COMMON_NAME_CHANGE = "changementDenominationUsuelleUniteLegale";
    String LEGAL_CATEGORY_CHANGE = "changementCategorieJuridiqueUniteLegale";
    String MAIN_ACTIVITY_CHANGE = "changementActivitePrincipaleUniteLegale";

    /**
     * Siret - establishment variables
     */
    String ESTABLISHMENTS = "etablissements";
    String ESTABLISHMENT_CREATION_DATE = "dateCreationEtablissement";
    String EMPLOYEE_HEADCOUNT_BAND = "trancheEffectifsEtablissement";
    String EMPLOYEE_HEADCOUNT_VALIDITY_YEAR = "anneeEffectifsEtablissement";
    String TRADE_REGISTER_MAIN_ACTIVITY = "activitePrincipaleRegistreMetiersEtablissement";
    String ESTABLISHMENT_LAST_MODIFIED_DATE = "dateDernierTraitementEtablissement";
    String IS_HEAD = "etablissementSiege";
    String NUMBER_ESTABLISHMENT_PERIODS = "nombrePeriodesEtablissement";
    String ADDRESS = "adresseEtablissement";
    String ADDRESS_2 = "adresse2Etablissement";
    String ESTABLISHMENT_PERIODS = "periodesEtablissement";

    /**
     * Siret - establishment address
     */
    String ADDRESS_SUPPLEMENT = "complementAdresseEtablissement";
    String ROAD_NUMBER = "numeroVoieEtablissement";
    String REPETITION_INDEX = "indiceRepetitionEtablissement";
    String LAST_ROAD_NUMBER = "dernierNumeroVoieEtablissement";
    String REPETITION_INDEX_LAST_ROAD_NUMBER = "indiceRepetitionDernierNumeroVoieEtablissement";
    String ROAD_TYPE = "typeVoieEtablissement";
    String ROAD_NAME = "libelleVoieEtablissement";
    String POSTAL_CODE = "codePostalEtablissement";
    String MUNICIPALITY_NAME = "libelleCommuneEtablissement";
    String FOREIGN_MUNICIPALITY_NAME = "libelleCommuneEtrangerEtablissement";
    String SPECIAL_DISTRIBUTION = "distributionSpecialeEtablissement";
    String MUNICIPALITY_CODE = "codeCommuneEtablissement";
    String CODE_CEDEX = "codeCedexEtablissement";
    String WORDED_CEDEX = "libelleCedexEtablissement";
    String FOREIGN_COUNTRY_CODE = "codePaysEtrangerEtablissement";
    String FOREIGN_COUNTRY_NAME = "libellePaysEtrangerEtablissement";
    String ADDRESS_IDENTIFIER = "identifiantAdresseEtablissement";
    String COORDINATE_ABSCISSA = "coordonneeLambertAbscisseEtablissement";
    String COORDINATE_ORDINATE = "coordonneeLambertOrdonneeEtablissement";

    /**
     * Historicized variables - establishment
     */
    String ESTABLISHMENT_ADMIN_STATUS = "etatAdministratifEtablissement";
    String ESTABLISHMENT_SIGN_1 = "enseigne1Etablissement";
    String ESTABLISHMENT_SIGN_2 = "enseigne2Etablissement";
    String ESTABLISHMENT_SIGN_3 = "enseigne3Etablissement";
    String ESTABLISHMENT_COMMON_NAME = "denominationUsuelleEtablissement";
    String ESTABLISHMENT_MAIN_ACTIVITY = "activitePrincipaleEtablissement";
    String ESTABLISHMENT_MAIN_ACTIVITY_NOMENCLATURE = "nomenclatureActivitePrincipaleEtablissement";
    String EMPLOYER_TYPE = "caractereEmployeurEtablissement";
    String ESTABLISHMENT_ADMIN_STATUS_CHANGE = "changementEtatAdministratifEtablissement";
    String ESTABLISHMENT_NAME_CHANGE = "changementEnseigneEtablissement";
    String ESTABLISHMENT_COMMON_NAME_CHANGE = "changementDenominationUsuelleEtablissement";
    String ESTABLISHMENT_MAIN_ACTIVITY_CHANGE = "changementActivitePrincipaleEtablissement";
    String EMPLOYER_TYPE_CHANGE = "changementCaractereEmployeurEtablissement";
}