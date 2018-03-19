
/**
 * 去除空格
 */
function Trim(str){
    return str.replace(/^\s*/,'').replace(/\s*$/,'');
}

/**
 * 判断是否为null
 * @param str
 * @returns {boolean}
 */
function isNull(str){
    str = Trim(str);
    if(str==null || str.length<1){
        return true;
    }
    return false;
}

/**
 * 校验是否为整数
 * @param str
 * @returns {boolean}
 */
function checkIsNumber(str){
    str = Trim(str);
    var ppp=/^[0-9]{1,20}$/;
    if(!ppp.exec(str)){
        return false;
    }
    return true;
}

/**
 * ^[1-9]d*$　 　 //匹配正整数
 * ^-[1-9]d*$ 　 //匹配负整数
 * ^-?[1-9]d*$　　 //匹配整数
 * ^[1-9]d*|0$　 //匹配非负整数（正整数 + 0）
 * ^-[1-9]d*|0$　　 //匹配非正整数（负整数 + 0）
 * ^[1-9]d*.d*|0.d*[1-9]d*$　　 //匹配正浮点数
 * ^-([1-9]d*.d*|0.d*[1-9]d*)$　 //匹配负浮点数
 * ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　 //匹配浮点数
 * ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
 * ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
 * 功能：	判断是否为数字
 * 参数：	strNumber：		数字字符串
 *		flag：			数字字符串类型
 * 返回：	如果通过验证则返回true,否则返回false
 */


function isNumeric(strNumber, flag)
{
    if(isNaN(strNumber))
    {
        return false;
    }

    switch(flag)
    {
        case null://数字
            return false;
        case "n":
            return /^\+?[1-9][0-9]*$/.test(strNumber);
        case "": //正整数 + 0
            return /^\d+$/.test(strNumber);
        case "0": //零和非零开头的数字：“^(0|[1-9][0-9]*)$”
            return /^(0|[1-9][0-9]*)$/.test(strNumber);
        case "1":  //有1位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"
            return /^[0-9]+(.[0-9]{1,3})?$/.test(strNumber);
        case "11":  //有1位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"
            return /^[0-9]+(.[0-9]{1})?$/.test(strNumber);
        case "2":  //有2位小数的正实数："^[0-9]+(.[0-9]{2})?$"
            return /^[0-9]+(.[0-9]{2})?$/.test(strNumber);
        case "3":  //非零的正整数："^\+?[1-9][0-9]*$"
            return /^\+?[1-9][0-9]*$/.test(strNumber);
        case "4":  //非零的负整数："^\-?[1-9][0-9]*$"
            return /^\-?[1-9][0-9]*$/.test(strNumber);
        case "+"://正数
            return /(^\+?|^\d?)\d*\.?\d+$/.test(strNumber);
        case "-"://负数
            return /^-\d*\.?\d+$/.test(strNumber);
        case "i"://整数
            return /(^-?|^\+?|\d)\d+$/.test(strNumber);
        case "+i"://正整数
            return /(^\d+$)|(^\+?\d+$)/.test(strNumber);
        case "-i"://负整数
            return /^[-]\d+$/.test(strNumber);
        case "f"://浮点数
            return /(^-?|^\+?|^\d?)\d*\.\d+$/.test(strNumber);
        case "+f"://正浮点数
            return /(^\+?|^\d?)\d*\.\d+$/.test(strNumber);
        case "-f"://负浮点数
            return /^[-]\d*\.\d$/.test(strNumber);
        default://缺省
            return true;
    }
}

/**
 * 日期比较 保证日期格式一致,yyyy-MM-dd || yyyy/MM/dd
 * @param strDate1 日期字符串1
 * @param strDate2 日期字符串2
 * @return 1:strDate1 > strDate2; -1:strDate1 < strDate2; 0:strDate1 = strDate2
 */
function compareDate(strDate1, strDate2) {
    var date1, date2;
    strDate1 = trimString(strDate1);
    strDate2 = trimString(strDate2);
    if (strDate1.indexOf("/", 0) < 0) {
        date1 = strDate1.split("-");
        date2 = strDate2.split("-");
    } else {
        date1 = strDate1.split("/");
        date2 = strDate2.split("/");
    }
    if (parseInt(date1[0]) > parseInt(date2[0])) {
        return 1;
    }
    if (parseInt(date1[0]) < parseInt(date2[0])) {
        return -1;
    }
    // 如果不进行截取0的操作,parseInt(09)会按八进制解析
    if (parseInt(getRealTimeNum(date1[1])) > parseInt(getRealTimeNum(date2[1]))) {
        return 1;
    }
    if (parseInt(getRealTimeNum(date1[1])) < parseInt(getRealTimeNum(date2[1]))) {
        return -1;
    }
    if (parseInt(getRealTimeNum(date1[2])) > parseInt(getRealTimeNum(date2[2]))) {
        return 1;
    }
    if (parseInt(getRealTimeNum(date1[2])) < parseInt(getRealTimeNum(date2[2]))) {
        return -1;
    }
    return 0;
}

