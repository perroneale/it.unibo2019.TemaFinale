const script = require('../public/javascripts/iosocketemitter');
const coap = require("node-coap-client").CoapClient;
const coapAddressFridge = "coap://localhost:5683";
const coapResourceFridge = "/fridgecontent";
const coapAddressButler = "coap://localhost:5684";
const serverFridge = coapAddressFridge+coapResourceFridge;
const serverButlerPOsition = coapAddressButler+"/position";
const serverButlerRoomState = coapAddressButler+"/roomstate";
const serverButlerMap = coapAddressButler+"/getmap";
var string = "";

/*exports.connectToServer = function() {
  const serverFridge = "coap://"+coapAddressFridge+coapResourceFridge;
  coap
      .tryToConnect(serverFridge)
      .then((result) =>{
        if (result) {
          console.log("Connected succesfully with fridge");
        }else{
          console.log("Not Connected with coap");
        }
      });
}*/
exports.getmap = function(){
  coap
    .request(
      serverButlerMap,
      "get"
    )
    .then(response => {
      console.log("request map response "+ response.payload);
      script.setmap(response.payload)}
    )
    .catch(err => {
      console.log("error from maprequest");}
    )
    ;
}

coap
  .observe(serverFridge, "get", function(response){
      console.log("from observe "+response.payload);
      string = response.payload;
      console.log("Emit event");
      script.updateContent(string);
    })
  .then(() =>{})
  .catch(err => {console.log("err");});

coap
   .observe(serverButlerPOsition, "get", function(response){
     console.log("from observer butler position " +response.payload)
     script.updatePos(response.payload);
   })
  .then(() =>{})
  .catch(err => {console.log("err");});

  coap
  .observe(serverButlerRoomState, "get", function(response){
    console.log("from observer butler position " +response.payload)
    script.updateState(response.payload);
  })
 .then(() =>{})
 .catch(err => {console.log("err");});

exports.coapGet = function(){
  coap
      .request(
        server, "get"
      )
      .then(response => {
        console.log("coap response get"+response.payload);
      })
      .catch( err => {
        console.log("coap get error "+ err);
      });
}

exports.getString = function(){
  return string;
}

