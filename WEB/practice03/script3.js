// Declaration
const H1 = document.querySelector('h1')
const TABLE = document.querySelector('table')

// Fill the h1 with the title
H1.innerText = 'Mandalorian - Season 1'

// Hardcode table header
//let tableHeader = ["Title", "Summary"]

// Gather table header
let tableHeader = Object.keys(episodes[0])


// Display table header
let tr = document.createElement('tr')
tableHeader.forEach(key => {
    let th = document.createElement('th')
    th.innerText = key
    tr.appendChild(th)
});
TABLE.appendChild(tr)

// Create a table for the episodes - For manual header, with choosen elements
/*
for (const episode of episodes) {
    let tr = document.createElement('tr')
    let tdName = document.createElement('td')
    let tdSummary = document.createElement('td')
    tdName.innerText = episode.name
    tr.appendChild(tdName)
    tdSummary.innerHTML = episode.summary
    tr.appendChild(tdSummary)
    TABLE.appendChild(tr)
}
*/

// Create table for all elements

for (const episode of episodes) {
    let tr = document.createElement('tr')
    for (const key in episode) {
        const data = episode[key];
        let td = document.createElement('td')
        
        // Optional task: Handle the different data types
        switch (key) {
            case 'url':
                let a = document.createElement('a')
                let icon = document.createElement('i');
                icon.classList.add('fas', 'fa-link'); 
                a.appendChild(icon);
                a.href = data
                td.appendChild(a)
                break;

            case 'airstamp':
                let date = new Date(data);
                td.innerText = date.toLocaleString('hu-HU');
                break;

            case 'runtime':
                td.innerText = data + ' minutes'
                break;

            case 'rating':
                let span = document.createElement('span')
                span.innerHTML = data
                span.classList.add('rating')
                td.appendChild(span)
                break;

            case 'image':
                let img = document.createElement('img')
                img.src = data
                img.alt = 'Episode Cover Image'
                td.appendChild(img)
                break;

            case 'summary':
                td.innerHTML = data
                td.classList.add('summary')
                break;      
                          
            default:
                td.innerText = data
                break;
        }        
        tr.appendChild(td)
    }
    TABLE.appendChild(tr)
}


// Table Row click handler
function handleTableRowClick() {
    // Check if the clicked <tr> contains any <th> elements
    if(Array.from(this.childNodes).every(e => e === 'th')) return;

    this.classList.toggle('watched')
}


// Call delegate function with proper parameters
delegate(TABLE, "click", "tr", handleTableRowClick)
// Delegate Function
function delegate(parent, type, selector, handler) {
    parent.addEventListener(type, function (event) {
        const targetElement = event.target.closest(selector);
        if (this.contains(targetElement)) {
            handler.call(targetElement, event);
        }
    });
}