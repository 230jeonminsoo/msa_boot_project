$(function(){
	//--이미지첨부파일 변경될때  미리보기 시작--
	$('fieldset.boardwrite>form>div.data>input[name=imageFile]').change(function(){
		console.log(this.files[0]);
		if(this.files[0] != undefined){
			let file = this.files[0];
			$("div.image>img.preview").attr('src',URL.createObjectURL(file));
		}else{
			$("div.image>img.preview").attr('src',"");
		}		
	});
	//--이미지첨부파일 변경될때  미리보기 끝--
});

//저장버튼 클릭되었을때
function boardSubmit($formObj){
	let $addBoardBt = $('fieldset.boardwrite>form>input[type=button]');
	$addBoardBt.click(function(){
		let formData = new FormData($formObj[0]);
		formData.forEach(function (value, key) {
			console.log(key + ":" + value);
		});
	
		$.ajax({
			url: './brdadd',
            method: 'post',
            processData: false, //파일업로드용 설정
			contentType: false, //파일업로드용 설정
            data:formData,
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
	let $modifyCancelBt = $('fieldset.boardwrite>form>button.addcancel');
	$modifyCancelBt.click(function(){
		if (confirm("작성한 내용은 저장되지 않습니다. 취소하시겠습니까??") == true){
			$.ajax({
				url: './brdlist',
				method:'get',
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