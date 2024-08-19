<script lang="ts">
	import type { Entity } from '$lib/types';
	import EntityCard from '$lib/components/EntityCard.svelte';

	export let results: Entity[] = [];
</script>

<div class="results-container">
	{#each results as entity (entity.siren)}
		<EntityCard {entity} />
	{/each}
</div>

<style>
	.results-container {
		display: flex;
		flex-direction: column;
		gap: 1.5rem;
	}
</style>
