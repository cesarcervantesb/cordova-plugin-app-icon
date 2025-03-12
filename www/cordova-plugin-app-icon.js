var exec = require('cordova/exec');

const PLUGIN_NAME = "PluginAppIcon";

module.exports = {
    changeIcon: function(successCallback, errorCallback, args){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'change', [args]);
    },
    resetIcon: function(successCallback, errorCallback, args){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'reset', [args]);
    },
    getAppName: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'getAppName', []);
    },
}