// ------------- Task 1. ---------------------------------------
// Step 1. - collect all elements 
const greetButton = document.querySelector("#greeting")
const greetSpan = document.querySelector("#hello")

// Step 2. - implement the handler function 
function handleGreetingButtonClick() {
    greetButton.style.display = "none"
    greetSpan.innerHTML = "Welcome on my Course!"
}

// Step 3. - Link the two things together
greetButton.addEventListener('click', handleGreetingButtonClick)


// ------------- Task 2. ---------------------------------------
const target = Math.floor(Math.random()*100)
const guessInput = document.querySelector("input#guess")
const guessButton = document.querySelector("button#guess")
const guessOutput = document.querySelector("span#guess")

function handleGuessButtonClick(){
    let guessed = parseInt(guessInput.value)
    if(guessed === target) guessOutput.innerText = "You find it!"
    else if (guessed > target) guessOutput.innerText = "Lower!"
    else guessOutput.innerText = "Greater!"
}

guessButton.addEventListener('click', handleGuessButtonClick)