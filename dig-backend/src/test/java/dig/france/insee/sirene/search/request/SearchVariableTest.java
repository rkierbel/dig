package dig.france.insee.sirene.search.request;

import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import dig.france.insee.exception.SireneSearchException;
import dig.france.insee.sirene.SireneConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchVariableTest {

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void fromName_shouldDeserializeAllEnumValues(SearchVariable searchVariable) {
        String searchVarName = searchVariable.name();
        assertEquals(searchVariable, SearchVariable.fromName(searchVarName));
    }

    @Test
    void fromName_shouldThrowExceptionForInvalidName() {
        String invalidSearchVarName = "INVALID_VARIABLE_NAME";
        SireneSearchException exception = assertThrows(SireneSearchException.class,
                () -> SearchVariable.fromName(invalidSearchVarName));
        assertTrue(exception.getMessage().contains(invalidSearchVarName));
    }

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void frenchVariableName_shouldReturnNonNullValue(SearchVariable searchVariable) {
        assertNotNull(searchVariable.frenchVariableName());
        assertFalse(searchVariable.frenchVariableName().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void enumHelper_shouldCorrectlyDeserializeEnum(SearchVariable searchVariable) throws EnumValueNotFoundException {
        String searchVarDeserName = searchVariable.name();
        assertEquals(searchVariable, EnumHelper.helper().deSerEnum(searchVarDeserName, SearchVariable.values()));
    }

    @Test
    void frenchVariableName_shouldMatchConstantValue() {
        assertEquals(SireneConstants.COMPANY_NAME, SearchVariable.COMPANY_NAME.frenchVariableName());
        assertEquals(SireneConstants.NATURAL_PERSON_COMMON_NAME, SearchVariable.NATURAL_PERSON_COMMON_NAME.frenchVariableName());
        assertEquals(SireneConstants.FIRST_NAME, SearchVariable.FIRST_NAME.frenchVariableName());
        assertEquals(SireneConstants.MIDDLE_NAME, SearchVariable.MIDDLE_NAME.frenchVariableName());
        assertEquals(SireneConstants.THIRD_NAME, SearchVariable.THIRD_NAME.frenchVariableName());
        assertEquals(SireneConstants.FOURTH_NAME, SearchVariable.FOURTH_NAME.frenchVariableName());
        assertEquals(SireneConstants.COMMON_FIRST_NAME, SearchVariable.COMMON_FIRST_NAME.frenchVariableName());
        assertEquals(SireneConstants.ADMIN_STATUS, SearchVariable.ADMIN_STATUS.frenchVariableName());
        assertEquals(SireneConstants.NATURAL_PERSON_NAME, SearchVariable.NATURAL_PERSON_NAME.frenchVariableName());
        assertEquals(SireneConstants.LEGAL_UNIT_NAME, SearchVariable.LEGAL_UNIT_NAME.frenchVariableName());
        assertEquals(SireneConstants.COMPANY_COMMON_NAME_1, SearchVariable.COMPANY_COMMON_NAME_1.frenchVariableName());
        assertEquals(SireneConstants.COMPANY_COMMON_NAME_2, SearchVariable.COMPANY_COMMON_NAME_2.frenchVariableName());
        assertEquals(SireneConstants.COMPANY_COMMON_NAME_3, SearchVariable.COMPANY_COMMON_NAME_3.frenchVariableName());
        assertEquals(SireneConstants.ADDRESS_SUPPLEMENT, SearchVariable.ADDRESS_SUPPLEMENT.frenchVariableName());
        assertEquals(SireneConstants.ROAD_NUMBER, SearchVariable.ROAD_NUMBER.frenchVariableName());
        assertEquals(SireneConstants.REPETITION_INDEX, SearchVariable.REPETITION_INDEX.frenchVariableName());
        assertEquals(SireneConstants.ROAD_TYPE, SearchVariable.ROAD_TYPE.frenchVariableName());
        assertEquals(SireneConstants.ROAD_NAME, SearchVariable.ROAD_NAME.frenchVariableName());
        assertEquals(SireneConstants.MUNICIPALITY_NAME, SearchVariable.MUNICIPALITY_NAME.frenchVariableName());
        assertEquals(SireneConstants.FOREIGN_MUNICIPALITY_NAME, SearchVariable.FOREIGN_MUNICIPALITY_NAME.frenchVariableName());
        assertEquals(SireneConstants.WORDED_CEDEX, SearchVariable.WORDED_CEDEX.frenchVariableName());
        assertEquals(SireneConstants.FOREIGN_COUNTRY_NAME, SearchVariable.FOREIGN_COUNTRY_NAME.frenchVariableName());
        assertEquals(SireneConstants.COORDINATE_ABSCISSA, SearchVariable.COORDINATE_ABSCISSA.frenchVariableName());
        assertEquals(SireneConstants.COORDINATE_ORDINATE, SearchVariable.COORDINATE_ORDINATE.frenchVariableName());
        assertEquals(SireneConstants.ESTABLISHMENT_ADMIN_STATUS, SearchVariable.ESTABLISHMENT_ADMIN_STATUS.frenchVariableName());
        assertEquals(SireneConstants.ESTABLISHMENT_SIGN_1, SearchVariable.ESTABLISHMENT_SIGN_1.frenchVariableName());
        assertEquals(SireneConstants.ESTABLISHMENT_SIGN_2, SearchVariable.ESTABLISHMENT_SIGN_2.frenchVariableName());
        assertEquals(SireneConstants.ESTABLISHMENT_SIGN_3, SearchVariable.ESTABLISHMENT_SIGN_3.frenchVariableName());
        assertEquals(SireneConstants.ESTABLISHMENT_COMMON_NAME, SearchVariable.ESTABLISHMENT_COMMON_NAME.frenchVariableName());
        assertEquals(SireneConstants.SIREN, SearchVariable.SIREN.frenchVariableName());
    }
}