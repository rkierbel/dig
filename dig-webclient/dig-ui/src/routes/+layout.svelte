<div class="container">
    <header>
        <h1>Dig</h1>
        <h2>Every Fraudster's Worst Nightmare</h2>
        <nav>
            <a href="/">Home</a>
            <a href="/about">About</a>
            <a href="/contact">Contact</a>
        </nav>
    </header>

    <main>
        <slot></slot>
    </main>
</div>

<style>
    .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 2rem;
        background-color: #f5f5f5;
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    header {
        text-align: center;
        margin-bottom: 2rem;
    }

    h1 {
        font-family: 'Sankofa Display', sans-serif;
        font-size: 2.5rem;
        margin-bottom: 0.5rem;
    }

    h2 {
        color: #666;
        font-size: 1.25rem;
        margin-bottom: 1rem;
    }

    nav {
        margin-top: 1rem;
    }

    nav a {
        margin: 0 1rem;
        color: #0070f3;
        text-decoration: none;
        font-size: 1rem;
    }

    nav a:hover {
        text-decoration: underline;
    }

    main {
        display: flex;
        flex-direction: column;
        gap: 2rem;
    }

    @media (max-width: 600px) {
        .container {
            padding: 1rem;
        }

        h1 {
            font-size: 2rem;
        }

        h2 {
            font-size: 1rem;
        }

        nav a {
            margin: 0 0.5rem;
            font-size: 0.875rem;
        }
    }
</style>