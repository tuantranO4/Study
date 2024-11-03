function createElement(type, id, value, inputType) {
    let element = document.createElement(type)
    element.setAttribute("id",id)
    if(value) element.innerHTML = value
    if(inputType) element.type = inputType

    return element
}

function delegate(parent, type, selector, handler) {
    parent.addEventListener(type, function (event) {
        const targetElement = event.target.closest(selector)
        if (this.contains(targetElement)) handler.call(targetElement, event)
    })
}
