/*-calpostmodify화면에서 수정버튼 클릭했을때-*/
function modifyCalPostClick(){
	let $modifyFormObj = $('form');
	$modifyFormObj.submit(function(){
		
		alert ("캘린더글을 수정하시겠습니까?");
		alert ("캘린더글이 수정되었습니다!");
		self.close();
		
		let calIdx = $(this).find('input[name=calIdx]');
		let ajaxUrl = './calpostmodify';
		
		
		let formData = new FormData($(this)[0]);
		$.ajax({
	            url: ajaxUrl,
	            method : 'post',
				processData: false, //파일업로드용 설정
				contentType: false, //파일업로드용 설정
				//data:{calIdx:calIdx}, //formData:formData,
				data:formData,
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                //opener.parent.location.reload(); //jsp새로고침
	                $articlesObj.empty(); //선택한 요소의 하위요소를 제거합니다
	                $articlesObj.html(responseData);
		            }
	        });
	        return false;
		});
}