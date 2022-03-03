function calpostModiPageClick() {
	let $calpostModifyObj = $('button.modifycalpost');
	console.log("calpostModifyClick()");
	$calpostModifyObj.click(function(){
		alert("calpostModifyClick()");
		let ajaxUrl = 'calpostmodifypage';	
		
	
		$.ajax({
			url : ajaxUrl,
			method : 'get',
			//processData: false, //파일업로드용 설정
			//contentType: false, //파일업로드용 설정
			//data: formdata,
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
	

