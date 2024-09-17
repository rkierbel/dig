<script lang="ts">
	import type { Change } from '$lib/types';
	import { fade, slide } from 'svelte/transition';
	import { ChevronDown, ChevronUp } from 'lucide-svelte';

	export let changes: Change[] = [];
	let isExpanded = false;

	function toggleExpanded() {
		isExpanded = !isExpanded;
	}
</script>

{#if changes.length > 0}
	<div class="mt-4" transition:fade>
		<button
			class="flex items-center justify-between w-full text-lg font-semibold mb-2 text-left"
			on:click={toggleExpanded}
		>
			<span>Changes ({changes.length})</span>
			{#if isExpanded}
				<ChevronUp class="h-5 w-5" />
			{:else}
				<ChevronDown class="h-5 w-5" />
			{/if}
		</button>

		{#if isExpanded}
			<ul class="space-y-2" transition:slide>
				{#each changes as change}
					<li class="bg-gray-100 p-3 rounded">
						<span class="font-medium text-gray-700">{change.changeReason ?? 'Unknown'}:</span>
						<span class="text-gray-600">{change.changeValue ?? 'N/A'}</span>
					</li>
				{/each}
			</ul>
		{/if}
	</div>
{/if}
