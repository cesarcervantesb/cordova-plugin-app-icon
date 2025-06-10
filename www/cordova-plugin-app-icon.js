var exec = require('cordova/exec');

const PLUGIN_NAME = "PluginAppIcon";

module.exports = {
    changeIcon: function(successCallback, errorCallback, args){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'changeIcon', [args]);
    },
    resetIcon: function(successCallback, errorCallback, args){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'resetIcon', [args]);
    },
    getAppName: function(successCallback, errorCallback){
        exec(successCallback, errorCallback, PLUGIN_NAME, 'getAppName', []);
    },
}