<script lang="ts">
	import type { Change } from '$lib/types';

	export let changes: Change[] = [];
</script>

<div class="changes-list">
	<h4>Changes:</h4>
	<ul>
		{#each changes as change}
			<li>
				<span class="change-reason">{change.changeReason ?? 'Unknown'}:</span>
				{change.changeValue ?? 'N/A'}
			</li>
		{/each}
	</ul>
</div>

<style>
	.changes-list {
		margin-top: 1rem;
	}
	h4 {
		font-weight: 600;
		margin-bottom: 0.5rem;
	}
	ul {
		list-style-type: none;
		padding: 0;
	}
	li {
		background-color: #f3f4f6;
		padding: 0.75rem;
		border-radius: 4px;
		margin-bottom: 0.5rem;
	}
	.change-reason {
		font-weight: 500;
	}
</style>
