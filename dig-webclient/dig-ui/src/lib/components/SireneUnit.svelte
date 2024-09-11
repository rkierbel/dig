<script lang="ts">
	import type { SirenUnit } from '$lib/types';
	import PeriodDetails from './PeriodDetails.svelte';
	import { slide } from 'svelte/transition';
	import { ChevronDown, ChevronUp } from 'lucide-svelte';

	export let sireneUnit: SirenUnit;
	let showDetails = false;
	let showAllPeriods = false;
	const initialPeriodsToShow = 3;

	function toggleDetails() {
		showDetails = !showDetails;
	}

	function toggleAllPeriods() {
		showAllPeriods = !showAllPeriods;
	}

	function formatDate(dateString: string): string {
		return new Date(dateString).toLocaleDateString();
	}

	$: visiblePeriods = showAllPeriods
		? sireneUnit.periods
		: sireneUnit.periods.slice(0, initialPeriodsToShow);

	$: hasMorePeriods = sireneUnit.periods.length > initialPeriodsToShow;
</script>

<div class="bg-white shadow-md rounded-lg overflow-hidden">
	<div class="p-6">
		<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
			<div>
				<span class="text-sm font-medium text-gray-500">SIREN:</span>
				<span class="ml-2 text-gray-900">{sireneUnit.siren}</span>
			</div>
			<div class="flex items-center">
				<span class="text-sm font-medium text-gray-500">Type:</span>
				<span
					class="ml-2 px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800"
				>
					{sireneUnit.type}
				</span>
			</div>
			{#if sireneUnit.type === 'NATURAL_PERSON'}
				<div>
					<span class="text-sm font-medium text-gray-500">Name:</span>
					<span class="ml-2 text-gray-900">
						{sireneUnit.commonFirstName}
						{sireneUnit.periods[0]?.naturalPersonLastName ?? ''}
					</span>
				</div>
			{:else}
				<div>
					<span class="text-sm font-medium text-gray-500">Company Name:</span>
					<span class="ml-2 text-gray-900">
						{sireneUnit.periods[0]?.companyNames ?? 'N/A'}
					</span>
				</div>
			{/if}
			<div>
				<span class="text-sm font-medium text-gray-500">Creation Date:</span>
				<span class="ml-2 text-gray-900">{formatDate(sireneUnit.creationDate)}</span>
			</div>
			<div>
				<span class="text-sm font-medium text-gray-500">Last Modified:</span>
				<span class="ml-2 text-gray-900">{formatDate(sireneUnit.lastModifiedDate)}</span>
			</div>
		</div>
		<button
			on:click={toggleDetails}
			class="flex items-center justify-center w-full py-2 mt-4 text-sm font-medium text-blue-600 bg-blue-100 rounded-md hover:bg-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-500"
		>
			{showDetails ? 'Hide' : 'Show'} Details
			{#if showDetails}
				<ChevronUp class="w-4 h-4 ml-2" />
			{:else}
				<ChevronDown class="w-4 h-4 ml-2" />
			{/if}
		</button>
	</div>
	{#if showDetails}
		<div transition:slide class="border-t border-gray-200">
			{#each visiblePeriods as period (period.startDate)}
				<PeriodDetails {period} />
			{/each}
			{#if hasMorePeriods && !showAllPeriods}
				<div class="p-4">
					<button
						on:click={toggleAllPeriods}
						class="w-full py-2 text-sm font-medium text-blue-600 bg-blue-100 rounded-md hover:bg-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-500"
					>
						Show All Periods
					</button>
				</div>
			{/if}
		</div>
	{/if}
</div>
