const mainMenu = document.querySelector('#mainMenu');
const descMenu = document.querySelector('#descMenu');
const gameScreen = document.querySelector('#gameScreen');
const gameGrid = document.querySelector('#gameGrid');
const playerDisplay = document.querySelector('#playerDisplay');
const timerDisplay = document.querySelector('#timer');
const loadGameBtn = document.querySelector('#loadGameBtn');
const saveGameBtn = document.querySelector('#saveGameBtn');
const exitBtn = document.querySelector('#exitBtn');

let selectedDifficulty = null;
let playerName = '';
let gridData = [];
let timer;
let startTime;

const easyLevels = [
    [
        ["empty", "mountain_90", "empty", "empty", "oasis"],//1
        ["empty", "empty", "empty", "bridge", "oasis"],
        ["bridge", "empty", "mountain_180", "empty", "empty"],
        ["empty", "empty", "empty", "oasis", "empty"],
        ["empty", "empty", "mountain_270", "empty", "empty"]
    ],
    [
        ["oasis", "empty_90", "bridge_270", "empty", "empty"],//2
        ["empty", "mountain_180", "empty", "empty", "mountain_180"],
        ["bridge", "oasis", "mountain_270", "empty", "empty"],
        ["empty", "empty", "empty", "oasis", "empty"],
        ["empty", "empty", "empty_270", "empty", "empty"]
    ],
    [
        ["empty", "empty_90", "bridge_270", "empty", "empty"],//3
        ["empty", "empty_180", "empty", "empty", "bridge_180"],
        ["empty", "mountain_180", "bridge", "empty", "empty"],
        ["empty", "oasis", "empty", "empty", "empty"],
        ["empty", "bridge_270", "empty_270", "empty", "mountain_180"]
    ],
    [
        ["empty", "empty", "empty", "bridge_270", "empty"],//4
        ["empty", "empty", "empty", "empty", "empty"],
        ["bridge", "empty", "mountain_90", "empty", "mountain_90"],
        ["empty", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "oasis", "mountain_270", "empty"]
    ],
    [
        ["empty", "empty", "bridge_270", "empty", "empty"],//5
        ["empty", "mountain", "empty", "empty", "empty"],
        ["bridge", "empty", "empty", "mountain_270", "empty"],
        ["empty", "empty", "bridge", "oasis", "empty"],
        ["empty", "mountain_180", "empty", "empty", "empty"]
    ],
];


const hardLevels = [
    [
        ["empty", "mountain_90", "oasis", "oasis", "empty", "bridge_270", "empty"], //1
        ["bridge", "empty", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "bridge", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "empty", "mountain_270", "empty", "empty", "empty"],
        ["mountain_270", "empty", "mountain_90", "empty", "bridge_270", "empty", "oasis"],
        ["empty", "empty", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "empty", "bridge_270", "empty", "empty", "empty"]
    ],
    [
        ["empty", "empty", "oasis", "empty", "empty", "empty", "empty"],//2
        ["bridge", "empty", "bridge_270", "empty", "empty", "mountain_180", "empty"],
        ["empty", "empty", "bridge_270", "empty", "empty", "empty", "bridge"],
        ["mountain", "empty", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "oasis", "empty", "mountain_90", "empty", "empty", "empty"],
        ["empty", "mountain", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "oasis", "empty", "empty", "empty", "empty"]
    ],
    [
        ["empty", "empty", "bridge_270", "empty", "empty", "empty", "empty"],//3
        ["empty", "empty", "empty", "empty", "empty", "empty", "bridge"],
        ["oasis", "empty", "mountain_270", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "oasis", "mountain_270", "empty", "bridge_270", "empty", "empty"],
        ["bridge", "empty", "empty", "empty", "empty", "mountain_90", "empty"],
        ["empty", "empty", "oasis", "mountain_270", "empty", "empty", "empty"]
    ],
    [
        ["empty", "empty", "empty_270", "empty", "empty", "empty", "empty"],//4
        ["empty", "empty", "empty", "bridge", "empty", "mountain_180", "empty"],
        ["empty", "empty_270", "mountain_270", "empty", "empty", "empty", "empty"],
        ["empty", "bridge_270", "empty", "oasis", "empty", "bridge_270", "empty"],
        ["empty", "empty", "mountain_180", "empty", "mountain_90", "empty", "empty"],
        ["bridge", "empty", "empty", "empty", "empty", "mountain_270", "empty"],
        ["empty", "empty", "empty", "empty_270", "empty", "empty", "empty"]
    ],
    [
        ["empty", "empty", "empty", "empty", "empty", "empty", "empty"],//5
        ["empty", "empty", "empty", "empty", "empty", "mountain", "empty"],
        ["empty", "bridge_270", "bridge_270", "empty", "mountain_90", "empty", "empty"],
        ["empty", "empty", "empty", "empty", "empty", "empty", "empty"],
        ["empty", "empty", "mountain", "empty", "oasis", "empty", "empty"],
        ["empty", "mountain_180", "empty", "bridge", "empty", "empty", "empty"],
        ["empty", "empty", "empty", "empty", "empty", "empty", "empty"]
    ]
];
const railConnections = {
    straight_rail_0: [[0, -1], [0, 1]], 
    straight_rail_90: [[-1, 0], [1, 0]], 
    curve_rail_0: [[0, 1], [-1, 0]], 
    curve_rail_90: [[-1, 0], [0, -1]], 
    curve_rail_180: [[0, -1], [1, 0]], 
    curve_rail_270: [[1, 0], [0, 1]], 
    bridge_rail_0: [[0, -1], [0, 1]], 
    mountain_rail_0: [[1, 0], [-1, 0]]
};


