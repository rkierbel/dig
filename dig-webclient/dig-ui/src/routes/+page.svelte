<script lang="ts">
	import SearchResults from '$lib/components/SearchResults.svelte';
	import SearchForm from './SearchForm.svelte';
	import type { Entity } from '$lib/types.ts';

	let searchResults: Entity[] = [];

	function handleResults(event: CustomEvent) {
		searchResults = event.detail.searchResults;
	}
</script>

<SearchForm on:results={handleResults} />
<SearchResults results={searchResults} />
