var ctx = document.getElementById('myChart');

var myChart = new Chart(ctx, {
    type: 'pie',
    data:  {
  labels: [
    'Red',
    'Blue',
    'Yellow'
  ],
  datasets: [{
    label: 'My First Dataset',
//TODO: fill with data from corona API
    data: [300, 50, 100],
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