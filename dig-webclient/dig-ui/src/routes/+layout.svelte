<script>
	import '../app.css';
</script>

<div class="flex flex-col min-h-screen">
	<header class="bg-gray-100 p-4">
		<h1 class="text-3xl font-bold text-center text-gray-800">
			Dig - Every Fraudster's Worst Nightmare
		</h1>
	</header>

	<main class="flex-1 p-8">
		<slot />
	</main>

	<footer class="bg-gray-100 p-4 text-center text-gray-600">
		<p>&copy; 2024 Dig. All rights reserved.</p>
	</footer>
</div>
