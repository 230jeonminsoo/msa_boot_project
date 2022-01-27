/* 캘린더 추가 등록 버튼 클릭 되었을 때 */

  /*--캘린더 등록버튼이 클릭되었을때 START--*/
function addCalSubmit($formObj){
		//let $addCalObj = $('fieldset>form>button>input');
		/*$formObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
			let sendData = $(this).serialize();
			console.log(sendData); //calCategory=aaaa

			$.ajax({ //파일업로드는 ajax (X)
				url:ajaxUrl,
	            method:ajaxMethod,
	            data:sendData,
				success:function(responseData){
					console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);

					opener.parent.location.reload();
					//window.self.close();
			}
		});
		return false;
    });*/
		//formData.append( "file1", $("#file1")[0].files[0] );
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
}
