const scritp = require('../app_server/views/script');
const coap = require("node-coap-client").CoapClient;
const coapAddress = "localhost:5683";
const coapResource = "/fridgecontent";
const server = "coap://"+coapAddress+coapResource;
var string = ""

exports.connectToServer = function() {
  const server = "coap://"+coapAddress+coapResource;
  coap
      .tryToConnect(server)
      .then((result) =>{
        if (result) {
          console.log("Connected succesfully with coap");
        }else{
          console.log("Not Connected with coap");
        }
      });
};
coap
  .observe(
    server, "get", function(response){
      console.log("from observe "+response.payload);
      string = response.payload;
      console.log("Emit event");
      scritp.updateContent(string);
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

exports.setIoSocket = function ( iosock ) {
  io    = iosock;
 console.log("coap SETIOSOCKET io=" + io);
}

