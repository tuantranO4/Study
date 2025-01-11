const refreshButton = document.querySelector('#refresh')
const filterInput = document.querySelector('#filter')
const table = document.querySelector('table')

async function refreshTable() {
    let resp = await fetch('table.php?filter=' + filterInput.value)
    let data = await resp.json()

    table.innerHTML = ''

    data.forEach(row => {
        let tr = document.createElement('tr')
        for (const field in row) {
            let td = document.createElement('td')
            td.innerHTML = row[field]
            tr.appendChild(td)
        }
        table.appendChild(tr)
    });
}

refreshButton.addEventListener('click', refreshTable)
filterInput.addEventListener('input', refreshTable)