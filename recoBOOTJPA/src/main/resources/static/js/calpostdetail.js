/*
function calpostmodifyClick() {  
	let $calpostmodifyObj = $('td.calimage>input.modifyBt');
	console.log("calpostmodifyClick()");
	$calpostmodifyObj.click(function(){
		
		let ajaxUrl = 'calpostmodifypage';
		
		//let calIdx = $(this).attr('name'); 
		//let calCategory = $(this).attr('id');
		        
		$.ajax({
            url: ajaxUrl,
            method : 'get',
			//data:{calIdx:calIdx, calCategory: calCategory},
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
	         },error:function(xhr){
				alert("응답실패"+xhr.status);
			 }
        });
			
	});
	
}*/