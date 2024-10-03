const TABLE = document.querySelector('table')
const Q1 = document.querySelector('#q1')
const Q2 = document.querySelector('#q2')
const Q3 = document.querySelector('#q3')
const Q4 = document.querySelector('#q4')

let Header = Object.keys(characters[0])

let tr = document.createElement('tr')
Header.forEach(key => {
    let th = document.createElement('th')
    th.innerText = key
    tr.appendChild(th)
});
TABLE.appendChild(tr)

characters.forEach(char1 => {
    let tr = document.createElement('tr');
    tr.classList.add(char1.side.toLowerCase()); 
    Object.values(char1).forEach(value => {
        let td = document.createElement('td');
        td.innerText = value;
        tr.appendChild(td);
    });
    TABLE.appendChild(tr);
});
//q1
let totalAge = 0;
characters.forEach(charage => {
    totalAge+=charage.age;
});
Q1.innerText = (totalAge / characters.length);

//q2
let jediname = "";
characters.forEach(charforce => {
    if (charforce.forceSensitive === true) { 
        if (jediname !== "") {
            jediname += ", "; 
        }
        jediname += charforce.name;
    }
});
Q2.innerText = jediname;

//q3
let weaponlist = "";
let unique = new Set(); 
characters.forEach(charweapon => {
    unique.add(charweapon.weapon);
});
weaponlist = Array.from(unique).join(', ');
Q3.innerText = weaponlist;

//q4
let earliest = characters[0];
for (let early of characters) {
    if (new Date(early.joinDate) < new Date(earliest.joinDate)) {
        earliest = early;
    }
}
Q4.innerText = `${earliest.name} (${earliest.joinDate})`;


const checkAnswersButton = document.querySelector('#checkAnswersButton')
function handleAnswerButtonClick() {
    let answersList=[];
    answersList.push(Q1.innerText);
    answersList.push(Q2.innerText);
    answersList.push(Q3.innerText);
    answersList.push(Q4.innerText);
    if (answersList==answers){
        console.log("true");
    } 
    else{
        console.log("false");
    }
}
checkAnswersButton.addEventListener('click', handleAnswerButtonClick)

const answers = ['43.15384615384615','Grogu, Ahsoka Tano','Amban Phase-Pulse Blaster, None, Heavy Blaster, Darksaber, Blasters, Dual Lightsabers, Sniper Rifle, Blaster','Cad Bane (2008-10-03)']

