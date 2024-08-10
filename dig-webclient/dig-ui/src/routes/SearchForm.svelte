<script lang="ts">
	import { createEventDispatcher } from 'svelte';
	import type { Entity } from '$lib/types';

	let inputValue: string = '';
	const dispatch = createEventDispatcher();

	async function handleSubmit(event: Event): Promise<void> {
		event.preventDefault();
		const url = `http://localhost:5666/insee/sirene/natural-person?term=${inputValue}`;
		console.log('url:', url);
		try {
			const response = await fetch(url);
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
            const data = await response.json();
			const searchResults: Entity[] = data.sireneUnits
			console.log('Form submitted with:', searchResults);
			dispatch('results', { searchResults });
		} catch (error) {
			console.error('There was a problem with the fetch operation:', error);
		}
	}
</script>

<form on:submit={handleSubmit}>
	<div>
		<input type="text" id="input" bind:value={inputValue} placeholder="Enter a search value" />
	</div>
	<button type="submit">Dig Deep</button>
</form>

<style>
	form {
		max-width: 400px;
		margin: 0 auto;
		padding: 1rem;
		border: 1px solid #ccc;
		border-radius: 8px;
		background-color: #f9f9f9;
	}

	div {
		margin-bottom: 1rem;
	}

	input[type='text'] {
		width: 100%;
		padding: 0.5rem;
		border: 1px solid #ccc;
		border-radius: 4px;
		box-sizing: border-box;
	}

	button {
		display: block;
		width: 100%;
		padding: 0.75rem;
		border: none;
		border-radius: 4px;
		background-color: #007bff;
		color: white;
		font-size: 1rem;
		cursor: pointer;
	}

	button:hover {
		background-color: #0056b3;
	}
</style>
