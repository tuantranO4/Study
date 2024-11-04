function homeScreen() {
    CONTAINER.appendChild(H1) //add H1 (init.js) to Container const (init.html)
    CONTAINER.appendChild(controlsDiv) //div elem (div - kẻ khổ)

    controlsDiv.appendChild(createElement('label',"width", "Width:")) //type: label, id: width, "Width: " value
    controlsDiv.appendChild(widthInput)

    controlsDiv.appendChild(createElement('label',"height","Height:")) //making label for Height (same as above)
    controlsDiv.appendChild(heightInput)

    controlsDiv.appendChild(startButton)//startButton Const (init.js) click function append
}



function paintingScreen(width, height) { //making painting screen
    controlsDiv.innerHTML = '' //Clears out any existing content within controlsDiv ('')
    H1.innerText += '/drawing' //appending the text "/drawing" to H1
    controlsDiv.appendChild(createElement("label","colorInput","Choose a color: ")) //label type, colorInput const (init.js)
    controlsDiv.appendChild(colorInput) //append colorInput const to div controlsDiv
    CONTAINER.appendChild(paintDiv) //add new div paintDiv for paint
    paintDiv.appendChild(board)  //add board const elem to the paintDiv (div)

    for (let i = 0; i < height; i++){ //make a table width x height
        let tr = document.createElement("tr") //table row
        for (let j = 0; j < width; j++){
            let td = document.createElement("td") //table data (pixel to paint)
            tr.appendChild(td)
        }
        board.appendChild(tr)
    }
}


function handleStartButtonClick() {
    height = heightInput.valueAsNumber //get the numeric values .valueAsNumber
    width = widthInput.valueAsNumber

    if ((height >= 5 && height <= 30) && (width >= 5 && width <= 30)) {
        paintingScreen(width, height)
    } else {
        if(!document.querySelector("#inputError")) //if doesn't have inputError
            controlsDiv.appendChild(createElement("span","inputError","All values must between 5 and 30!")) //error msg span type
    }
}


function paintTd(event){
    event.preventDefault() //Blocking default click handling
    if (event.type == "click" || event.buttons == 1) //checks if the left mouse button (button code 1) is being held down (code 0 1 2)
        this.style.backgroundColor = colorInput.value //this: <td>, colorInput.color
}
