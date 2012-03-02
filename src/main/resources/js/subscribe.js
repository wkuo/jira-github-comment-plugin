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
	      var username = AJS.$("input#username").val();
	      var password = AJS.$("input#password").val();
	      var error = false;
	      if ('undefined' != typeof(mode) && mode == "") {
	    	  AJS.$("span#mode").show();
	    	  error = true;
	      } 		
	      if (secret == "") {
	    	  AJS.$("span#secret").show();
	    	  AJS.$("input#secret").focus();  
	    	  error = true;
	      } 		
	      if (topic == "") {
	    	  AJS.$("span#topic").show();
	    	  AJS.$("input#topic").focus();  
	    	  error = true;
	      } 		
	      if (password == "") {
	    	  AJS.$("span#password").show();
	    	  AJS.$("input#password").focus();  
	    	  error = true;
	      } 		
	      if (username == "") {
	    	  AJS.$("span#username").show();
	    	  AJS.$("input#username").focus();
	    	  error = true;
	      } 		
	      if (error==true) {
	    	  alert("error");
	    	  return false;  
	      } 		
		
		    var dataString = 'hub.topic='+ topic + '&hub.mode=' + mode + '&hub.callback=' + callback + '&hub.secret=' + secret;  
	    AJS.$.ajax({  
	      type: "POST",  
	      url: "https://api.github.com/hub",  
	      data: dataString,
	      username: AJS.$("#username"),
	      password: AJS.$("#password")
	    });  
		return true;
	});
});	