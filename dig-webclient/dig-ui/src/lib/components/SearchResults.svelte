<script lang="ts">
	import type { Entity } from '$lib/types.ts';

	export let results: Entity[] = [];
	let showPeriods: boolean[] = results.map(() => false);

	function togglePeriods(index: number) {
		showPeriods[index] = !showPeriods[index];
	}
</script>

<div class="results">
	{#each results as result, index}
		<div class="result-card">
			<div class="result-header">
				<div><strong>First Name:</strong> {result.commonFirstName}</div>
				<div><strong>Creation Date:</strong> {result.creationDate}</div>
				<div><strong>Last Modified Date:</strong> {result.lastModifiedDate}</div>
			</div>
			<button class="toggle-button" on:click={() => togglePeriods(index)}>
				{showPeriods[index] ? 'Hide Periods' : 'Show Periods'}
			</button>
			{#if showPeriods[index]}
				<div class="periods">
					<div><strong>Periods:</strong></div>
					{#each result.periods as period}
						<div class="period-card">
							<div class="period-header">Period:</div>
							<div><strong>Start Date:</strong> {period.startDate}</div>
							<div><strong>End Date:</strong> {period.endDate}</div>
							<div><strong>Changes:</strong></div>
							{#each period?.changes ?? [] as change}
								<div class="change">
									<div><strong>Change Reason:</strong> {change.changeReason}</div>
									<div><strong>Change Value:</strong> {change.changeValue}</div>
								</div>
							{/each}
						</div>
					{/each}
				</div>
			{/if}
		</div>
	{/each}
</div>

<style>
	.results {
		max-width: 600px;
		margin: 0 auto;
		padding: 1rem;
		display: flex;
		flex-direction: column;
		gap: 1rem;
	}

	.result-card {
		background: #fff;
		border-radius: 8px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		padding: 1rem;
		display: flex;
		flex-direction: column;
		gap: 0.5rem;
	}

	.result-header {
		display: flex;
		flex-direction: column;
		gap: 0.25rem;
	}

	.toggle-button {
		background: #007bff;
		color: #fff;
		border: none;
		border-radius: 4px;
		padding: 0.5rem 1rem;
		cursor: pointer;
		align-self: flex-start;
	}

	.toggle-button:hover {
		background: #0056b3;
	}

	.periods {
		margin-top: 1rem;
	}

	.period-card {
		background: #f9f9f9;
		border-radius: 8px;
		padding: 0.5rem;
		margin-top: 0.5rem;
	}

	.period-header {
		font-weight: bold;
		margin-bottom: 0.5rem;
	}

	.change {
		margin-top: 0.5rem;
	}
</style>
