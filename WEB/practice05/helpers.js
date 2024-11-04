function createElement(type, id, value, inputType) {
    let element = document.createElement(type) //Specifies the type of element to create 
    element.setAttribute("id",id) //set ID attribute
    if(value) element.innerHTML = value //Sets the innerHTML or text content 
    if(inputType) element.type = inputType //nput elements to set their types (number, color...)

    return element //after many element.set, we return newly created elem
}

function delegate(parent, type, selector, handler) { //parent: DOM elem that listen for the event. Type: click, mousemove..., selector: CSS selector for child elem
    parent.addEventListener(type, function (event) {  // handler: function to execute when the event occurs on an element that matches 'selector'
        const targetElement = event.target.closest(selector) //searching DOM tree for 
        if (this.contains(targetElement)) handler.call(targetElement, event) //this: parent, handler.call: invoke handler function
    })
}
/*Example Workflow
Suppose you have a table with multiple cells (<td>), and you want to add an event listener to board (the table) so that any click on a <td> cell triggers the paintTd function:
javascript

delegate(board, "click", "td", paintTd);

When the user clicks on any <td> inside board, delegate's event listener is triggered.
event.target.closest("td") looks for the closest <td> ancestor of the clicked element.
If a matching <td> element is found within board, handler.call(targetElement, event) calls paintTd, with targetElement set as this in paintTd.
In paintTd, this will refer to the clicked <td>, allowing it to apply the color to that cell.

Summary of Core Concepts
function (event): A callback that receives event information.
event.target.closest(selector): Finds the nearest ancestor of the clicked element matching selector.
handler.call(targetElement, event): Calls the handler with this set to the matched element (targetElement), passing event as an argument.
this.contains(targetElement): Confirms targetElement is within the specified parent to ensure scope.
This approach allows efficient handling of events on dynamically created or repeated elements without attaching individual listeners to each element.

pick child elem <td>, board contain child elem <td> -> invoke function paintTd. Set const var targetElement to child elem -> invoke function event to manipulate targetElement 
 */