mainMenu.innerHTML = `
    <h1>RAILWAYS</h1>
    <input type="text" id="playerName" placeholder="WHO ARE YOU?">
    <div class="difficulty">
        <p>SET THE DIFFICULTY!</p>
        <button class="difficulty-btn" data-difficulty="Easy">5 X 5</button>
        <button class="difficulty-btn" data-difficulty="Hard">7 X 7</button>
    </div>
    <button id="rulesBtn">RULES</button>
    <button id="startBtn">START GAME</button>
`;

document.querySelectorAll('.difficulty-btn').forEach(btn => {
    btn.addEventListener('click', (event) => {
        selectedDifficulty = event.target.dataset.difficulty;
        document.querySelectorAll('.difficulty-btn').forEach(b => b.classList.remove('selected'));
        event.target.classList.add('selected');
    });
});

// Show rule
document.querySelector('#rulesBtn').addEventListener('click', () => {
    descMenu.style.display = 'block';
});

// Close rules 
document.querySelector('#closeRulesButton').addEventListener('click', () => {
    descMenu.style.display = 'none';
});
let previousDifficulty = null;
let previousPlayerName = '';  
let savedStartTime = null; 

function startGame() {
    const currentPlayerName = document.querySelector('#playerName').value.trim();
    if (currentPlayerName !== previousPlayerName || selectedDifficulty !== previousDifficulty) {
        resetGameState(); 
        playerName = currentPlayerName;
        previousPlayerName = currentPlayerName; 
        previousDifficulty = selectedDifficulty;
    }
    
    if (!playerName || !selectedDifficulty) {
        console.log("Please enter your name and select a difficulty to start the game.");
        return;
    }

    mainMenu.style.display = 'none';
    gameScreen.style.display = 'flex';
    playerDisplay.textContent = playerName;

    if (!startTime) {
        startTime = new Date();
    }
    clearInterval(timer);
    timer = setInterval(updateTimer, 1000);

    if (!gridData.length) {
        gridData = getRandomMap(selectedDifficulty);
    }
    renderMap(gridData);
}


document.querySelector('#startBtn').addEventListener('click', () => {
    playerName = document.querySelector('#playerName').value;
    if (!playerName || !selectedDifficulty) {
        console.log("Please enter your name and select a difficulty to start the game.");
        return;
    }
    startGame();
});

let easyMapIndex = 0;
let hardMapIndex = 0;


