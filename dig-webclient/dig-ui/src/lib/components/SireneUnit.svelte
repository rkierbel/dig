<script lang="ts">
	import type { SirenUnit } from '$lib/types';
	import PeriodDetails from './PeriodDetails.svelte';
	import { slide } from 'svelte/transition';

	export let sireneUnit: SirenUnit;
	let showDetails = false;

	function toggleDetails() {
		showDetails = !showDetails;
	}

	function formatDate(dateString: string): string {
		return new Date(dateString).toLocaleDateString();
	}
</script>

<div class="entity-card">
	<div class="card-content">
		<div class="grid">
			<div><span class="label">SIREN:</span> {sireneUnit.siren}</div>
			<div><span class="label">Type:</span> {sireneUnit.type}</div>
			{#if sireneUnit.type === 'NATURAL_PERSON'}
				<div>
					<span class="label">Name:</span>
					{sireneUnit.commonFirstName}
					{sireneUnit.periods[0]?.naturalPersonLastName ?? ''}
				</div>
			{:else}
				<div>
					<span class="label">Company Name:</span>
					{sireneUnit.periods[0]?.companyNames ?? 'N/A'}
				</div>
			{/if}
			<div><span class="label">Creation Date:</span> {formatDate(sireneUnit.creationDate)}</div>
			<div><span class="label">Last Modified:</span> {formatDate(sireneUnit.lastModifiedDate)}</div>
		</div>
		<button on:click={toggleDetails}>
			{showDetails ? 'Hide Details' : 'Show Details'}
		</button>
	</div>
	{#if showDetails}
		<div transition:slide class="details">
			{#each sireneUnit.periods || [] as period (period.startDate)}
				<PeriodDetails {period} />
			{/each}
		</div>
	{/if}
</div>

<style>
	.entity-card {
		background-color: white;
		border-radius: 8px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		overflow: hidden;
	}
	.card-content {
		padding: 1.5rem;
	}
	.grid {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
		gap: 1rem;
		margin-bottom: 1rem;
	}
	.label {
		font-weight: 600;
		color: #4b5563;
	}
	button {
		width: 100%;
		padding: 0.5rem 1rem;
		background-color: #3b82f6;
		color: white;
		border: none;
		border-radius: 4px;
		cursor: pointer;
	}
	button:hover {
		background-color: #2563eb;
	}
	.details {
		border-top: 1px solid #e5e7eb;
	}
</style>
