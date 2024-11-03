const CANVAS = document.querySelector("canvas")
const CONTEXT = CANVAS.getContext('2d')

let planeImg = new Image()
planeImg.src = "assets/plane.png"

CANVAS.width = window.innerWidth
CANVAS.height = window.innerHeight

let plane = {
    x: 0,
    y: CANVAS.height / 2 - 63,
    w: 200,
    h: 125,
    vy: 0,
    ay: 200
}

let lastFrame = performance.now()

function gameLoop() {
    let now = performance.now()
    let deltaT = (now - lastFrame) / 1000

    lastFrame = now

    update(deltaT)
    render()
    requestAnimationFrame(gameLoop)
}

function update(deltaT) {
    plane.vy += plane.ay * deltaT
    plane.y += plane.vy * deltaT
}

function render() {
    CONTEXT.fillStyle = "lightblue"
    CONTEXT.strokeStyle = "black"
    CONTEXT.lineWidth = 2

    CONTEXT.fillRect(0,0,CANVAS.width,CANVAS.height)
    CONTEXT.strokeRect(plane.x,plane.y,plane.w,plane.h)
    CONTEXT.drawImage(planeImg,plane.x,plane.y,plane.w,plane.h)
}

function handleFlight(e) {
    if(e.code == "Space") {
        e.preventDefault()
        plane.vy = -200
    }
}

document.addEventListener("keydown", handleFlight)
// CANVAS.style.border="2px solid red"
gameLoop()
