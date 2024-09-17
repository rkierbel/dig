<script lang="ts">
	import type { Period } from '$lib/types';
	import ChangesList from './ChangesList.svelte';
	import { Calendar, Briefcase, ChevronDown, ChevronUp, MapPin, Phone, Globe } from 'lucide-svelte';
	import { slide } from 'svelte/transition';

	export let period: Period;

	let showAdditionalDetails = false;

	function formatDate(dateString: string | null): string {
		return dateString ? new Date(dateString).toLocaleDateString() : 'Current';
	}

	function toggleAdditionalDetails() {
		showAdditionalDetails = !showAdditionalDetails;
	}
</script>

<div class="bg-white shadow rounded-lg p-6 mb-4">
	<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
		<div class="flex items-center">
			<Calendar class="w-5 h-5 mr-2 text-gray-500" />
			<span class="text-sm font-medium text-gray-600">Start Date:</span>
			<span class="ml-2">{formatDate(period.startDate)}</span>
		</div>
		<div class="flex items-center">
			<Calendar class="w-5 h-5 mr-2 text-gray-500" />
			<span class="text-sm font-medium text-gray-600">End Date:</span>
			<span class="ml-2">{formatDate(period.endDate)}</span>
		</div>
		<div class="flex items-center">
			<Briefcase class="w-5 h-5 mr-2 text-gray-500" />
			<span class="text-sm font-medium text-gray-600">Legal Category:</span>
			<span class="ml-2">{period.legalCategory ?? 'N/A'}</span>
		</div>
		<div class="flex items-center">
			<Briefcase class="w-5 h-5 mr-2 text-gray-500" />
			<span class="text-sm font-medium text-gray-600">Main Activity:</span>
			<span class="ml-2">{period.mainActivity ?? 'N/A'}</span>
		</div>
	</div>

	<button
		class="mt-4 flex items-center text-blue-500 hover:text-blue-700"
		on:click={toggleAdditionalDetails}
	>
		{showAdditionalDetails ? 'Hide' : 'Show'} Additional Details
		{#if showAdditionalDetails}
			<ChevronUp class="ml-1 w-4 h-4" />
		{:else}
			<ChevronDown class="ml-1 w-4 h-4" />
		{/if}
	</button>

	{#if showAdditionalDetails}
		<div transition:slide class="mt-4 grid grid-cols-1 md:grid-cols-2 gap-4">
			<div class="flex items-center">
				<MapPin class="w-5 h-5 mr-2 text-gray-500" />
				<span class="text-sm font-medium text-gray-600">Address:</span>
				<span class="ml-2">{period.address ?? 'N/A'}</span>
			</div>
			<div class="flex items-center">
				<Phone class="w-5 h-5 mr-2 text-gray-500" />
				<span class="text-sm font-medium text-gray-600">Phone:</span>
				<span class="ml-2">{period.phone ?? 'N/A'}</span>
			</div>
			<div class="flex items-center">
				<Globe class="w-5 h-5 mr-2 text-gray-500" />
				<span class="text-sm font-medium text-gray-600">Website:</span>
				<span class="ml-2">{period.website ?? 'N/A'}</span>
			</div>
			<!-- Add more additional details as needed -->
		</div>
	{/if}

	{#if period.changes && period.changes.length > 0}
		<ChangesList changes={period.changes} />
	{/if}
</div>
