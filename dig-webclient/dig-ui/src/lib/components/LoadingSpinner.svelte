<script lang="ts">
	export let size = '2rem';
	export let color = 'currentColor';
	export let text = '';
</script>

<div class="flex flex-col justify-center items-center" aria-label={text || 'Loading'}>
	<svg
		class="animate-spin"
		style="width: {size}; height: {size};"
		viewBox="0 0 24 24"
		fill="none"
		xmlns="http://www.w3.org/2000/svg"
	>
		<circle class="opacity-25" cx="12" cy="12" r="10" stroke={color} stroke-width="4" />
		<path
			class="opacity-75"
			fill={color}
			d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
		/>
	</svg>
	{#if text}
		<p class="mt-2 text-sm text-gray-600">{text}</p>
	{/if}
</div>
