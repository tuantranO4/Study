    // The correct number
    const correctNumber = 32;

    // Get references to the input, button, and message display
    const guessInput = document.getElementById('guessInput');
    const guessButton = document.getElementById('guessButton');
    const message = document.getElementById('message');

    guessButton.addEventListener('click', function() {
        const userGuess = parseInt(guessInput.value, 10);

        if (isNaN(userGuess)) {
            message.textContent = 'Please enter a valid number.';
        } else if (userGuess > correctNumber) {
            message.textContent = 'Too high! Try a lower number.';
        } else if (userGuess < correctNumber) {
            message.textContent = 'Too low! Try a higher number.';
        } else {
            message.textContent = 'Correct! You guessed the number!';
        }
    });