/**
 * 时间比较 hh:mm:ss
 * @param strTime1
 * @param strTime2
 * @return 1:strTime1 > strTime2; -1:strTime1 < strTime2; 0:strTime1 = strTime2;
 */
function compareHhmmss(strTime1, strTime2) {
    strTime1 = trimString(strTime1);
    strTime2 = trimString(strTime2);
    var hms1 = strTime1.split(":");
    var hms2 = strTime2.split(":");
    if(parseInt(getRealTimeNum(hms1[0])) > parseInt(getRealTimeNum(hms2[0]))){
        return 1;
    }
    if(parseInt(getRealTimeNum(hms1[0])) < parseInt(getRealTimeNum(hms2[0]))){
        return -1;
    }
    if(parseInt(getRealTimeNum(hms1[1])) > parseInt(getRealTimeNum(hms2[1]))){
        return 1;
    }
    if(parseInt(getRealTimeNum(hms1[1])) < parseInt(getRealTimeNum(hms2[1]))){
        return -1;
    }
    if(parseInt(getRealTimeNum(hms1[2])) > parseInt(getRealTimeNum(hms2[2]))){
        return 1;
    }
    if(parseInt(getRealTimeNum(hms1[2])) < parseInt(getRealTimeNum(hms2[2]))){
        return -1;
    }
    return 0;
}

/**
 *功能：	日期时间比较
 *参数：	strTime1：		时间字符串1  YYYY-MM-DD hh:mm:ss
 *		strTime2：		时间字符串2  YYYY-MM-DD hh:mm:ss
 *返回：	如果strTime1>strTime2返回1,否则返回false
 */
function compareTime(strTime1, strTime2){
    strTime1 = trimString(strTime1);
    strTime2 = trimString(strTime2);
    var index = strTime1.indexOf(" ");
    var strDate= strTime1.substring(0,index);
    var endDate= strTime2.substring(0,index);
    var t = compareDate(strDate,endDate);
    if(t != 0) {
        return t;
    }

    var strHms = strTime1.substring(index);
    var endHms = strTime2.substring(index);

    return compareHhmmss(strHms, endHms);
}

function getRealTimeNum(str) {
    if(str == "" || str == "0"){
        return str;
    }

    if(str.indexOf('0') == 0) {
        str = str.substring(1);
    }
    return str;
}

/**
 *功能：	去掉字符串的前后空格
 *参数：	strVal		字符串
 *返回：	去掉前后空格的字符串
 */
function trimString (strVal)
{
    var reVal;
    var strTmp;
    strTmp = strVal + "";
    if (strTmp.length == 0)
        return (strTmp);
    reVal = /^(\s|　)*/;
    strTmp = strTmp.replace (reVal, '');
    reVal = /(\s|　)*$/;
    return (strTmp.replace (reVal, ''));
}

/**
 *功能：	日期合法性检查
 *参数：	strDate：		日期字符串
 *返回：	如果通过验证返回true,否则返回false
 */
function isDate (strDate)
{
    var nStart;
    var nEnd;
    var nYear;
    var nMonth;
    var nDay;
    var nFact;
    var arrDay = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    strDate = (trimString (strDate));
    if (strDate.length == 0)
        return (false);
    reVal = /^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/;
    if (!reVal.test (strDate))
        return (false);
    nStart = strDate.indexOf ("/", 0);
    nEnd = strDate.indexOf ("/", nStart + 1);
    nYear = eval (strDate.substring (0, nStart));
    nMonth = eval (strDate.substring (nStart + 1, nEnd));
    nDay = eval (strDate.substring (nEnd + 1, strDate.length));
    nFact = arrDay[nMonth - 1];
    if (nMonth == 2)
    {
        if ((nYear % 4 == 0 && nYear %100 != 0) || (nYear % 400 == 0))
            nFact ++;
    };
    if (nDay > nFact)
        return (false);
    return (true);
}

/**
 *功能：	时间合法性检查
 *参数：	strTime：		日期字符串
 *返回：	如果通过验证返回true,否则返回false
 */
