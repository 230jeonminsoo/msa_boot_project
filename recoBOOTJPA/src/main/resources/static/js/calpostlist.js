
//달력안에 add버튼 
function dateClick() {  
	let $dateObj = $('td.cp>a.add>button.cpbt');
	$dateObj.click(function(){
		console.log("글등록 버튼 클릭");
	    alert("글등록 버튼 클릭");
		let calIdx = $(this).parent().attr('id'); 
		let calCategory = $(this).parent().attr('href');
		
		let ajaxUrl = 'calpostwritepage'; 
		         
		$.ajax({	
            url: ajaxUrl,
            method : 'get',
			data:{calIdx:calIdx, calCategory: calCategory},
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
           }
		});
	 	return false;
	});
}
/*
//글작성 상세화면으로 가는 이벤트 
function functionClick() {
	let $functionObj = $('td.calimage>a.calpostWR');
	$functionObj.click(function(){	  	
     	  alert.log("글 상세보기 버튼 클릭");
		  let ajaxUrl = 'calpostdetail'; //2022-3-5  		  

		  let calDate = $(this).attr('href');
		  let calIdx = $('td.cp>a.add').attr('calIdx');//3
		  let calCategory = $('td.cp>a.add').attr('href'); 
		  
		  
		  $.ajax({	
	          url: ajaxUrl,
	          method : 'get',
		      data:{calIdx:calIdx, calDate: calDate, calCategory:calCategory}, //calCategory:calCategory
	          success:function(responseData){
	              let $articlesObj = $('section>div.articles');
	              $articlesObj.empty();
	              $articlesObj.html(responseData);
	         }
		});
		
 		return false;
    });
}*/






