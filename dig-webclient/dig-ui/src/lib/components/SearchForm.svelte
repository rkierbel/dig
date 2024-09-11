<script lang="ts">
	import { createEventDispatcher } from 'svelte';
	import { search } from '$lib/http-actions/natural-person-api';
	import { Search, X } from 'lucide-svelte';

	const dispatch = createEventDispatcher();
	let searchTerm = '';
	let isLoading = false;

	async function handleSubmit() {
		if (!searchTerm.trim()) return;

		dispatch('searchStart');
		isLoading = true;
		try {
			const results = await search(searchTerm);
			dispatch('results', { searchResults: results.sireneUnits });
		} catch (error) {
			let errorMessage = 'An unknown error occurred';
			if (error instanceof Error) {
				errorMessage = error.message;
			}
			dispatch('error', { message: errorMessage });
		} finally {
			isLoading = false;
			dispatch('searchEnd');
		}
	}

	function clearSearch() {
		searchTerm = '';
	}
</script>

<form on:submit|preventDefault={handleSubmit} class="mb-8">
	<div class="flex">
		<div class="relative flex-grow">
			<input
				type="text"
				bind:value={searchTerm}
				placeholder="Enter search term"
				class="w-full pl-10 pr-10 py-2 border border-gray-300 rounded-l-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
				disabled={isLoading}
			/>
			<div class="absolute inset-y-0 left-3 flex items-center pointer-events-none">
				<Search class="h-5 w-5 text-gray-400" />
			</div>
			{#if searchTerm}
				<button
					type="button"
					class="absolute inset-y-0 right-3 flex items-center"
					on:click={clearSearch}
				>
					<X class="h-5 w-5 text-gray-400 hover:text-gray-600" />
				</button>
			{/if}
		</div>
		<button
			type="submit"
			class="px-6 py-2 bg-blue-500 text-white font-semibold rounded-r-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:bg-gray-300 disabled:cursor-not-allowed"
			disabled={isLoading}
		>
			{isLoading ? 'Searching...' : 'Search'}
		</button>
	</div>
</form>