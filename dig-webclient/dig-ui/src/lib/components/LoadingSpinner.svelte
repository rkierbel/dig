<script lang="ts">
	export let size = '2rem';
</script>

<div class="spinner-container">
	<div class="spinner" style="width: {size}; height: {size};"></div>
</div>

<style>
	.spinner-container {
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.spinner {
		border: 4px solid #e5e7eb;
		border-top: 4px solid #3b82f6;
		border-radius: 50%;
		animation: spin 1s linear infinite;
	}
	@keyframes spin {
		0% {
			transform: rotate(0deg);
		}
		100% {
			transform: rotate(360deg);
		}
	}
</style>
