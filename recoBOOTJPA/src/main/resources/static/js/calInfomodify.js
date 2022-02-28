/* calInfowrite.html */
/*--캘린더 수정버튼이 클릭되었을때 START--*/
function calInfomodifyBtClick(){
		console.log("calInfomodifyBtClick");
	let $calInfomodifyBt = $('form');
    $calInfomodifyBt.submit(function(){

			alert("캘린더 썸네일과 이름을 수정하시겠습니까?");
			self.close();
			
			let calIdx = $(this).find('input[name=calIdx]');
			let ajaxUrl = './calInfomodify'; 
			
			let formData = new FormData($(this)[0]);
	        
			$.ajax({
	            url: ajaxUrl,
	            method : 'post',
				processData: false, //파일업로드용 설정
				contentType: false, //파일업로드용 설정
				data:formData,
	            success:function(responseData){
					alert("캘린더가 수정 되었습니다!");
		     		self.close();
					console.log(responseData);
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
	            }
				,error: function (jqXHR)
	           {
	               alert(jqXHR.responseText);
	           }
        }); 
		return false;
	});
}


 //--수정전 팝업창--
/*function calInfomodifyBtClick($formObj){
    $formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');
   		let ajaxMethod = $(this).attr('method');
		let formData = new FormData(this);

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
