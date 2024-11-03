function homeScreen() {
    CONTAINER.appendChild(H1)
    CONTAINER.appendChild(controlsDiv)

    controlsDiv.appendChild(createElement('label',"width", "Width:"))
    controlsDiv.appendChild(widthInput)

    controlsDiv.appendChild(createElement('label',"height","Height:"))
    controlsDiv.appendChild(heightInput)

    controlsDiv.appendChild(startButton)
}
function paintingScreen(width, height) {
    controlsDiv.innerHTML = ''
    H1.innerText += '/drawing'
    controlsDiv.appendChild(createElement("label","colorInput","Choose a color: "))
    controlsDiv.appendChild(colorInput)
    CONTAINER.appendChild(paintDiv)
    paintDiv.appendChild(board)

    for (let i = 0; i < height; i++){
        let tr = document.createElement("tr")
        for (let j = 0; j < width; j++){
            let td = document.createElement("td")
            tr.appendChild(td)
        }
        board.appendChild(tr)
    }
}

function handleStartButtonClick() {
    height = heightInput.valueAsNumber
    width = widthInput.valueAsNumber

    if ((height >= 5 && height <= 30) && (width >= 5 && width <= 30)) {
        paintingScreen(width, height)
    } else {
        if(!document.querySelector("#inputError")) 
            controlsDiv.appendChild(createElement("span","inputError","All values must between 5 and 30!"))
    }
}


function paintTd(event){
    event.preventDefault()
    if (event.type == "click" || event.buttons == 1)
        this.style.backgroundColor = colorInput.value
}
