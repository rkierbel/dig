<script lang="ts">
	import type { Period } from '$lib/types';
	import ChangesList from './ChangesList.svelte';

	export let period: Period;

	function formatDate(dateString: string | null): string {
		return dateString ? new Date(dateString).toLocaleDateString() : 'Current';
	}
</script>

<div class="period-details">
	<div class="grid">
		<div><span class="label">Start Date:</span> {formatDate(period.startDate)}</div>
		<div><span class="label">End Date:</span> {formatDate(period.endDate)}</div>
		<div><span class="label">Legal Category:</span> {period.legalCategory ?? 'N/A'}</div>
		<div><span class="label">Main Activity:</span> {period.mainActivity ?? 'N/A'}</div>
	</div>
	{#if period.changes && period.changes.length > 0}
		<ChangesList changes={period.changes} />
	{/if}
</div>

<style>
	.period-details {
		padding: 1.5rem;
		border-bottom: 1px solid #e5e7eb;
	}
	.period-details:last-child {
		border-bottom: none;
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
</style>
