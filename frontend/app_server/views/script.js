var cont = "test";
var io = " ";

module.exports = {
  setIoSocket : function(iopassed){
    io = iopassed;
    console.log("Set Io script.js");
  },
  updateContent : function(content){
    cont = content;
    console.log("From script "+ cont);
    if(io === " "){
    }else{
      console.log("message emitted!!!!");
      //io.sockets.send(cont);
      io.on('connection', socket =>{
        socket.emit('update', cont);
      });
    }
  }
}

