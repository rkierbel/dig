<script lang="ts">
  import type { Entity, Change } from '$lib/types.ts';
  
  export let results: Entity[] = [];
  let showPeriods: boolean[] = results.map(() => false);

  function togglePeriods(index: number) {
    showPeriods[index] = !showPeriods[index];
  }
</script>

<div class="results">
  {#each results as result, index}
    <div class="result">
      <div><strong>First Name:</strong> {result.commonFirstName}</div>
      <div><strong>Creation Date:</strong> {result.creationDate}</div>
      <div><strong>Last Modified Date:</strong> {result.lastModifiedDate}</div>
      <button on:click={() => togglePeriods(index)}>
        {showPeriods[index] ? 'Hide Periods' : 'Show Periods'}
      </button>
      {#if showPeriods[index]}
        <div><strong>Periods:</strong></div>
        {#each result.periods as period}
          <div class="period">
            <div class="period-header">Period:</div>
            <div><strong>Start Date:</strong> {period.startDate}</div>
            <div><strong>End Date:</strong> {period.endDate}</div>
            <div><strong>Changes:</strong></div>
            {#each period.changes as change}
              <div class="change">
                <div><strong>Change Reason:</strong> {change.changeReason}</div>
                <div><strong>Change Value:</strong> {change.changeValue}</div>
              </div>
            {/each}
          </div>
        {/each}
      {/if}
    </div>
  {/each}
</div>

<style>
  .results {
    max-width: 400px;
    margin: 0 auto;
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  .result {
    padding: 1rem;
    background-color: #ffffff;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    margin-bottom: 1rem;
  }
  .period {
    padding: 0.5rem;
    background-color: #f1f1f1;
    border: 1px solid #d0d0d0;
    border-radius: 6px;
    margin-top: 0.5rem;
  }
  .change {
    padding: 0.5rem;
    background-color: #e9e9e9;
    border: 1px solid #c0c0c0;
    border-radius: 4px;
    margin-top: 0.5rem;
  }
  .period-header {
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  .change div {
    margin-bottom: 0.25rem;
  }
  .result div,
  .period div,
  .change div {
    margin-bottom: 0.5rem;
  }
  .result div:last-child,
  .period div:last-child,
  .change div:last-child {
    margin-bottom: 0;
  }
</style>