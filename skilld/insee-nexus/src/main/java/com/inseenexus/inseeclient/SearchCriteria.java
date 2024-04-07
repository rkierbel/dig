package com.inseenexus.inseeclient;

import static com.inseenexus.inseeclient.SirenSearchFactory.WHITESPACE;

record SearchCriteria(SearchVariable key, String value, SearchOperator operator) {

    static SearchCriteria from(SearchVariable key, String value, SearchOperator operator) {
        return new SearchCriteria(key, value, operator);
    }

    static SearchCriteria from(SearchVariable key, String value) {
        return new SearchCriteria(key, value, SearchOperator.NONE);
    }

    static SearchCriteria simpleSearch(String value) {
        return new SearchCriteria(SearchVariable.COMPANY_NAME, value, SearchOperator.NONE);
    }

    @Override
    public String toString() {
        String stringified = String.join(":", key().getSearchVariable(), value());

        return SearchOperator.NONE.equals(this.operator()) ?
                stringified :
                stringified + WHITESPACE + operator();
    }
}
