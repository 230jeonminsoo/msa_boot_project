function modifyBoardSubmit($formObj){
	let $modifyBoardBt = $('fieldset.boardmodify>form>input[type=button]');
	$modifyBoardBt.click(function(){
		let formData = new FormData($formObj[0]);
		formData.forEach(function (value, key) {
			console.log(key + ":" + value);
		});
		$.ajax({
			url: './brdmodify',
            method: 'post',
			processData: false, //파일업로드용 설정
			contentType: false, //파일업로드용 설정
            data: formData, //파일업로드용 설정
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
		if (confirm("작성한 내용은 저장되지 않습니다. 취소하시겠습니까??") == true){
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
		}else{   //취소
			return false;
		}
		return false;
	});
}