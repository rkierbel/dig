package dig.france.insee.sirene.search.request;

import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import dig.france.insee.exception.SireneSearchException;
import dig.france.insee.sirene.SireneConstants;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class SearchVariableTest {

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void fromName_shouldDeserializeAllEnumValues(SearchVariable searchVariable) {
        String name = searchVariable.name();
        assertEquals(searchVariable, SearchVariable.fromName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"COMPANY_NAME", "NATURAL_PERSON_COMMON_NAME", "FIRST_NAME", "SIREN"})
    void fromName_shouldDeserializeSpecificValues(String name) {
        assertNotNull(SearchVariable.fromName(name));
        assertEquals(name, SearchVariable.fromName(name).name());
    }

    @Test
    void fromName_shouldThrowExceptionForInvalidName() {
        String invalidVar = "INVALID_VARIABLE_NAME";
        SireneSearchException exception = assertThrows(SireneSearchException.class,
                () -> SearchVariable.fromName(invalidVar));
        assertTrue(exception.getMessage().contains(invalidVar));
    }

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void frenchVariableName_shouldReturnNonNullValue(SearchVariable searchVariable) {
        assertNotNull(searchVariable.frenchVariableName());
        assertFalse(searchVariable.frenchVariableName().isEmpty());
    }

    @Test
    void frenchVariableName_shouldMatchConstantValue() {
        assertEquals(SireneConstants.COMPANY_NAME, SearchVariable.COMPANY_NAME.frenchVariableName());
        assertEquals(SireneConstants.SIREN, SearchVariable.SIREN.frenchVariableName());
    }

    @ParameterizedTest
    @EnumSource(SearchVariable.class)
    void enumHelper_shouldCorrectlyDeserializeEnum(SearchVariable searchVariable) throws EnumValueNotFoundException {
        String searchVar = searchVariable.name();
        assertEquals(searchVariable, EnumHelper.helper().deSerEnum(searchVar, SearchVariable.values()));
    }
}