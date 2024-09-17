<script lang="ts">
	import SearchForm from '$lib/components/SearchForm.svelte';
	import SearchResults from './SearchResults.svelte';
	import LoadingSpinner from '$lib/components/LoadingSpinner.svelte';
	import ErrorMessage from '$lib/components/ErrorMessage.svelte';
	import type { SirenUnit } from '$lib/types';
	import { fade } from 'svelte/transition';

	let searchResults: SirenUnit[] = [];
	let isLoading = false;
	let error = '';

	function handleSearchResults(event: CustomEvent<{ searchResults: SirenUnit[] }>) {
		searchResults = event.detail.searchResults;
	}

	function handleSearchStart() {
		isLoading = true;
		error = '';
	}

	function handleSearchEnd() {
		isLoading = false;
	}

	function handleSearchError(event: CustomEvent<{ message: string }>) {
		error = event.detail.message;
	}
</script>

<div class="container mx-auto max-w-3xl px-4">
	<SearchForm
		on:results={handleSearchResults}
		on:searchStart={handleSearchStart}
		on:searchEnd={handleSearchEnd}
		on:error={handleSearchError}
	/>

	{#if isLoading}
		<div class="flex justify-center my-8" transition:fade>
			<LoadingSpinner size="3rem" />
		</div>
	{:else if error}
		<div transition:fade>
			<ErrorMessage message={error} />
		</div>
	{:else if searchResults.length > 0}
		<div transition:fade>
			<SearchResults results={searchResults} />
		</div>
	{:else}
		<p class="text-center text-gray-500 my-8" transition:fade>
			No results to display. Start by searching for an entity.
		</p>
	{/if}
</div>
