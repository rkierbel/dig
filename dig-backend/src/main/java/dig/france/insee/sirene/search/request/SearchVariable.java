package dig.france.insee.sirene.search.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import dig.france.insee.exception.SireneSearchException;
import dig.common.exception.EnumValueNotFoundException;
import dig.common.util.EnumHelper;
import dig.france.insee.sirene.SireneConstants;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

@Serdeable
@Getter
@Introspected
public enum SearchVariable {

    COMPANY_NAME(SireneConstants.COMPANY_NAME),
    NATURAL_PERSON_COMMON_NAME(SireneConstants.NATURAL_PERSON_COMMON_NAME),
    FIRST_NAME(SireneConstants.FIRST_NAME),
    MIDDLE_NAME(SireneConstants.MIDDLE_NAME),
    THIRD_NAME(SireneConstants.THIRD_NAME),
    FOURTH_NAME(SireneConstants.FOURTH_NAME),
    COMMON_FIRST_NAME(SireneConstants.COMMON_FIRST_NAME),
    ADMIN_STATUS(SireneConstants.ADMIN_STATUS),
    NATURAL_PERSON_NAME(SireneConstants.NATURAL_PERSON_NAME),
    LEGAL_UNIT_NAME(SireneConstants.LEGAL_UNIT_NAME),
    COMPANY_COMMON_NAME_1(SireneConstants.COMPANY_COMMON_NAME_1),
    COMPANY_COMMON_NAME_2(SireneConstants.COMPANY_COMMON_NAME_2),
    COMPANY_COMMON_NAME_3(SireneConstants.COMPANY_COMMON_NAME_3),
    ADDRESS_SUPPLEMENT(SireneConstants.ADDRESS_SUPPLEMENT),
    ROAD_NUMBER(SireneConstants.ROAD_NUMBER),
    REPETITION_INDEX(SireneConstants.REPETITION_INDEX),
    ROAD_TYPE(SireneConstants.ROAD_TYPE),
    ROAD_NAME(SireneConstants.ROAD_NAME),
    MUNICIPALITY_NAME(SireneConstants.MUNICIPALITY_NAME),
    FOREIGN_MUNICIPALITY_NAME(SireneConstants.FOREIGN_MUNICIPALITY_NAME),
    WORDED_CEDEX(SireneConstants.WORDED_CEDEX),
    FOREIGN_COUNTRY_NAME(SireneConstants.FOREIGN_COUNTRY_NAME),
    COORDINATE_ABSCISSA(SireneConstants.COORDINATE_ABSCISSA),
    COORDINATE_ORDINATE(SireneConstants.COORDINATE_ORDINATE),
    ESTABLISHMENT_ADMIN_STATUS(SireneConstants.ESTABLISHMENT_ADMIN_STATUS),
    ESTABLISHMENT_SIGN_1(SireneConstants.ESTABLISHMENT_SIGN_1),
    ESTABLISHMENT_SIGN_2(SireneConstants.ESTABLISHMENT_SIGN_2),
    ESTABLISHMENT_SIGN_3(SireneConstants.ESTABLISHMENT_SIGN_3),
    ESTABLISHMENT_COMMON_NAME(SireneConstants.ESTABLISHMENT_COMMON_NAME),
    SIREN(SireneConstants.SIREN);

    final String frenchVariableName;

    SearchVariable(String frenchVariableName) {
        this.frenchVariableName = frenchVariableName;
    }

    public String frenchVariableName() {
        return this.frenchVariableName;
    }

    @JsonCreator
    static SearchVariable fromName(String name) {
        try {
            return EnumHelper.helper().deSerEnum(name, SearchVariable.values());
        } catch (EnumValueNotFoundException notFoundEx) {
            throw SireneSearchException.searchVariableNotFound(name);
        }
    }
}
