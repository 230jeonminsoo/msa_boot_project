/* 캘린더 추가 등록 버튼 클릭 되었을 때 */

  /*--캘린더 등록버튼이 클릭되었을때 START--*/
function addCalSubmit($formObj){
		//let $addCalObj = $('fieldset>form>button>input');
		$formObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
			let sendData = $(this).serialize();

			$.ajax({
				url:ajaxUrl,
	            method:ajaxMethod,
	            data:sendData,
				success:function(responseData){	
					console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);
					
					opener.parent.location.reload();
					window.self.close();
			}
		});
		return false;
    });
}