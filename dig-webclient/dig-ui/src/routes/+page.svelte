<script lang="ts">
	import SearchResults from '$lib/SearchResults.svelte';
	import SearchForm from './SearchForm.svelte'
	import type { SearchResultData } from '$lib/types.ts';

	let searchResults: SearchResultData[] = [];

	function handleResults(event: CustomEvent) {
    searchResults = event.detail.searchResults;
  }
</script>



<SearchForm on:results={handleResults} />
<SearchResults results={searchResults} />