function updateTimer() {
    const now = new Date();
    const elapsed = Math.floor((now - startTime) / 1000);
    const minutes = String(Math.floor(elapsed / 60)).padStart(2, '0');
    const seconds = String(elapsed % 60).padStart(2, '0');
    timerDisplay.textContent = `${minutes}:${seconds}`;
}
function updateCellDisplay(cellDiv, tileType) {
    const baseType = tileType.replace(/_\d+$/, ''); 
    const rotation = {
        'bridge_90': '90deg',
        'bridge_180': '180deg',
        'bridge_270': '270deg',
        'mountain_90': '90deg',
        'mountain_180': '180deg',
        'mountain_270': '270deg',
        'empty_90': '90deg',
        'empty_180': '180deg',
        'empty_270': '270deg',
        'straight_rail_90': '90deg',
        'straight_rail_180': '180deg',
        'straight_rail_270': '270deg',
        'curve_rail_90': '90deg',
        'curve_rail_180': '180deg',
        'curve_rail_270': '270deg'
    };

    const rotationAngle = rotation[tileType] || '0deg';

    const imgPaths = {
        'empty': 'starter_eng/pics/tiles/empty.png',
        'bridge': 'starter_eng/pics/tiles/bridge.png',
        'bridge_rail': 'starter_eng/pics/tiles/bridge_rail.png',
        'mountain': 'starter_eng/pics/tiles/mountain.png',
        'mountain_rail': 'starter_eng/pics/tiles/mountain_rail.png',
        'oasis': 'starter_eng/pics/tiles/oasis.png',
        'straight_rail': 'starter_eng/pics/tiles/straight_rail.png',
        'curve_rail': 'starter_eng/pics/tiles/curve_rail.png'
    };const imgPath = imgPaths[baseType] || 'starter_eng/pics/tiles/empty.png';

    console.log(`Updating cell at (${cellDiv.dataset.row}, ${cellDiv.dataset.col}) with image: ${imgPath} and rotation: ${rotationAngle}`);

    cellDiv.innerHTML = `<img src="${imgPath}" alt="${baseType} tile" style="transform: rotate(${rotationAngle});">`;

}

gameGrid.addEventListener('contextmenu', (e) => e.preventDefault());
function updateCellDisplay(cellDiv, tileType) {
    const baseType = tileType.replace(/_\d+$/, ''); 
    const rotation = {
        'bridge_90': '90deg',
        'bridge_180': '180deg',
        'bridge_270': '270deg',
        'mountain_90': '90deg',
        'mountain_180': '180deg',
        'mountain_270': '270deg',
        'empty_90': '90deg',
        'empty_180': '180deg',
        'empty_270': '270deg',
        'straight_rail_90': '90deg',
        'straight_rail_180': '180deg',
        'straight_rail_270': '270deg',
        'curve_rail_90': '90deg',
        'curve_rail_180': '180deg',
        'curve_rail_270': '270deg',
        'mountain_rail_90': '90deg',
        'mountain_rail_180': '180deg',
        'mountain_rail_270': '270deg',
        'bridge_rail_90': '90deg',
        'bridge_rail_180': '180deg',
        'bridge_rail_270': '270deg'
    };

    const rotationAngle = rotation[tileType] || '0deg';

    const imgPaths = {
        'empty': 'starter_eng/pics/tiles/empty.png',
        'bridge': 'starter_eng/pics/tiles/bridge.png',
        'bridge_rail': 'starter_eng/pics/tiles/bridge_rail.png',
        'mountain': 'starter_eng/pics/tiles/mountain.png',
        'mountain_rail': 'starter_eng/pics/tiles/mountain_rail.png',
        'oasis': 'starter_eng/pics/tiles/oasis.png',
        'straight_rail': 'starter_eng/pics/tiles/straight_rail.png',
        'curve_rail': 'starter_eng/pics/tiles/curve_rail.png'
    };

    const imgPath = imgPaths[baseType] || 'starter_eng/pics/tiles/empty.png';

    cellDiv.innerHTML = `<img src="${imgPath}" alt="${baseType} tile" style="transform: rotate(${rotationAngle});">`;
}
function handleCellClick(event) {
    const cellDiv = event.currentTarget;
    const row = cellDiv.dataset.row;
    const col = cellDiv.dataset.col;

    if (row === undefined || col === undefined) {
        console.log("Click event not registered on a grid cell.");
        return;
    }

    let tile = gridData[row][col];
    const isLeftClick = event.button === 0; 
    const isRightClick = event.button === 2; 

    let rotationSuffix = tile.match(/_\d+$/)?.[0] || '_0';
    let currentRotation = parseInt(rotationSuffix.replace('_', ''), 10);

    if (tile.includes('oasis')) {
        console.log("Clicked on an oasis tile; no action taken.");
        return;
    } else if (tile.includes('mountain')) {
        if (isLeftClick) {
            gridData[row][col] = `mountain_rail${rotationSuffix}`;
        }
    } else if (tile.includes('bridge')) {
        if (isLeftClick) {
            gridData[row][col] = `bridge_rail${rotationSuffix}`;
        }
    } else if (tile.includes('straight_rail')) {
        if (isLeftClick) {
            gridData[row][col] = `straight_rail_${(currentRotation + 90) % 360}`;
        } else if (isRightClick) {
            gridData[row][col] = 'empty';
        }
    } else if (tile.includes('curve_rail')) {
        if (isRightClick) {
            gridData[row][col] = `curve_rail_${(currentRotation + 90) % 360}`;
        } else if (isLeftClick) {
            gridData[row][col] = 'empty';
        }
    } else if (tile.includes('empty')) {
        if (isLeftClick) {
            gridData[row][col] = 'straight_rail_0';
        } else if (isRightClick) {
            gridData[row][col] = 'curve_rail_0';
        }
    }

    updateCellDisplay(cellDiv, gridData[row][col]);
}


