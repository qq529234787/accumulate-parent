function urlToSpringMvcGeneralQueryByData(host,data,isTo,isJsonContentType){
	var result={};
	if(isTo){
		result.url=__CONTEXT_PATH+"/http/data";
		result.data={};
		if(host){
			result.data["data[_host]"]=host;
		}
		if(isJsonContentType && data){
			result.data["data[data]"]=JSON.stringify(data);
		}else if(data){
			for(var key in data){
				result.data["data["+key+"]"]=data[key];
			}
		}
		result.dataType="json";
	}else{
		result.url=host;
		result.data=data;
		result.dataType="jsonp";
	}
	
	return result;
}


function submitPostFormByJs(action,type,data){
	var formId = "__buildSubmitPostFormByJs";
	var formHtml = "";
	formHtml += "<form id='"+formId+"' target='_self'>";
	formHtml += "";
	formHtml += "</form>";
	var formEle = $("#"+formId);
	if(formEle.length<1){
		log("create Form");
		$("body").append(formHtml);
	}
	formEle = $("#"+formId);
	var html="";
	if(data){
		for(var name in data){
			html+="<input type='hidden' id='__buildSubmitPostFormByJsInput_"+name+"' name='"+name+"'></input>";
		}
	}
	formEle.attr("action",action);
	formEle.attr("method",type);
	formEle.html(html);
	if(data){
		for(var name in data){
			$("#__buildSubmitPostFormByJsInput_"+name).val(data[name]);
		}
	}
	log(html);
	formEle.submit();
	
	
	
}