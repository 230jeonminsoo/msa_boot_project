
function loadBeforeAfter(){
	let loginFont = document.getElementById("login");
	console.log(loginFont);
	if( loginFont != null){
		
		ajaxUrl = 'before.html';
	    ajaxMethod = "GET";
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxUrl,function(responseText, textStatus, jqXHR){
	 		if(jqXHR.status != 200){
				alert('응답실패:' + jqXHR.status);
	        }
		});	
		
	}else{
		ajaxUrl = './callist';
	    ajaxMethod = "GET";
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxUrl,function(responseText, textStatus, jqXHR){
	 		if(jqXHR.status != 200){
				alert('응답실패:' + jqXHR.status);
	        }
	    });
	}
}