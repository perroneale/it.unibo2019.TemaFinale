var cont={"cont" : ""};
var io;
var pos = {"x":0, "y":0};
var room = {"dishes" : [], "foodtable" : []};
var map
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
      io.emit('update', cont);
    }
  },
  getCont : function(){
    return cont;
  },
  getPos : function(){
    console.log("pos from iosocketemitter "+pos )
    return pos;
  },
  getTable : function(){
    return room;
  },
  updatePos : function(position){
    position = JSON.parse(position);
    pos = position;
    console.log("Socket.io ");
    console.log(pos)
    io.emit('updatePosition', pos);
  },
  updateState : function(roomState){
    room = JSON.parse(roomState);
    console.log("updateRoomState Iosocketemitter");
    console.log(room);
    io.emit('updateRoom', room);
  },
  completedTask : function(taskcompl){
    console.log("completed task = "+taskcompl);
    io.emit('completedTask', taskcompl);
  },
  setmap : function(mapFromCoap){
    map = mapFromCoap;
    console.log("obstacle "+ map)
    io.emit('obstacle',map)
  },
  foodNotAvailable : function(foodCode, quantity, avQuant){
    io.emit('foodnotav', {"foodcode": foodCode, "quantity": quantity, "avquant" : avQuant})
  }
}

function ab2str(buf) {
  return decoder.decode(new Uint8Array(buf));
}
