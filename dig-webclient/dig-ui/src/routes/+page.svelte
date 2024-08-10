<script lang="ts">
	import SearchResults from '$lib/SearchResults.svelte';
	import SearchForm from './SearchForm.svelte'
	import type { SearchResultData } from '$lib/types.ts';

	let searchResults: SearchResultData[] = [];

	function handleResults(event: CustomEvent) {
    searchResults = event.detail.searchResults;
  }
</script>

<h1>Dig</h1>
<h2>Every Fraudster's Worst Nightmare</h2>

<SearchForm on:results={handleResults} />
<SearchResults results={searchResults} />

<style>
	h1 {
		text-align: center;
		font-family: "Sankofa Display", sans-serif;
	}

	h2 {
		text-align: center;
		color: #666;
	}

	
</style>
