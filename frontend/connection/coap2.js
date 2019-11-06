const script = require('../public/javascripts/iosocketemitter');
const coap = require("coap")
const coapAddressFridge = "coap://localhost:5683";
const coapResourceFridge = "/fridgecontent";
const coapAddressButler = "coap://localhost:5684";
const serverFridge = coapAddressFridge+coapResourceFridge;
const serverButlerPOsition = coapAddressButler+"/position";
const serverButlerRoomState = coapAddressButler+"/roomstate";
const serverButlerMap = coapAddressButler+"/getmap";
var string = "";

const fridgeContent = coap.request({
    observe:true,
    host: 'localhost',
    pathname: '/fridgecontent',
    method:'GET'
});

fridgeContent.on('response', (response) =>{
    console.log("@@@@@@response from 2 " + response.payload.toString('utf-8'));
})