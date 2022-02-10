
function modifyNoticeSubmit($formObj){
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');		
		let ajaxMethod = $(this).attr('method');
		let sendData = $(this).serialize().trim();	
		console.log(sendData);
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseData){
				console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);
			}
		});
		return false;
	});
}

function modifyCancelBtClick(){
	let $modifyCancelBt = $('fieldset>form>button.modifycancel');
	let $ntcIdx = $('fieldset>form>input[id=ntcIdx]').val().trim();
	console.log($ntcIdx);
	$modifyCancelBt.click(function(){
		if (confirm("작성한 내용은 저장되지 않습니다. 취소하시겠습니까??") == true){
			$.ajax({
				url: './ntcdetail',
				method:'get',
				data:{ntcIdx: $ntcIdx},
				success:function(responseData){
					let $articlesObj = $('section>div.articles');
	               	 $articlesObj.empty();
	                 $articlesObj.html(responseData);
				} 	
			});
		}else{   //취소
			return false;
		}
		return false;
	});
}