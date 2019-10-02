const express = require('express');
const router = express.Router();
const mqtt = require('../../connection/mqttUtils');
const coap = require('../../connection/CoapClient')
const topic = "unibo/qak/butlerresourcemodel"

router.get('/', function(req, res, next){
    coap.coapGet();
});

module.exports = router;
