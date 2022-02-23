/* calInfowrite.html */
/*--캘린더 수정버튼이 클릭되었을때 START--*/
function calInfomodifyBtClick($formObj){
	//	alert("in addCalSubmit");
	let $calInfomodifyBt = $('fieldset>form>button[type=submit]');
    $calInfomodifyBt.click(function(){
		let formData = new FormData(this);
		formData.forEach(function (value, key) {
			console.log(key + ":" + value);
		});		
		 let ajaxUrl = './calInfomodify'; 
	        
			$.ajax({
	            url: ajaxUrl,
	            method : 'post',
				processData: false, //파일업로드용 설정
				contentType: false, //파일업로드용 설정
				data:formData,
	            success:function(responseData){
					console.log(responseData);
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(response);
	            }
				,error: function (jqXHR)
	           {
	               alert(jqXHR.responseText);
	           }
        }); 
		return false;
	});
}


/* --수정전 팝업창--
function addCalSubmit($formObj){
	//	alert("in addCalSubmit");
    $formObj.submit(function(){
	//	alert("calwrite.js-1");
		let ajaxUrl = $(this).attr('action');
			//alert("calwrite.js-2");
   		let ajaxMethod = $(this).attr('method');
	//alert("calwrite.js-3");
		let formData = new FormData(this);
	//alert("calwrite.js-4");
         $.ajax({
           url:ajaxUrl
           , type : "POST"
           , processData : false
           , contentType : false
           , data : formData
           , success:function(response) {
               console.log(response);
				$(opener.document).find("section>div.articles0").html(response);
				self.close();

           }
           ,error: function (jqXHR)
           {
               alert(jqXHR.responseText);
           }
    	});
	//alert("calwrite.js-5");
		return false;
	});
}*/
