/*calpostwrite.jsp*/

function validateForm($form) {  // 필수 입력 확인
	let calMainImg = $form.find('input[name=calMainImg]');
    if (calMainImg.val() == "") {
        alert("대표 이미지를 추가하세요.");
        calMainImg.focus();
        return false;
    }
	let calMemo = $form.find('textarea[name=calMemo]');
    if (calMemo.val() == ''){
        alert("리뷰/메모를 입력하세요.");
        calMemo.focus();
        return false;
    }
	return true;
}
/*-calpostwrite화면에서 작성완료 클릭했을때-*/
function addCalPostClick(){
	let $writeFormObj = $('form');
	$writeFormObj.submit(function(){
		if(!validateForm($writeFormObj)){
			return false;
		}
		let calIdx = $(this).find('input[name=calIdx]');
		let ajaxUrl = './calpostAdd';
		alert ("캘린더 글이 저장되었습니다");
		self.close();
		
		let formData = new FormData($(this)[0]);
		$.ajax({
	            url: ajaxUrl,
	            method : 'post',
				processData: false, //파일업로드용 설정
				contentType: false, //파일업로드용 설정
				//data:{calIdx:calIdx}, //formData:formData,
				data:formData,
	            success:function(responseData){
					//opener.parent.location.reload(); //jsp새로고침
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty(); //선택한 요소의 하위요소를 제거합니다
	                $articlesObj.html(responseData);
		            }
	        });
	        return false;
		});
}



/*-calpostwrite화면에서 calmainimg 미리보기-*/
	function readImage(input) {
    // 인풋 태그에 파일이 있는 경우
    if(input.files && input.files[0]) {
        // 이미지 파일인지 검사 (생략)
        // FileReader 인스턴스 생성
        const reader = new FileReader()
        // 이미지가 로드가 된 경우
        reader.onload = e => {
            const previewImage = document.getElementById("preview-image")
            previewImage.src = e.target.result
            
        }
        // reader가 이미지 읽도록 하기
        reader.readAsDataURL(input.files[0])
        document.querySelector('.dellink').style.display = 'block'; // 이미지 삭제 링크 표시
      
    }
}
// input file에 change 이벤트 부여
const inputImage = document.getElementById("input-image")
inputImage.addEventListener("change", e => {
    readImage(e.target)
})


// calpostwrite화면에서 대표이미지삭제를 클릭했을경우
$('.dellink').click( function () {
    $('#preview-image').empty()
    $('#input-image').val("");
});

	
	
/*-calpostwrite화면에서 캘린더보기 클릭했을때-*/
function calPostViewClick(){
	let $calPostViewObj = $('form>table>tr>td>button[id=2]');
	 
		$calPostViewObj.click(function(){
	       //let menuHref = $(this).attr('id="2"'); 
	        let ajaxUrl = "./calpostlist";	
	        
			$.ajax({
	            url: ajaxUrl,
	            method : 'get',
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
		        }
	        }); 
	        return false;
				
		});
	}