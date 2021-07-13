var exec = require('cordova/exec');

exports.start = function (arg0, success, error) {
    exec(success, error, 'Scanovatec', 'start', [arg0]);
};

exports.evaluateTransaction = function (arg0, success, error){
    exec(success, error, 'Scanovatec', 'evaluateTransaction', [arg0]);
};