function modifyBoardSubmit($formObj){
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');		
		let ajaxMethod = $(this).attr('method');
		let sendData = $(this).serialize();	
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
	let $brdIdx = $('fieldset>form>input[id=brdIdx]').val().trim();
	console.log($brdIdx);	
	$modifyCancelBt.click(function(){
		$.ajax({
			url: './brddetail',
			method:'get',
			data:{brdIdx: $brdIdx},
			success:function(responseData){
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
			}
		});
		return false;
	});
}