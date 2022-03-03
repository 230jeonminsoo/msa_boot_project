
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

