var express = require('express');
var router = express.Router();
var controller = require('../controllers/controller_index')
/* GET home page. */
router.get('/', controller.home);

module.exports = router;