function renderMap(mapData) {
    const size = mapData.length;
    gameGrid.style.gridTemplateColumns = `repeat(${size}, 50px)`;
    gameGrid.innerHTML = ''; // Clear

    mapData.forEach((row, rowIndex) => {
        row.forEach((cell, colIndex) => {
            const cellDiv = document.createElement('div');
            cellDiv.classList.add('cell');
            cellDiv.dataset.row = rowIndex;
            cellDiv.dataset.col = colIndex;
            cellDiv.addEventListener('mousedown', handleCellClick); 
            updateCellDisplay(cellDiv, cell);
            gameGrid.appendChild(cellDiv);
        });
    });
}

function getRandomMap(difficulty) {
    if (difficulty === 'Easy') {
        const randomIndex = Math.floor(Math.random() * easyLevels.length);
        return easyLevels[randomIndex];
    } else if (difficulty === 'Hard') {
        const randomIndex = Math.floor(Math.random() * hardLevels.length);
        return hardLevels[randomIndex];
    }
    return null;
}

function checkPuzzleCompletion() {
    const rows = gridData.length;
    const cols = gridData[0].length;

    const isInBounds = (r, c) => r >= 0 && c >= 0 && r < rows && c < cols;

    for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
            const tile = gridData[row][col];

            if (tile.includes('rail')) {
                const connections = railConnections[tile];
                if (!connections) continue;

                for (const [dr, dc] of connections) {
                    const neighborRow = row + dr;
                    const neighborCol = col + dc;

                    if (isInBounds(neighborRow, neighborCol)) {
                        const neighborTile = gridData[neighborRow][neighborCol];
                        const oppositeDirection = [-dr, -dc];

                        const hasMatchingConnection = railConnections[neighborTile]?.some(([nr, nc]) =>
                            nr === oppositeDirection[0] && nc === oppositeDirection[1]
                        );
                        if (!hasMatchingConnection) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    return true;
}

//BONUS:

const LEADERBOARD_KEY = 'railwayLeaderboard';

function saveToLeaderboard(playerName, time, difficulty) {
    let leaderboard = JSON.parse(localStorage.getItem(LEADERBOARD_KEY)) || {};
    if (!leaderboard[difficulty]) {
        leaderboard[difficulty] = [];
    }

    leaderboard[difficulty].push({ name: playerName, time });
    leaderboard[difficulty].sort((a, b) => a.time - b.time);
    leaderboard[difficulty] = leaderboard[difficulty].slice(0, 10);

    localStorage.setItem(LEADERBOARD_KEY, JSON.stringify(leaderboard));
}

function displayLeaderboard(difficulty) {
    const leaderboard = JSON.parse(localStorage.getItem(LEADERBOARD_KEY)) || {};
    const entries = leaderboard[difficulty] || [];
    
    let leaderboardHTML = `<h2>Leaderboard - for ${difficulty} Mode</h2><ul>: `;
    entries.forEach((entry, index) => {
        leaderboardHTML += `<li>${index + 1}. ${entry.name}: ${formatTime(entry.time)}</li>`;
    });
    leaderboardHTML += `</ul>`;
    
    document.querySelector('#leaderboardContent').innerHTML = leaderboardHTML;
    document.querySelector('#leaderboardModal').classList.remove('hidden');
}

document.querySelector('#closeLeaderboardButton').addEventListener('click', () => {
    document.querySelector('#leaderboardModal').classList.add('hidden');
});

function endGame() {
    clearInterval(timer);
    const elapsed = new Date() - startTime;

    if (checkPuzzleCompletion()) {
        console.log("You've completed the puzzle :)");
        saveToLeaderboard(playerName, elapsed, selectedDifficulty);
        displayLeaderboard(selectedDifficulty);
    } else {
        console.log("Game Over...");
    }
}

function formatTime(milliseconds) {
    const seconds = Math.floor((milliseconds / 1000) % 60);
    const minutes = Math.floor((milliseconds / (1000 * 60)) % 60);
    return `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
}


const GAME_STATE_KEY = 'railwayGameState';

function saveGameState() {
    const gameState = {
        gridData,
        startTime: startTime ? startTime.getTime() : null,
        playerName,
        selectedDifficulty
    };
    localStorage.setItem(GAME_STATE_KEY, JSON.stringify(gameState));
}


function resetGameState() {
    gridData = [];
    startTime = null;
    savedStartTime = null;
    clearInterval(timer);
    timerDisplay.textContent = "00:00";
    previousDifficulty = selectedDifficulty;
}

saveGameBtn.addEventListener('click', () => {
    saveGameState();
    console.log("Game state saved");
});

loadGameBtn.addEventListener('click', () => {
    loadGameState();
    startGame();
    console.log("Game state loaded and continued.");
});

exitBtn.addEventListener('click', () => {
    exitToMainMenu();
    console.log("Exited to main menu");
});

function exitToMainMenu() {
    clearInterval(timer);
    gameScreen.style.display = 'none';
    mainMenu.style.display = 'flex';
    savedStartTime = startTime;
}

document.querySelector('#startBtn').addEventListener('click', () => {
    playerName = document.querySelector('#playerName').value;
    if (!playerName || !selectedDifficulty) {
        console.log("Please enter your name and select a difficulty to start the game.");
        return;
    }
    startGame();
});

document.querySelectorAll('.difficulty-btn').forEach(btn => {
    btn.addEventListener('click', (event) => {
        selectedDifficulty = event.target.dataset.difficulty;
        document.querySelectorAll('.difficulty-btn').forEach(b => b.classList.remove('selected'));
        event.target.classList.add('selected');
    });
});

document.querySelector('#rulesBtn').addEventListener('click', () => {
    descMenu.style.display = 'block';
});

document.querySelector('#closeRulesButton').addEventListener('click', () => {
    descMenu.style.display = 'none';
});

function checkForSavedGame() {
    if (localStorage.getItem(GAME_STATE_KEY)) {
        loadGameBtn.style.display = 'block';
    } else {
        loadGameBtn.style.display = 'none';
    }
}

function loadGameState() {
    const savedState = JSON.parse(localStorage.getItem('railwayGameState'));
    if (savedState) {
        gridData = savedState.gridData;
        startTime = new Date(savedState.startTime);
        playerName = savedState.playerName;
        selectedDifficulty = savedState.selectedDifficulty;
        renderMap(gridData);
        playerDisplay.textContent = playerName;
        if (startTime) {
            timer = setInterval(updateTimer, 1000);
        }
        console.log("Game state loaded.");
    }
}
checkForSavedGame();