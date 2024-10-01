// ------------- Task 1. ---------------------------------------
// Step 1. - collect all elements 
const greetButton = document.querySelector("#greeting")
const greetSpan = document.querySelector("#hello") 
//It returns the first element in the document that matches the given CSS selector reference in HTML. (id: #greeting #hello)

// Step 2. - implement the handler function 
function handleGreetingButtonClick() { //function for const var (greetButton/greetSpan) when click  (what it do when click action executed)
    greetButton.style.display = "none"
    greetSpan.innerHTML = "Welcome on my Course!" //innerHTML: change the text in the span.
    //Also, innerhtml retrieve html elements and such, different from innertext (unrendered element)
}

// Step 3. - Link the two things together
greetButton.addEventListener('click', handleGreetingButtonClick)
//When the button with ID greeting is clicked, run the callback function handleGreetingButtonClick.

// ------------- Task 2. ---------------------------------------
const target = Math.floor(Math.random()*100) //floor
const guessInput = document.querySelector("input#guess") //input with id#guess ->get user guess
const guessButton = document.querySelector("button#guess") //button with id#guess ->click event
const guessOutput = document.querySelector("span#guess") //span with id#guess ->display feedback

function handleGuessButtonClick(){
    let guessed = parseInt(guessInput.value) //parseint the guessInput.value Display with guessOutput accordingly (let varname = ...)
    if(guessed === target) guessOutput.innerText = "You find it!"
    else if (guessed > target) guessOutput.innerText = "Lower!" 
    else guessOutput.innerText = "Greater!"
}

guessButton.addEventListener('click', handleGuessButtonClick) //When the user clicks the button, the handleGuessButtonClick function is executed.