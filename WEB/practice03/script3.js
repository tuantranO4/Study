// Declaration
const H1 = document.querySelector('h1')
const TABLE = document.querySelector('table')

// Fill the h1 with the title
H1.innerText = 'Mandalorian - Season 1' //(check style3.css) 

// Hardcode table header
//let tableHeader = ["Title", "Summary"]

//TABLE HEADER=========================================
// Gather table header
let tableHeader = Object.keys(episodes[0]) //return the keys(id, url, name...) not value(chapter 1, 2019-xx-xx...)
//why episodes[0]? doesnt matter, it just returns key(id, url...), also works with 1 2 3...

//HEADER CELL MAKER=========================================
// Display table header
let tr = document.createElement('tr') //create .js table row in memory (rationalise it in appendChild)
tableHeader.forEach(key => { 
    let th = document.createElement('th'); // Create a new table header cell (<th>) element
    th.innerText = key; // Set the text content of the header cell to the current key
    tr.appendChild(th); // Append the header cell to the row
});
TABLE.appendChild(tr); // Append the populated table row to the actual table in the document
//---------
//Object.keys(episodes[0]) returns: ["id", "url", "name", "season", "number", "type", ...]
//so the key in foreach loops iterate thru that returned array
//---------

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

//TABLE ROW POPULATING LOOP=========================================
// Create table for all elements
for (const episode of episodes) { //iterate through episodes const (data3.js)
    let tr = document.createElement('tr') //create table row (vertical for each episode)
    
    for (const key in episode) {//no need for tableHeader because we directly accessing eposides. 
        //const key: official syntax to access properties in for...in... loop 
        const data = episode[key]; //store the corresponding value of key (url, airstamp...)

        let td = document.createElement('td') //create table cell 'td' elems in memory (append later)
        
        // Optional task: Handle the different data types
        switch (key) {
            case 'url':
                let a = document.createElement('a') //anchor element (website hyperlink with href attribute)
                let icon = document.createElement('i');//icon element 
                icon.classList.add('fas', 'fa-link'); //style icon with css classes
                a.appendChild(icon);//append icon to anchor
                a.href = data //set href to data=ep.key[] (in this case key=url)
                td.appendChild(a)//append anchor to table cell
                break;

            case 'airstamp':
                let date = new Date(data);
                td.innerText = date.toLocaleString('hu-HU'); //Converts the airstamp string to a Date object.
                //then formats it as a local string using Hungarian locale.
                break;

            case 'runtime':
                td.innerText = data + ' minutes' //display runtime in minute
                break;

            case 'rating':
                let span = document.createElement('span') //display rating in astyled span
                span.innerHTML = data
                span.classList.add('rating')
                td.appendChild(span) //besure to add them later if we not modify 'td' directly (like td.innerText)
                break;

            case 'image':
                let img = document.createElement('img') //create img elem
                img.src = data //souce of image
                img.alt = 'Episode Cover Image' //alt text
                td.appendChild(img)
                break;

            case 'summary'://display summary innerhtml
                td.innerHTML = data
                td.classList.add('summary')
                break;      
                //td.classList.add() is a method used to add one or more CSS styles to an HTML element's class attribute.
                //way to manipulate CSS (we use those styled elems in style3.css, and use .add for the picturisation)          
                
            default:
                td.innerText = data //default case (id, ep, season)
                break;
        }        
        tr.appendChild(td)//shape up td on tr
    }
    TABLE.appendChild(tr) //picturising tr (included td)
}

 

//INTERACTIVE CLICKER HANDLING===========================
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