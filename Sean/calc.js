let app = new PIXI.Application({
  antialias: true,
  transparent: true,
  forceCanvas: false
});

let i = document.getElementById('calcInput');
let e = document.getElementById('equals');

function add (a,b) {
  return Number(a)+Number(b);
}
function subtract (a,b) {
  return Number(a)-Number(b);
}
function mult(a,b) {
  return Number(a)*Number(b);
}
function divide(a,b) {
  return Number(a)/Number(b);
}
function isNumeral(char) {
  if (char == '0' || char == '1' || char == '2' || char == '3' || char == '4' || char == '5' || char == '6' || char == '7' || char == '8' || char == '9' || char == '.') {
    return true;
  }
  return false;
}
function isOperator(char) {
  if (char == '+' || char == '-' || char == '*' || char == '/') {
    return true;
  }
  return false;
}
function calculate() {
  //input box in html file
  let box = i.value;
  //s1 is a temporary array for all number chars while s2 is for operands. Nulls are used as placeholders
  let s1 = [];
  let s2 = [];
  //the array of numbers and operands that'll eventually be run through calc functions
  let nums = [];
  let ops = [];

  for (let i = 0; i < box.length; i++) {
    if (isNumeral(box[i])) {
      s1[i] = box[i];
      s2[i] = null;
    } else if (isOperator(box[i])) {
      s1[i] = null;
      s2[i] = box[i];
    } else {
    s1[i] = null;
    s2[i] = null;
  }
}
console.log(s1);
console.log(s2);
  let nb = '';
  let nac = 0;
  console.log(s2.length);
  for (let i = 0; i < s2.length; i++) {
    if (s1[i] !== null) {
      nb = nb + s1[i];
    } else {
      nums[nac] = nb;
      ops[nac] = s2[i];
      nac++;
      nb = '';
    }
  }
  nums[nac] = nb;

  console.log(nums);
  console.log(ops);
  let val = nums[0];
  for (let i = 0; i < ops.length; i++) {
    if (ops[i] == '+') {
      val = add(val, nums[i+1]);
    } else if (ops[i] == '-') {
      val = subtract(val, nums[i+1]);
    } else if (ops[i] == '*') {
      val = mult(val, nums[i+1]);
    } else if (ops[i] == '/') {
      val = mult(val, nums[i+1]);
    }
  }
  document.getElementById('answer').innerHTML = 'The Answer is: ' + val;
}
