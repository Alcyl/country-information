function buildPieChart(newConfirmed, newDeaths, newRecovered) {
    let ctx = document.getElementById('coronaPieChart');
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

function buildBarChart(totalConfirmed, totalDeaths, totalRecovered) {
    let ctx = document.getElementById('coronaBarChart');
    const coronaBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [
                'Total Confirmed',
                'Total Deaths',
                'Total Recovered'
            ],
            datasets: [{
                label: 'Total Global Corona Information',
                data: [totalConfirmed, totalDeaths, totalRecovered],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(255, 205, 86, 0.2)'
                ],
                borderColor: [
                    'rgb(255, 99, 132)',
                    'rgb(255, 159, 64)',
                    'rgb(255, 205, 86)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        },
    });
}

function buildLineChart(coronaEndOfMonth) {
    let ctx = document.getElementById('coronaLineChart');
    const labels = [];
    for(i = 1;i<13;i++){
        labels.push(i);
    }

    const coronaBarChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'My First Dataset',
                data: coronaEndOfMonth,
                fill: true,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        }
    })
};














function setURL() {
// TODO: replace URL
    var actionSrc = "http://localhost:8081/search/" + document.getElementById("value").value;
    var searchForm = document.getElementById('searchForm');
    searchForm.action = actionSrc ;
}