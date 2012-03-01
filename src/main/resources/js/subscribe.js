AJS.toInit(function() {
    var baseUrl = AJS.$("meta[name='application-base-url']").attr("content");

    AJS.$(document).ready(function() {
    	AJS.$(".error").hide();
    });
    
	
	AJS.$("#projectsubscribe").submit(function() {
		AJS.$(".error").hide();  
	      var topic = AJS.$("input#topic").val();
	      var mode = AJS.$("input:radio[name='mode']:checked").val();
	      var secret = AJS.$("input#secret").val();
	      var callback = AJS.$("input#callback").val();
	      if (topic == "") {
	    	  AJS.$("span#topic").show();
	    	  AJS.$("input#topic").focus();  
	    	  return false;  
	      } 		
	      if (secret == "") {
	    	  AJS.$("span#secret").show();
	    	  AJS.$("input#secret").focus();  
		      return false;  
	      } 		
	      if ('undefined' != typeof(mode) && mode == "") {
	    	  AJS.$("span#mode").show();
	    	  AJS.$("input#mode").focus();  
	    	  return false;  
	      } 		
		
	    var dataString = 'hub.topic='+ topic + '&hub.mode=' + mode + '&hub.callback=' + callback + '&hub.secret=' + secret;  
	    //alert (dataString);return false;  
	    AJS.$.ajax({  
	      type: "POST",  
	      url: "https://api.github.com/hub",  
	      data: dataString
	    });  
	    AJS.$.ajax({  
	      type: "POST",  
	      url: "/githubcomment/projectsubscribe?project="+projectKey,  
	      data: dataString  
	    });  
		return false;
	});
});	