
const http = new XMLHttpRequest();
const url = 'http://localhost:3000/getCont';
http.open('GET', url, true);
http.send();

var cont = "test";
var pos = "test2";
var room = "test3";


function getCont(){
  return cont;
}
function getPos(){
  return pos;
}
function getRoom(){
  return test3;
}

http.onreadystatechange = function(e){
    if(http.readyState==4 && http.status==200){
    var response = JSON.parse(http.responseText)
    console.log(response.content);
    var replace = response.replace(/@/g,"<br />");
    document.getElementById('fridgecont').innerHTML = replace;
    }
}

var decoder = new TextDecoder("utf-8");

function ab2str(buf) {
    return decoder.decode(new Uint8Array(buf));
}
var socket =  io('http://localhost:3000');
socket.on('connect', function(){
    console.log("HTML CONNECTED!!");
});

socket.on('update', function(v){
    var string = ab2str(v);
    console.log("RECEIVED "+ string);
    var replace = string.replace(/@/g,"<br />");
    document.getElementById('fridgecont').innerHTML = replace;
});

socket.on('updatePosition', function(v){
    console.log("received "+v);
    x = v.x
    y = v.y
    document.getElementById(x.toString()+y.toString()).style.backgroundColor = "yellow";
});

socket.on('updateRoom', function(v){
    document.getElementById('tablecont').innerHTML = JSON.stringify(v);
});


socket.on('disconnect', function(){console.log('disconnected')});