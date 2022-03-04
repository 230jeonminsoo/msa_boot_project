function calpostModiPageClick() {
	let $calpostModifyObj = $('button.modifycalpost');
	
	$calpostModifyObj.click(function(){
		//alert("calpostModifyClick()");
		console.log("calpostModifyClick()");
		let ajaxUrl = 'calpostmodifypage';	
		
		let calDate = $('div.thumbnail>div.calDate').attr('id');
		let calIdx = $('div.thumbnail>div.calIdx').attr('id');
		let calMainImg = $('div.thumbnail>div.calMainImg').attr('id');
		let calMemo = $('div.calMemo').attr('id');
		
	
		$.ajax({
			url : ajaxUrl,
			method : 'get',
			//processData: false, //파일업로드용 설정
			//contentType: false, //파일업로드용 설정
			data: {calDate:calDate, calIdx:calIdx, calMainImg:calMainImg, calMemo:calMemo },
			success: function(responseData){
				 let $articlesObj = $('section>div.articles');
	              $articlesObj.empty();
	              $articlesObj.html(responseData);
			},error:function(jqXHR){
				//location.href="calpostlistresult.jsp";
			}
			
		});	
			
		return false;	
		});
}
	
//캘린더 글 삭제하는 이벤트 
$('button.removecalpost').click(function(){
	console.log("캘린더 삭제 버튼 클릭");
		if(confirm("해당 캘린더 글을 삭제하시겠습니까?")==true){ //확인

		}else{ //취소 
			return false;
		}
		
		let calDate = $('div.thumbnail>div.calDate').attr('id');
		let calIdx = $('div.thumbnail>div.calIdx').attr('id');
		
		let ajaxUrl = "./calpostremove";	 
		    
		$.ajax({
            url: ajaxUrl,
            method : 'get',
			data: {calIdx:calIdx, calDate:calDate },
            success:function(responseData){
				let $articlesObj = $('section>div.articles');//callistresutl.jsp의 섹션
				$articlesObj.empty();
                $articlesObj.html(responseData);
		    },error:function(xhr){
				location.href="./";
			}

        }); 
		return false;
})



