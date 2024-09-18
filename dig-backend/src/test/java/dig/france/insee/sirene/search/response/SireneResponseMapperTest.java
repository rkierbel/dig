package dig.france.insee.sirene.search.response;

import dig.common.util.DateTimeUtil;
import dig.france.insee.sirene.search.response.enumerated.AdministrativeStatus;
import dig.france.insee.sirene.search.response.enumerated.UnitType;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static dig.common.util.DateTimeUtil.ZONE_BRUSSELS;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class SireneResponseMapperTest {

    @Inject
    SireneResponseMapper mapper;

    private final SireneSearchResponse sireneResponse = SireneSearchResponseFixtures.createSireneSearchResponse();
    private final SiretSearchResponse siretResponse = SiretSearchResponseFixtures.createSiretSearchResponse();

    @Test
    void validDataToDto_shouldMapCorrectly() {
        SearchResponseDto report = mapper.toDto(sireneResponse, siretResponse);

        assertNotNull(report);
        assertEquals(1, report.sireneUnits().size());
    }

    @Test
    void validSireneUnitToDto_shouldMapCorrectly() {
        SearchResponseDto.SireneUnitDto unitDto = mapper.toSireneUnitDto(
                sireneResponse.sireneUnits().getFirst(), siretResponse);

        assertNotNull(unitDto);
        assertEquals(123456789, unitDto.siren());
        assertEquals(LocalDate.parse("2024-09-04"), unitDto.creationDate());
        assertEquals(
                Instant.from(LocalDateTime.parse("2024-01-01T09:00:00.000", DateTimeUtil.FORMATTER).atZone(ZONE_BRUSSELS)),
                unitDto.lastModifiedDate());
        assertEquals(UnitType.NATURAL_PERSON, unitDto.type());
        assertEquals("Prison Mike", unitDto.commonFirstName());
        assertEquals("Michael, Gary", unitDto.firstNames());
        assertEquals(2, unitDto.periods().size());
        assertEquals(1, unitDto.establishments().size());
    }

    @Test
    void testToEstablishmentDto() {
        SearchResponseDto.EstablishmentDto establishmentDto = mapper.toEstablishmentDto(siretResponse.establishments().getFirst());

        assertNotNull(establishmentDto);
        assertEquals(12345678900123L, establishmentDto.siret());
        assertEquals(LocalDate.parse("2024-01-01"), establishmentDto.establishmentCreationDate());
        assertEquals("50-99", establishmentDto.employeeHeadcountBand());
        assertEquals("2024", establishmentDto.employeeHeadcountValidityYear());
        assertEquals("62.01Z", establishmentDto.tradeRegisterMainActivity());
        assertEquals(
                Instant.from(LocalDateTime.parse("2024-01-01T09:00:00.000", DateTimeUtil.FORMATTER).atZone(ZONE_BRUSSELS)),
                establishmentDto.establishmentLastModifiedDate());
        assertTrue(establishmentDto.isHead());
        assertNotNull(establishmentDto.address());
        assertNotNull(establishmentDto.address2()); //TODO -> test addresses
        assertEquals(1, establishmentDto.establishmentPeriods().size());
        assertEquals(
                establishmentDto.establishmentPeriods().size(),
                siretResponse.establishments().getFirst().numberEstablishmentPeriods());
    }

    @Test
    void testToEstablishmentPeriodDto() {
        SearchResponseDto.EstablishmentPeriodDto periodDto = mapper.toEstablishmentPeriodDto(
                siretResponse.establishments().getFirst().establishmentPeriods().getFirst());

        assertNotNull(periodDto);
        assertEquals(LocalDate.parse("2023-01-01"), periodDto.startDate());
        assertNull(periodDto.endDate());
        assertEquals(AdministrativeStatus.A, periodDto.administrativeStatus());
        assertEquals("Sign A, Sign B, Sign C", periodDto.sign());
        assertEquals("62.01Z", periodDto.mainActivity());
        assertEquals("Employer", periodDto.employerType());
        assertTrue(periodDto.changes().isEmpty());
    }

    @Test
    void testToPeriodDto() {
        SearchResponseDto.PeriodDto lastPeriodDto = mapper.toPeriodDto(
                sireneResponse.sireneUnits().getFirst().periods().getFirst());
        SearchResponseDto.PeriodDto firstPeriodDto = mapper.toPeriodDto(
                sireneResponse.getSireneUnits().getFirst().periods().getLast());

        assertNotNull(lastPeriodDto);
        assertEquals(LocalDate.parse("2024-01-02"), lastPeriodDto.startDate());
        assertNull(lastPeriodDto.endDate());
        assertEquals("Scott", lastPeriodDto.naturalPersonLastName());
        assertEquals("Michael", lastPeriodDto.naturalPersonCommonName());
        assertEquals("Dunder Mifflin Inc., Dunder Mifflin", lastPeriodDto.companyNames());
        assertEquals("1000", lastPeriodDto.legalCategory());
        assertEquals("62.01Z", lastPeriodDto.mainActivity());
        assertFalse(lastPeriodDto.changes().isEmpty());
        assertEquals(lastPeriodDto.changes().getFirst().reason(), PeriodChange.Reason.ADMIN_STATUS_CHANGE);

        assertNotNull(firstPeriodDto);
        assertEquals(LocalDate.parse("2023-01-01"), firstPeriodDto.startDate());
        assertEquals(LocalDate.parse("2024-01-01"), firstPeriodDto.endDate());
        assertEquals("Scott", firstPeriodDto.naturalPersonLastName());
        assertEquals("Michael", firstPeriodDto.naturalPersonCommonName());
        assertEquals("Dunder Mifflin Inc., Dunder Mifflin", firstPeriodDto.companyNames());
        assertEquals("1000", firstPeriodDto.legalCategory());
        assertEquals("62.01Z", firstPeriodDto.mainActivity());
        assertTrue(firstPeriodDto.changes().isEmpty());
    }

    @Test
    void testToInstant() {
        Instant result = mapper.toInstant("2023-01-01T10:00:00.000");

        assertEquals(Instant.parse("2023-01-01T09:00:00Z"), result);
    }

    @Test
    void testToDtoWithNullInputs() {
        SearchResponseDto report = mapper.toDto(null, null);

        assertNotNull(report);
        assertNull(report.sireneUnits());
    }

    @Test
    void testToDtoWithEmptyInputs() {
        SireneSearchResponse emptySireneResponse = SireneSearchResponseFixtures.empty();
        SiretSearchResponse emptySiretResponse = SiretSearchResponseFixtures.empty();

        SearchResponseDto report = mapper.toDto(emptySireneResponse, emptySiretResponse);

        assertNotNull(report);
        assertTrue(report.sireneUnits().isEmpty());
    }

    @Test
    void testToSireneUnitDtoWithNullUnit() {
        SearchResponseDto.SireneUnitDto unitDto = mapper.toSireneUnitDto(null, siretResponse);

        assertNotNull(unitDto);
        assertNull(unitDto.siren());
        assertNull(unitDto.creationDate());
        assertNull(unitDto.lastModifiedDate());
        assertNull(unitDto.type());
        assertNull(unitDto.commonFirstName());
        assertNull(unitDto.firstNames());
        assertNull(unitDto.periods());
        assertTrue(unitDto.establishments().isEmpty());
    }

    @Test
    void testToSireneUnitDtoWithNullFields() {
        SireneSearchResponse.SireneUnit unitWithNulls = SireneSearchResponseFixtures.nullDataSireneUnit();

        SearchResponseDto.SireneUnitDto unitDto = mapper.toSireneUnitDto(unitWithNulls, null);

        assertNotNull(unitDto);
        assertNull(unitDto.siren());
        assertNull(unitDto.creationDate());
        assertNull(unitDto.lastModifiedDate());
        assertNull(unitDto.commonFirstName());
        assertEquals("", unitDto.firstNames());
        assertTrue(unitDto.periods().isEmpty());
        assertTrue(unitDto.establishments().isEmpty());
    }

    @Test
    void testToEstablishmentDtoWithNullEstablishment() {
        SearchResponseDto.EstablishmentDto establishmentDto = mapper.toEstablishmentDto(null);
        assertNull(establishmentDto);
    }

    @Test
    void testToEstablishmentDtoWithNullFields() {
        SiretSearchResponse.Establishment establishmentWithNulls = SiretSearchResponseFixtures.nullDataEstablishment();

        SearchResponseDto.EstablishmentDto establishmentDto = mapper.toEstablishmentDto(establishmentWithNulls);

        assertNotNull(establishmentDto);
        assertNull(establishmentDto.siret());
        assertNull(establishmentDto.establishmentCreationDate());
        assertNull(establishmentDto.employeeHeadcountBand());
        assertNull(establishmentDto.employeeHeadcountValidityYear());
        assertNull(establishmentDto.tradeRegisterMainActivity());
        assertNull(establishmentDto.establishmentLastModifiedDate());
        assertFalse(establishmentDto.isHead());
        assertNull(establishmentDto.address());
        assertNull(establishmentDto.address2());
        assertTrue(establishmentDto.establishmentPeriods().isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid-date", "2023-13-01", "2023-01-32"})
    void testToInstantWithInvalidInput(String input) {
        assertNull(mapper.toInstant(input));
    }

    @Test
    void testAddEstablishmentsWithNullSiretResponse() {
        SearchResponseDto.SireneUnitDto unitDtoEmptyEstablishments = Instancio.of(SearchResponseDto.SireneUnitDto.class)
                .set(field(SearchResponseDto.SireneUnitDto::establishments), Collections.emptyList())
                .create();
        SearchResponseDto.SireneUnitDto unitDtoNullEstablishments = Instancio.of(SearchResponseDto.SireneUnitDto.class)
                .set(field(SearchResponseDto.SireneUnitDto::establishments), null)
                .create();

        mapper.addEstablishments(unitDtoEmptyEstablishments, null);
        mapper.addEstablishments(unitDtoNullEstablishments, null);

        assertTrue(unitDtoEmptyEstablishments.establishments().isEmpty());
        assertNull(unitDtoNullEstablishments.establishments());
    }

    @Test
    void testAddEstablishmentsWithNonMatchingSiren() {
        SearchResponseDto.SireneUnitDto unitDto = Instancio.of(SearchResponseDto.SireneUnitDto.class)
                .set(field(SearchResponseDto.SireneUnitDto::establishments), Collections.emptyList())
                .create();
        SiretSearchResponse nonMatchingSiretResponse = Instancio.create(SiretSearchResponse.class);

        mapper.addEstablishments(unitDto, nonMatchingSiretResponse);

        assertTrue(unitDto.establishments().isEmpty());
    }

    @Test
    void testToPeriodDtoWithNullPeriod() {
        SearchResponseDto.PeriodDto periodDto = mapper.toPeriodDto(null);

        assertNull(periodDto);
    }

    @Test
    void testToEstablishmentPeriodDtoWithNullPeriod() {
        SearchResponseDto.EstablishmentPeriodDto periodDto = mapper.toEstablishmentPeriodDto(null);

        assertNull(periodDto);
    }
}