homeScreen()

startButton.addEventListener('click',handleStartButtonClick)

delegate(board, "click", "td", paintTd)
delegate(board, "mousemove", "td", paintTd)






