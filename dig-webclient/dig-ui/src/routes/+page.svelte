<script lang="ts">
	import SearchForm from '$lib/components/SearchForm.svelte';
	import SearchResults from './SearchResults.svelte';
	import LoadingSpinner from '$lib/components/LoadingSpinner.svelte';
	import ErrorMessage from '$lib/components/ErrorMessage.svelte';
	import type { SirenUnit } from '$lib/types';

	let searchResults: SirenUnit[] = [];
	let isLoading = false;
	let error = '';

	function handleSearchResults(event: CustomEvent) {
		searchResults = event.detail.searchResults;
	}

	function handleSearchStart() {
		isLoading = true;
		error = '';
	}

	function handleSearchEnd() {
		isLoading = false;
	}

	function handleSearchError(event: CustomEvent) {
		error = event.detail.message;
	}
</script>

<div class="container">
	<SearchForm
		on:results={handleSearchResults}
		on:searchStart={handleSearchStart}
		on:searchEnd={handleSearchEnd}
		on:error={handleSearchError}
	/>

	{#if isLoading}
		<LoadingSpinner size="3rem" />
	{:else if error}
		<ErrorMessage message={error} />
	{:else if searchResults.length > 0}
		<SearchResults results={searchResults} />
	{:else}
		<p class="no-results">No results to display. Start by searching for an entity.</p>
	{/if}
</div>

<style>
	.container {
		max-width: 800px;
		margin: 0 auto;
	}

	.no-results {
		text-align: center;
		color: #6b7280;
	}
</style>
