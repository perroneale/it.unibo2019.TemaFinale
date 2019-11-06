var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var script = require('./public/javascripts/iosocketemitter');
var indexRouter = require('./app_server/routes/index');
const prepareRoutes = require('./app_server/routes/prepare');
const addRoutes = require('./app_server/routes/add');
const testRoutes = require('./app_server/routes/test');
const cleanRoutes = require('./app_server/routes/clean');
var mqtt = require("./connection/mqttUtils");
var coap = require("./connection/coapClient")
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'app_server', 'views'));
app.set('view engine', 'ejs');
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'app_server', 'views')));
app.use('/', indexRouter);
app.use('/prepare', prepareRoutes);
app.use('/clean', cleanRoutes);
app.use('/add', addRoutes);
app.use('/test', testRoutes);
app.get('/getcont', function(req, res){
  console.log("on app " + script.getCont());
  res.status(200).send({cont : script.getCont().toString()});
});
app.get('/getpos', function(req, res){
  console.log("on app " + JSON.stringify(script.getPos()));
  res.status(200).send({content : script.getPos()});
});
app.get('/gettablecont', function(req, res){
  console.log(script.getTable());
  res.status(200).send({content : script.getTable()});
}); 
app.get('/requestmap',function(req,res){
  console.log("map request")
  coap.getmap();
});
app.post('/stop', function(req,res,next){
  mqtt.publish("unibo/qak/execroute", "msg(stop,dispatch,maitre,execroute,stop,1)");
  res.end();
  res.redirect('localhost:3000/');
});
app.post('/reactivate', function(req,res,next){
  mqtt.publish("unibo/qak/execroute", "msg(reactivate,dispatch,maitre,execroute,reactivate,1)");
  res.end();
  res.redirect('localhost:3000/');
});
app.post('/w', function(req,res,next){
  mqtt.publish("unibo/qak/butlerresourcemodel", "msg(modelChangeAction,dispatch,maitre,butlerresourcemodel,modelChangeAction(robot, w),1)");
  
});
app.post('/a', function(req,res,next){
  mqtt.publish("unibo/qak/butlerresourcemodel", "msg(modelChangeAction,dispatch,maitre,butlerresourcemodel,modelChangeAction(robot, a),1)");
  
})
app.post('/d', function(req,res,next){
  mqtt.publish("unibo/qak/butlerresourcemodel", "msg(modelChangeAction,dispatch,maitre,butlerresourcemodel,modelChangeAction(robot, d),1)");
  
})
app.post('/h', function(req,res,next){
  mqtt.publish("unibo/qak/butlerresourcemodel", "msg(modelChangeAction,dispatch,maitre,butlerresourcemodel,modelChangeAction(robot, h),1)");
  
})
app.post('/s', function(req,res,next){
  mqtt.publish("unibo/qak/butlerresourcemodel", "msg(modelChangeAction,dispatch,maitre,butlerresourcemodel,modelChangeAction(robot, s),1)");
  
})

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
