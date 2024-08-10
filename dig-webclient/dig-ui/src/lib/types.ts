export type Change = {
	changeReason: string;
	changeValue: string;
};

export type Period = {
	changes: Change[];
	startDate: string;
	endDate: string;
};

export type Entity = {
	creationDate: string;
	lastModifiedDate: string;
	commonFirstName: string;
	periods: Period[];
};
