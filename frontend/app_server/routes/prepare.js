const express = require('express');
const router = express.Router();
const mqtt = require('../../connection/mqttUtils');

const topic = "unibo/qak/butlerresourcemodel"

router.post('/', function(req, res, next){
  var msg = "msg(modelChangeTask,dispatch,maitre,butlerresourcemodel,modelChangeTask(robot, preparing, niente, niente),1)"
  mqtt.publish(topic, msg);
  res.render('index');
});

module.exports = router;
