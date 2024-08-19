import type { SearchResponse } from './types';

const API_BASE_URL = 'http://localhost:5666';

export async function search(term: string): Promise<SearchResponse> {
    const response = await fetch(`${API_BASE_URL}/insee/sirene/natural-person?term=${encodeURIComponent(term)}`);
    if (!response.ok) {
        throw new Error('Failed to fetch search results');
    }
    return await response.json();
}