/* calInfowrite.html */
/*--캘린더 등록버튼이 클릭되었을때 START--*/
function addCalSubmit(){
	console.log("in addCalSubmit");
	let $submitBtObj =  $('form');
	//alert("in addCalSubmit1");
    $submitBtObj.submit(function(){
		
		alert("캘린더를 생성하시겠습니까?");
		self.close();

		 let ajaxUrl = './caladd';
		 //let ajaxUrl = '../caladd'; //팝업창일때
		 let formData = new FormData($(this)[0]);
		 
		 $.ajax({
            url: ajaxUrl,
            method : 'post',
			processData: false, //파일업로드용 설정
			contentType: false, //파일업로드용 설정
			data:formData,
			
            success:function(responseData){
			 alert("캘린더가 생성 되었습니다!");
		     self.close();
			 let $articlesObj = $('section>div.articles');
                 $articlesObj.empty();
                 $articlesObj.html(responseData);
				 //window.Close();
            }
			,error: function (jqXHR)
           {
			   location.href="./";
               //alert(jqXHR.responseText);
			   
           }
        }); 
		
		return false;
	});
}

