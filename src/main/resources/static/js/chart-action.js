function buildChart(newConfirmed, newDeaths, newRecovered) {
let ctx = document.getElementById('coronaChart');

let coronaChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: [
      'New Confirmed',
      'New Deaths',
      'New Recovered'
    ],
    datasets: [{
      label: 'New Global Corona Information',
      //TODO: fill with data from corona API
      data: [newConfirmed, newDeaths, newRecovered],
      backgroundColor: [
        'rgb(255, 99, 132)',
        'rgb(54, 162, 235)',
        'rgb(255, 205, 86)'
      ],
      hoverOffset: 4
    }]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});

}
