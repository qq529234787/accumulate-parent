//import required sdk
var fs=require('fs');
var path = require("path");


//help info
function showHelp(){
	console.log("arguments: [jsPublishDirectory] [ftpSyncRoot] [jsResFile]");
}
//exit with error msg and error code
function exit(code, msg){
	console.error(msg);
	showHelp();
	process.exit(code);
}

//parse arguments from command
var args = process.argv.splice(2);
if (null==args || args.length<3){
	exit(40, "illegal number of arguments, please check it");
}
var fileDirectory = args[0];
var ftpSyncRoot = args[1];
var jsResFile = args[2];

//MD5 signature length, 5 as default value for F.I.S
var MD5_LENGTH=5
//directory seperator, which depends on the platform running this script.
var sep = path.sep;

//verify input args
if (!fs.existsSync(fileDirectory)){
	exit(41, "the argument 'jsPublishDirectory' specifies a directory, but it does not exist:" + fileDirectory);
}
if (fileDirectory.endsWith(sep)){
	exit(42, "the argument 'jsPublishDirectory' cannot end with" + sep);
}


//To implements a Breadth-First searching algrithm
function parseJSResDirectory(directory, callback){
	var files = fs.readdirSync(directory);
	if (typeof(files)=="undefined" || null == files){
		exit(43, "file access error");
	}
	var items = new Array();
	for (var i=0; i<files.length; i++){
		var fullFileName = directory +sep+files[i];
		items.push(fullFileName);
	}
	/*
	there is a problem that the result order of listing items are different in different platforms.
	for example, in a directory, there are two files that the names are "skuData_64b23.js" and "sku_fa7e6.js".
	the result in macOS may be as same as above, but in Windows, it may be opposite to above.
	to fix it, it is essential to execute "sort" for getting the same result.
	*/
	items.sort();
	//parse sorted items
	if (items.length>0){
		var directories = new Array();
		for(var i=0; i<items.length; i++){
			var stat = fs.lstatSync(items[i]);
			if (stat.isDirectory()){
				directories.push(items[i]);
			}
			if (stat.isFile()){
				callback(items[i]);
			}
		}
		if (directories.length>0){
			for (var i = 0; i<directories.length; i++) {
				parseJSResDirectory(directories[i], callback);
			}
		}
	}
}

/*
Calculate length of suffix to be removed, length= $MD5_LENGTH + length("_") + length (".js")
because the structure of pubulished js file name is original_name_without_extension + "_" + MD5 + ".js"
*/
var removeJSLen = MD5_LENGTH + 4;
var prefixLength = fileDirectory.length;
var resPrefix = ftpSyncRoot.replace(new RegExp("/", 'g'), "|")

//regular expression for directory seperator
var regSep = sep;
//compatible with Windows
if (regSep=="\\"){
	regSep="\\\\";
}

function jsFileCallback(jsFileName){
	//Remove prefix in jsFileName
	jsFileName = jsFileName.substr(prefixLength, jsFileName.length);
	//Replace all directory seperators to "|"
	var varValue = jsFileName.replace(new RegExp(regSep, 'g'), "|");
	varValue = resPrefix + varValue;
    //Replace all directory seperators, "." and "-" characters in file name to "_"
	var varName = jsFileName.replace(new RegExp(regSep, 'g'), "_");
	varName = varName.replace(new RegExp("\\.", 'g'), "_");
    varName = varName.replace(new RegExp("\\-", 'g'), "_");
	//Remove MD5 sign in file name
	varName = varName.substr(0, varName.length-removeJSLen);
	//to be compatible with Windows version, lines should end with "\r\n"
	var record = "[#assign c_p_static_" + varName + "=\"" + varValue + "\"/] \r\n"
	//Output string to jsResFile, use 'append' mode
	fs.writeFileSync(jsResFile, record, {flag:'a'});
	//console.log(record);
}

//file type dispatcher, files with different types call their own function
function fileCallback(fileName){
	if (/^.+(\.js)$/.test(fileName)){
		jsFileCallback(fileName);
	}
}

//init jsResFile, use 'write' mode, to overwrite previous content
var firstLine = "[#-- --]\r\n";
fs.writeFileSync(jsResFile, firstLine, {flag:'w'});
//call parse function
parseJSResDirectory(fileDirectory, fileCallback);
