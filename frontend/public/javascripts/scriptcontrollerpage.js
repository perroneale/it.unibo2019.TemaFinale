window.onload = function(){
    const http = new XMLHttpRequest();
    var url = 'http://localhost:3000/getCont';
    http.open('GET', url, true);
    http.send();
    http.onload = function(){
        if(http.status==200){
        var response = JSON.parse(http.response);
        console.log(response.content);
        document.getElementById('fridgecont').innerHTML = replace;
        }
    }

    url = 'http://localhost:3000/getpos';
    http.open('GET', url, true);
    http.send();
    http.onload = function(){
        if(http.status==200){
        var response = JSON.parse(http.response);
        console.log(response.content);
        x = response.content.x;
        y = response.content.y;
        document.getElementById(x.toString()+y.toString()).style.backgroundColor = "yellow";
        }
    }

    url = 'http://localhost:3000/gettablecont';
    http.open('GET', url, true);
    http.send();
    http.onload = function(){
        if(http.status==200){
        var response = JSON.parse(http.response);
        console.log(response.content);
        document.getElementById('tablecont').innerHTML = ab2str(response.content);
        }
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
    var string = ab2str(v);
    var json = JSON.parse(string)
    console.log("received "+string);
    x = json.x
    y = json.y
    console.log(x + y);
    document.getElementById(x.toString()+y.toString()).style.backgroundColor = "yellow";
});

socket.on('updateRoom', function(v){
    var string = ab2str(v);
    console.log("received "+string);
    document.getElementById('tablecont').innerHTML = JSON.stringify(string);
});


socket.on('disconnect', function(){console.log('disconnected')});