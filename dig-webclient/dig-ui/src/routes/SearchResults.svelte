<script lang="ts">
	import type { SirenUnit } from '$lib/types';
	import SireneUnit from '$lib/components/SireneUnit.svelte';
	import { fade } from 'svelte/transition';

	export let results: SirenUnit[] = [];
</script>

<div class="space-y-6">
	{#each results as sireneUnit (sireneUnit.siren)}
		<div transition:fade>
			<SireneUnit {sireneUnit} />
		</div>
	{/each}
</div>

{#if results.length === 0}
	<p class="text-center text-gray-500 my-8" transition:fade>
		No results found. Try adjusting your search criteria.
	</p>
{/if}
