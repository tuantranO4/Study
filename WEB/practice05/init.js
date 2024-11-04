const CONTAINER = document.querySelector('#container') //select div id

const controlsDiv = createElement("div","controls") //createElement - helpers.js function. #div type, #controls id 
const paintDiv = createElement("div","paintArea")

const H1 = createElement("h1","mainTitle","PixArt IK edition") //h1 type, mainTitle id, PixArt... value (value = innerHTML)

const widthInput = createElement("input","widthInput","","number")//input type, widthInput id, "" value (empty innerHTML), number inputType
const heightInput = createElement("input","heightInput","","number")
const colorInput = createElement("input","colorInput","","color")

const startButton = createElement("button","startButton","START")
const saveButton = createElement("button","saveButton","SAVE") //not implemented yet

const board = createElement("table","board") //table type, board id

let width = 0
let height = 0