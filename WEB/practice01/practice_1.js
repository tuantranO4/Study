"This is string"
'This is string'
// `Hello ${ name }!` // == 'Hello ' + name + '!'

0 // == 0.0

true
false
// falsy
null
undefined
0
''
NaN;

[];
[0, 'This is a string.'];

// {
//     x: {
//         y: [0]
//     },
//     0: 'x'
// };

// +, -, /...
// ==

0 == false // true, because 0 casts boolean
0 === false // false, because type conversion is forbidden

// if (condition) {
//     // if
// }
// else if (condition2) {
//     // 2nd if
// }
// else {
//     // otherwise
// }

// switch (n) {
//     case 0:
//         console.log('Got zero!');
//         break;

//     // case m: ... + break
//     default:
//         console.log('Something unexpected!');
// }

var x = 0; // DO NOT USE!
let y = 1; // GOOD
const z = 2; // ALSO GOOD

const a = 'a';
// a = 'b'; fails

const fruits = ['apple', 'orange', 'pineapple'];
// fruits[1] = 'plum';

for (let i = 0; i < fruits.length; ++i) {
    console.log(fruits[i]);
} //iterating

for (const fruit of fruits) {
    console.log(fruit);
}

// while (condition) {

// }

// do {

// }
// while (condition);

function f(x) {
    // y1 theoretically moved here

    if (x % 2 === 0) {
        var y1 = x + 1;
    }
    else {
        let y2 = x + 2; // let and const scopes to the nearest curly braces
    }
}

function g(h) {
    h();
}

g(function () { //anon function
    return 0; 
});
// equal (both ↑↓ are the same)
g(() => 0) //arrow function

console.log(students);

let result = []

for (const student of students) {
    result.push([student["name"],student["studentID"]])
}

/* console.log(result.filter(item => {
    if(item[1] < 5) return true
    else return false
})) */

// console.log(result.join(" </p><p> "))