const CANVAS = document.querySelector("canvas")
const CONTEXT = CANVAS.getContext('2d')//retrieve the canvas context in 2D mode to access to all the 2D drawing methods, like fillRect(), drawImage(), and strokeRect()

let planeImg = new Image()
planeImg.src = "assets/plane.png"

CANVAS.width = window.innerWidth //canvas size to match the full width and height of the window, so the plane can move freely across the screen
CANVAS.height = window.innerHeight

let plane = {
    x: 0,
    y: CANVAS.height / 2 - 63, //plane starts at the left edge and vertically centered
    w: 200,
    h: 125, //w,h: plane dimension
    vy: 0, // is the rate at which the plane moves vertically
    ay: 200 //ay (vertical acceleration) simulates gravity, pulling the plane downward when no other force is applied.
}

let lastFrame = performance.now() //lastFrame stores the timestamp of the previous frame. This is used to calculate deltaT

function gameLoop() {
    let now = performance.now() // captures the current time in milliseconds
    let deltaT = (now - lastFrame) / 1000 //Convert to seconds

    lastFrame = now

    update(deltaT) //adjust the plane's position
    render() //draw the plane at its updated location
    requestAnimationFrame(gameLoop) //build-in func rAF
}

function update(deltaT) { //updates the plane’s velocity and position
    plane.vy += plane.ay * deltaT // vertical velocity
    plane.y += plane.vy * deltaT // location
}

function render() {
    CONTEXT.fillStyle = "lightblue"
    CONTEXT.strokeStyle = "black"
    CONTEXT.lineWidth = 2

    CONTEXT.fillRect(0,0,CANVAS.width,CANVAS.height) //draws the background
    CONTEXT.strokeRect(plane.x,plane.y,plane.w,plane.h) //draws a rectangle outline around the plane’s position
    CONTEXT.drawImage(planeImg,plane.x,plane.y,plane.w,plane.h) // draws the plane’s image at its current position
}

function handleFlight(e) {
    if(e.code == "Space") { //listens for the "Space" key press
        e.preventDefault()
        plane.vy = -200 //pressing space repeatedly the player can "fly" the plane, counteracting gravity briefly
    }
}
document.addEventListener("keydown", handleFlight)
// CANVAS.style.border="2px solid red"

gameLoop() //Initiates the game by calling gameLoop()