function isTime(strTime)
{
    strTime=strTime.replace(/-/g,"/")
    if(new Date(strTime).toString()=="NaN")return false;
    else return true;
}

function isMoney( s ){
    var regu = "^[1-9][0-9]*(\\.)?([0-9]){0,2}$|^[0-9](\\.)?([0-9]){0,2}$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}

function isOneDecimal( s ){
    var regu = "^[1-9][0-9]*(\\.)?([0-9]){0,1}$|^[0-9](\\.)?([0-9]){0,1}$";
    var re = new RegExp(regu);
    if (re.test(s)) {
        return true;
    } else {
        return false;
    }
}

//=======================================================
//函数名		：	获取当前时间
//参数		：	空
//返回值		：	但前时间字符串，格式为：2007-10-31
//修改历史	：
//=======================================================
function getNowDate()
{
    var d,s,t;
    d=new Date();
    s=d.getFullYear().toString(10)+"-";
    t=d.getMonth()+1;
    s+=(t>9?"":"0")+t+"-";
    t=d.getDate();
    s+=(t>9?"":"0")+t+" ";
    //t=d.getHours();
    //s+=(t>9?"":"0")+t+":";
    //t=d.getMinutes();
    //s+=(t>9?"":"0")+t+":";
    //t=d.getSeconds();
    //s+=(t>9?"":"0")+t;
    return s;
}



function getNowTime()
{
    var d,s,t;
    d=new Date();
    s=d.getFullYear().toString(10)+"-";
    t=d.getMonth()+1;
    s+=(t>9?"":"0")+t+"-";
    t=d.getDate();
    s+=(t>9?"":"0")+t+" ";
    t=d.getHours();
    s+=(t>9?"":"0")+t+":";
    t=d.getMinutes();
    s+=(t>9?"":"0")+t+":";
    t=d.getSeconds();
    s+=(t>9?"":"0")+t;
    return s;
}

function getNowTimeMin(minutes)
{
    var d,s,t;
    d = new Date();
    minutes=parseInt(minutes);
    var interTimes=minutes*60*1000;
    interTimes=parseInt(interTimes);
    d = new Date(Date.parse(d)+interTimes);

    s=d.getFullYear().toString(10)+"-";
    t=d.getMonth()+1;
    s+=(t>9?"":"0")+t+"-";
    t=d.getDate();
    s+=(t>9?"":"0")+t+" ";
    t=d.getHours();
    s+=(t>9?"":"0")+t+":";
    t=d.getMinutes();
    s+=(t>9?"":"0")+t+":";
    t=d.getSeconds();
    s+=(t>9?"":"0")+t;
    return s;
}

/**
 * 计算字符数量，汉字算两个字符
 * @param str
 */
function countCharacters(str){
    var totalCount = 0;
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            totalCount++;
        } else {
            totalCount += 2;
        }
    }
    return totalCount;
}

/**
 * 获取特定cookie的值
 * @param name
 */
function getCookie(name) { 
	var arr,reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if(arr = document.cookie.match(reg)) {
		return unescape(arr[2]); 
	} else {
		return ""; 
	}
} 

/**
 * 设置cookie
 * @param name cookie名称
 * @param value cookie值
 * @param time 失效时间，格式如："d1"(1天)或者"h6"(6小时)或者"100s"(100秒)
 */
function setCookie(name,value,time) { 
	var strsec = getsec(time); 
	var exp = new Date(); 
	exp.setTime(exp.getTime() + strsec * 1); 
	document.cookie = name + "=" + escape (value) + ";expires=" + exp.toGMTString(); 
} 

function getsec(str){ 
    var str1 = str.substring(1,str.length) * 1;
    var str2 = str.substring(0,1);
    if (str2 == "s") { 
        return str1*1000; 
    } else if (str2 == "h") { 
        return str1*60*60*1000; 
    } else if (str2 == "d") { 
        return str1*24*60*60*1000; 
    } 
}

function isURL (str_url) {
    //alert(str_url);
    var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
        + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@
        + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184
        + '|' // 允许IP和DOMAIN（域名）
        + '([0-9a-z_!~*\'()-]+.)*' // 域名- www.
        + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名
        + '[a-z]{2,6})' // first level domain- .com or .museum
        + '(:[0-9]{1,4})?' // 端口- :80
        + '((/?)|' // a slash isn't required if there is no file name
        + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
    var re=new RegExp(strRegex);
    if (re.test(str_url)) {
        return (true);
    } else {
        return (false);
    }
}
