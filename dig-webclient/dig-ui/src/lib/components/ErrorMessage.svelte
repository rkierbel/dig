<script lang="ts">
	export let message: string;
</script>

<div class="error-message" role="alert">
	<strong>Error:</strong>
	<span>{message}</span>
</div>

<style>
	.error-message {
		background-color: #fee2e2;
		border: 1px solid #f87171;
		color: #b91c1c;
		padding: 1rem;
		border-radius: 4px;
		margin-bottom: 1rem;
	}
	strong {
		margin-right: 0.5rem;
	}
</style>
