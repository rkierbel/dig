export interface Change {
	changeReason: string;
	changeValue: string;
}

export interface Period {
	changes: Change[];
	startDate: string;
	endDate: string;
}

export interface SearchResultData {
	creationDate: string;
	lastModifiedDate: string;
	firstName: string;
	periods: Period[];
}
