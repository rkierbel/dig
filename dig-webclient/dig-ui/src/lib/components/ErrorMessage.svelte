<script lang="ts">
	import { AlertCircle, X } from 'lucide-svelte';
	import { fade } from 'svelte/transition';

	export let message: string;
	export let onClose: () => void = () => {};

	let isVisible = true;

	function handleClose() {
		isVisible = false;
		onClose();
	}
</script>

{#if isVisible}
	<div
		class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative"
		role="alert"
		transition:fade
	>
		<div class="flex items-center justify-between">
			<div class="flex items-center">
				<AlertCircle class="w-5 h-5 mr-2" />
				<strong class="font-bold mr-2">Error:</strong>
				<span class="block sm:inline">{message}</span>
			</div>
			<button
				on:click={handleClose}
				class="text-red-700 hover:text-red-900 ml-4"
				aria-label="Close error message"
			>
				<X class="w-5 h-5" />
			</button>
		</div>
	</div>
{/if}
