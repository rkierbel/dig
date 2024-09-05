package dig.france.insee.sirene.search.response;

import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;

import java.util.List;

class SiretSearchResponseFixtures {

    static SiretSearchResponse createSiretSearchResponse() {
        return new SiretSearchResponse(
                createHeader(),
                createEstablishments()
        );
    }

    static SiretSearchResponse.Header createHeader() {
        return new SiretSearchResponse.Header(
                200,
                "Operation successful",
                100,
                1
        );
    }

    static SiretSearchResponse.Establishment createEstablishment() {
        return new SiretSearchResponse.Establishment(
                "123456789", // siren
                "00123", // nic
                "12345678900123", // siret
                "2024-01-01", // establishmentCreationDate
                "50-99", // employeeHeadcountBand
                "2024", // employeeHeadcountValidityYear
                "62.01Z", // tradeRegisterMainActivity
                "2024-01-01T09:00:00.000", // establishmentLastModifiedDate
                true, // isHead
                1, // numberEstablishmentPeriods
                createAddress(), // address
                createAddress(), // address2
                createEstablishmentPeriods() // establishmentPeriods
        );
    }

    static SiretSearchResponse.Address createAddress() {
        return new SiretSearchResponse.Address(
                "Apt 4B", // addressSupplement
                "123", // roadNumber
                "Bis", // repetitionIndex
                null, // lastRoadNumber
                null, // repetitionIndexLastRoadNumber
                "Rue", // roadType
                "Laumière", // roadName
                "75000", // postalCode
                "Paris", // municipalityName
                null, // foreignMunicipalityName
                null, // specialDistribution
                "75019", // municipalityCode
                null, // codeCedex
                null, // wordedCedex
                null, // foreignCountryCode
                null, // foreignCountryName
                "FR123456789", // addressIdentifier
                "48.8566", // coordinateAbscissa
                "2.3522"  // coordinateOrdinate
        );
    }

    static SiretSearchResponse.EstablishmentPeriod createEstablishmentPeriod() {
        return new SiretSearchResponse.EstablishmentPeriod(
                "2023-01-01", // startDate
                null, // endDate
                AdministrativeStatus.A, // administrativeStatus
                "Sign A", // sign1
                "Sign B", // sign2
                "Sign C", // sign3
                "Common Name", // commonName
                "62.01Z", // mainActivity
                "NACE Rev.2", // mainActivityNomenclature
                "Employer", // employerType
                false, // administrativeStatusChange
                false, // signChange
                false, // commonNameChange
                false, // mainActivityChange
                false  // employerTypeChange
        );
    }

    static List<SiretSearchResponse.Establishment> createEstablishments() {
        return List.of(createEstablishment());
    }

    static List<SiretSearchResponse.EstablishmentPeriod> createEstablishmentPeriods() {
        return List.of(createEstablishmentPeriod());
    }

    static SiretSearchResponse.Establishment nullDataEstablishment() {
        return new SiretSearchResponse.Establishment(
                null, null, null, null,
                null, null,
                null, null, false, 1,
                null, null, null
        );
    }

    public static SiretSearchResponse empty() {
        return new SiretSearchResponse(null, null);
    }
}
