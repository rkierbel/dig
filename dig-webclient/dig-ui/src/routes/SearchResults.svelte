<script lang="ts">
	import type { SirenUnit } from '$lib/types';
	import SireneUnit from '$lib/components/SireneUnit.svelte';

	export let results: SirenUnit[] = [];
</script>

<div class="results-container">
	{#each results as sireneUnit (sireneUnit.siren)}
		<SireneUnit {sireneUnit} />
	{/each}
</div>

<style>
	.results-container {
		display: flex;
		flex-direction: column;
		gap: 1.5rem;
	}
</style>
