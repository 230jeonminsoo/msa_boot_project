
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
		return false;
	});
}