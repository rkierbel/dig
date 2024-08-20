package dig.france.insee.sirene;

public class SireneConstants {

    /**
     * Main sirene response components
     */
    public static final String SIRENE_UNITS = "unitesLegales";

    /**
     * Non-historical variables
     */
    public static final String SIREN = "siren";
    public static final String SIRENE_UNIT_CREATION_DATE = "dateCreationUniteLegale";
    public static final String FIRST_NAME = "prenom1UniteLegale";
    public static final String MIDDLE_NAME = "prenom2UniteLegale";
    public static final String THIRD_NAME = "prenom3UniteLegale";
    public static final String FOURTH_NAME = "prenom4UniteLegale";
    public static final String COMMON_FIRST_NAME = "prenomUsuelUniteLegale";
    public static final String LAST_MODIFIED_DATE = "dateDernierTraitementUniteLegale";
    public static final String UNIT_CHANGES = "periodesUniteLegale";

    /**
     * Historicized variables
     */
    public static final String START_DATE = "dateDebut";
    public static final String END_DATE = "dateFin";
    public static final String ADMIN_STATUS = "etatAdministratifUniteLegale";
    public static final String NATURAL_PERSON_NAME = "nomUniteLegale";
    public static final String NATURAL_PERSON_COMMON_NAME = "nomUsageUniteLegale";
    public static final String COMPANY_NAME = "denominationUniteLegale";
    public static final String COMPANY_COMMON_NAME_1 = "denominationUsuelle1UniteLegale";
    public static final String COMPANY_COMMON_NAME_2 = "denominationUsuelle2UniteLegale";
    public static final String COMPANY_COMMON_NAME_3 = "denominationUsuelle3UniteLegale";
    public static final String LEGAL_CATEGORY = "categorieJuridiqueUniteLegale";
    public static final String MAIN_ACTIVITY = "activitePrincipaleUniteLegale";
    public static final String ADMIN_STATUS_CHANGE = "changementEtatAdministratifUniteLegale";
    public static final String NATURAL_PERSON_NAME_CHANGE = "changementNomUniteLegale";
    public static final String NATURAL_PERSON_COMMON_NAME_CHANGE = "changementNomUsageUniteLegale";
    public static final String COMPANY_NAME_CHANGE = "changementDenominationUniteLegale";
    public static final String COMPANY_COMMON_NAME_CHANGE = "changementDenominationUsuelleUniteLegale";
    public static final String LEGAL_CATEGORY_CHANGE = "changementCategorieJuridiqueUniteLegale";
    public static final String MAIN_ACTIVITY_CHANGE = "changementActivitePrincipaleUniteLegale";
    public static final String HIDE_NULL_VALUES = "&masquerValeursNulles=true";

    /**
     * Siret - establishment variables
     */
    public static final String ESTABLISHMENTS = "etablissements";
    public static final String ESTABLISHMENT_CREATION_DATE = "dateCreationEtablissement";
    public static final String EMPLOYEE_HEADCOUNT_BAND = "trancheEffectifsEtablissement";
    public static final String EMPLOYEE_HEADCOUNT_VALIDITY_YEAR = "anneeEffectifsEtablissement";
    public static final String TRADE_REGISTER_MAIN_ACTIVITY = "activitePrincipaleRegistreMetiersEtablissement";
    public static final String ESTABLISHMENT_LAST_MODIFIED_DATE = "dateDernierTraitementEtablissement";
    public static final String IS_HEAD = "etablissementSiege";
    public static final String NUMBER_ESTABLISHMENT_PERIODS = "nombrePeriodesEtablissement";
    public static final String ADDRESS = "adresseEtablissement";
    public static final String ADDRESS_2 = "adresse2Etablissement";
    public static final String ESTABLISHMENT_PERIODS = "periodesEtablissement";

    /**
     * Siret - establishment address
     */
    public static final String ADDRESS_SUPPLEMENT = "complementAdresseEtablissement";
    public static final String ROAD_NUMBER = "numeroVoieEtablissement";
    public static final String REPETITION_INDEX = "indiceRepetitionEtablissement";
    public static final String LAST_ROAD_NUMBER = "dernierNumeroVoieEtablissement";
    public static final String REPETITION_INDEX_LAST_ROAD_NUMBER = "indiceRepetitionDernierNumeroVoieEtablissement";
    public static final String ROAD_TYPE = "typeVoieEtablissement";
    public static final String ROAD_NAME = "libelleVoieEtablissement";
    public static final String POSTAL_CODE = "codePostalEtablissement";
    public static final String MUNICIPALITY_NAME = "libelleCommuneEtablissement";
    public static final String FOREIGN_MUNICIPALITY_NAME = "libelleCommuneEtrangerEtablissement";
    public static final String SPECIAL_DISTRIBUTION = "distributionSpecialeEtablissement";
    public static final String MUNICIPALITY_CODE = "codeCommuneEtablissement";
    public static final String CODE_CEDEX = "codeCedexEtablissement";
    public static final String WORDED_CEDEX = "libelleCedexEtablissement";
    public static final String FOREIGN_COUNTRY_CODE = "codePaysEtrangerEtablissement";
    public static final String FOREIGN_COUNTRY_NAME = "libellePaysEtrangerEtablissement";
    public static final String ADDRESS_IDENTIFIER = "identifiantAdresseEtablissement";
    public static final String COORDINATE_ABSCISSA = "coordonneeLambertAbscisseEtablissement";
    public static final String COORDINATE_ORDINATE = "coordonneeLambertOrdonneeEtablissement";


    /**
     * Historicized variables - establishment
     */
    public static final String ESTABLISHMENT_ADMIN_STATUS = "etatAdministratifEtablissement";
    public static final String ESTABLISHMENT_SIGN_1 = "enseigne1Etablissement";
    public static final String ESTABLISHMENT_SIGN_2 = "enseigne2Etablissement";
    public static final String ESTABLISHMENT_SIGN_3 = "enseigne3Etablissement";
    public static final String ESTABLISHMENT_COMMON_NAME = "denominationUsuelleEtablissement";
    public static final String ESTABLISHMENT_MAIN_ACTIVITY = "activitePrincipaleEtablissement";
    public static final String ESTABLISHMENT_MAIN_ACTIVITY_NOMENCLATURE = "nomenclatureActivitePrincipaleEtablissement";
    public static final String EMPLOYER_TYPE = "caractereEmployeurEtablissement";
    public static final String ESTABLISHMENT_ADMIN_STATUS_CHANGE = "changementEtatAdministratifEtablissement";
    public static final String ESTABLISHMENT_NAME_CHANGE = "changementEnseigneEtablissement";
    public static final String ESTABLISHMENT_COMMON_NAME_CHANGE = "changementDenominationUsuelleEtablissement";
    public static final String ESTABLISHMENT_MAIN_ACTIVITY_CHANGE = "changementActivitePrincipaleEtablissement";
    public static final String EMPLOYER_TYPE_CHANGE = "changementCaractereEmployeurEtablissement";
}