/*calpostwrite.jsp*/
/*-calpostwrite화면에서 작성완료 클릭했을때-*/
function addCalPostClick(){
	let $addCalPostObj = $('form>table>tr>td>button[id=1]');
	 
		$addCalPostObj.click(function(){
	        //let menuHref = $(this).attr('id="1"'); 
	        let ajaxUrl = './calpostAdd'; 
	        
			$.ajax({
	            url: ajaxUrl,
	            method : 'post',
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
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
    }
}
// input file에 change 이벤트 부여
const inputImage = document.getElementById("input-image")
inputImage.addEventListener("change", e => {
    readImage(e.target)
})
	
	
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
	
	