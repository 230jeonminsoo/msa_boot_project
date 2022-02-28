//캘린더 삭제버튼 클릭시 발생하는 이벤트
$('button.mycalRM').click(function(){
		console.log("캘린더 삭제 버튼 클릭");
		if(confirm("해당 캘린더를 삭제하시겠습니까?")==true){ //확인

		}else{ //취소 
			return false;
		}
		
		// 선택된 목록 가져오기
		const query = 'input[name="calIdx"]:checked';
		console.log(query);	  	
	  	const selectedEls = 
      		document.querySelectorAll(query);
		
		// 선택된 목록에서 value 찾기
		let data = '';
	  	selectedEls.forEach((el,index) => {
	    data += 'calIdx'+'='+el.value;
		});
		
		console.log(data);
		let ajaxUrl = "./calendarRemove";	 
		    
		$.ajax({
            url: ajaxUrl,
            method : 'get',
			data: data,
            success:function(responseData){
				let $articlesObj = $('section>div.articles');//callistresutl.jsp의 섹션
				$articlesObj.empty();
                $articlesObj.html(responseData);
				//history.go(-1);
				location.reload(); 
		    },error:function(xhr){
				location.href="./";
			}

        }); 
		return false;
});			
	

/*--mycallist.jsp화면에서 캘린더 썸네일 클릭했을때--*/
/*function mycalThumbnailClick(option){
	
		let $articlesObj = $('section>div.articles');
		if(option == 'undefined'){ 		
		 
		}else if(option == 'mycalendar'){  
		  $articlesObj = $('div.calendardetail');
			
		}
	
	 	let $mycalThumbnailObj = $('div.calIdx img');
	 	console.log("calThumbnailClick()");
		$mycalThumbnailObj.click(function(){
			let $calIdxObj =  $(this).parents('.calIdx'); 
			let calIdx = $calIdxObj.attr('id'); 
			let calCategory = $calIdxObj.find('p.title_front').html(); 
			
			let tableName = $(this).attr('id');
	        console.log("tableName=" + tableName + "calCategory" + calCategory);
			let ajaxUrl = "./calpostlist";	 
			    
			$.ajax({
	            url: ajaxUrl,
	            method : 'get',
				data:{calIdx:calIdx, calCategory: calCategory},
	            success:function(responseData){
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
			     	window.scrollTo(0, 0);
			    }
	        }); 
	        return false;
		});
	
}*/