var first = true
var socketFirst = true
var firstTable = true
var firstMap = true
var firstDishes = true
var buttonState = {
    "prepareButton" : {
        "name" : "prepareButton",
        "disabled" : false
    },
    "addButton" : {
        "name" : "addButton",
        "disabled" : true
    },
    "cleanButton" : {
        "name" : "cleanButton",
        "disabled" : true
    }
}

function updateButton(prepareState, addState, cleanState){
    buttonState.prepareButton.disabled = prepareState
    buttonState.addButton.disabled = addState
    buttonState.cleanButton.disabled = cleanState
}
function getButtonState(){
    return buttonState
}
function getFirst(){
    return first
}

function getSocketFirst(){
    return socketFirst
}

function setFirst(newFirst){
    first = newFirst
}

function setSocketFirst(newFirst){
    socketFirst = newFirst
}
function getFirstTable(){
    return firstTable
}
function setFirstTable(firsttable){
    firstTable = firsttable
}
function getFirstMap(){
    return firstMap
}
function setFirstMap(firstmap){
    firstMap = firstmap
}
function getFirstDishes(){
    return firstDishes
}
function setFirstDishes(firstdishes){
    firstDishes = firstdishes
}
