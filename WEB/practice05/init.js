const CONTAINER = document.querySelector('#container')

const controlsDiv = createElement("div","controls")
const paintDiv = createElement("div","paintArea")

const H1 = createElement("h1","mainTitle","PixArt IK edition")

const widthInput = createElement("input","widthInput","","number")
const heightInput = createElement("input","heightInput","","number")
const colorInput = createElement("input","colorInput","","color")

const startButton = createElement("button","startButton","START")
const saveButton = createElement("button","saveButton","SAVE")

const board = createElement("table","board")

let width = 0
let height = 0