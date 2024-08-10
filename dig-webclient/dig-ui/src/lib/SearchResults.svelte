<script lang="ts">
	import type { SearchResultData } from '$lib/types.ts';
    
	export let results: SearchResultData[] = [];
</script>

<div class="results">
	{#each results as result}
		<div class="result">
			<div><strong>First Name:</strong> {result.firstName}</div>
			<div><strong>Creation Date:</strong> {result.creationDate}</div>
			<div><strong>Last Modified Date:</strong> {result.lastModifiedDate}</div>
			<div><strong>Periods:</strong></div>
			{#each result.periods as period}
				<div class="period">
					<div class="period-header">Period:</div>
					<div><strong>Change Reason:</strong> {period.changeReason}</div>
					<div><strong>Change Value:</strong> {period.changeValue}</div>
					<div><strong>Start Date:</strong> {period.startDate}</div>
					<div><strong>End Date:</strong> {period.endDate}</div>
				</div>
			{/each}
		</div>
	{/each}
</div>

<style>
	.results {
		display: flex;
		flex-direction: column;
		gap: 1rem;
	}

	.result {
		border: 1px solid #ccc;
		padding: 1rem;
		border-radius: 5px;
		background-color: #f9f9f9;
	}

	.period {
		margin-top: 0.5rem;
		padding-left: 1rem;
		border-left: 2px solid #ddd;
	}

	.period-header {
		font-weight: bold;
	}
</style>
