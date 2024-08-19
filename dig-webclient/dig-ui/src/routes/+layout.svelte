<script>
	import '../global.css';
</script>

<div class="app">
	<header>
		<h1>Dig - Every Fraudster's Worst Nightmare</h1>
	</header>

	<main>
		<slot />
	</main>

	<footer>
		<p>&copy; 2024 Dig. All rights reserved.</p>
	</footer>
</div>

<style>
	.app {
		display: flex;
		flex-direction: column;
		min-height: 100vh;
	}

	header,
	footer {
		padding: 1rem;
		background-color: #f3f4f6;
	}

	main {
		flex: 1;
		padding: 2rem;
	}

	h1 {
		font-size: 2rem;
		font-weight: 700;
		text-align: center;
		color: #1f2937;
	}

	footer {
		text-align: center;
		color: #6b7280;
	}
</style>
