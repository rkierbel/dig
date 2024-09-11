<script lang="ts">
	import { createEventDispatcher } from 'svelte';
	import { search } from '$lib/http-actions/natural-person-api';

	const dispatch = createEventDispatcher();
	let searchTerm = '';
	let isLoading = false;

	async function handleSubmit() {
		dispatch('searchStart');
		isLoading = true;
		try {
			const results = await search(searchTerm);
			dispatch('results', { searchResults: results.sireneUnits });
		} catch (error) {
			let errorMessage = 'An unkown error occured';
			if (error instanceof Error) {
				errorMessage = error.message;
			}
			dispatch('error', { message: errorMessage });
		} finally {
			isLoading = false;
			dispatch('searchEnd');
		}
	}
</script>

<form on:submit|preventDefault={handleSubmit}>
	<div class="search-container">
		<input type="text" bind:value={searchTerm} placeholder="Enter search term" />
		<button type="submit" disabled={isLoading}>
			{isLoading ? 'Searching...' : 'Search'}
		</button>
	</div>
</form>

<style>
	form {
		margin-bottom: 2rem;
	}
	.search-container {
		display: flex;
	}
	input {
		flex-grow: 1;
		padding: 0.5rem 1rem;
		border: 1px solid #ccc;
		border-right: none;
		border-radius: 4px 0 0 4px;
	}
	button {
		padding: 0.5rem 1rem;
		background-color: #3b82f6;
		color: white;
		border: none;
		border-radius: 0 4px 4px 0;
		cursor: pointer;
	}
	button:hover {
		background-color: #2563eb;
	}
	button:disabled {
		background-color: #9ca3af;
		cursor: not-allowed;
	}
</style>
