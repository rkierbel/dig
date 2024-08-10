export interface Period {
    changeReason: string;
    changeValue: string;
    startDate: string;
    endDate: string;
  }
  
  export interface SearchResultData {
    creationDate: string;
    lastModifiedDate: string;
    firstName: string;
    periods: Period[];
  }