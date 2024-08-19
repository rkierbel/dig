export type Change = {
    changeReason: string;
    changeValue: string;
};

export type Period = {
    changes: Change[];
    naturalPersonLastName?: string;
    companyNames?: string;
    legalCategory: string;
    mainActivity: string;
    startDate: string;
    endDate: string | null;
};

export type Entity = {
    siren: number;
    creationDate: string;
    lastModifiedDate: string;
    type: 'NATURAL_PERSON' | 'LEGAL_ENTITY';
    commonFirstName?: string;
    firstNames?: string;
    periods: Period[];
};

export type SearchResponse = {
    sireneUnits: Entity[];
};