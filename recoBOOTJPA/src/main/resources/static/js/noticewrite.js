

function showDate(){
	//날짜 표시하기
let $dateObj = $('fieldset>form>table>tbody>tr>td.date');
let today = new Date();
// 년도 getFullYear()
let year = today.getFullYear(); 

// 월 getMonth() (0~11로 1월이 0으로 표현되기 때문에 + 1을 해주어야 원하는 월을 구할 수 있다.)
let month = today.getMonth() + 1

// 일 getDate()
let date = today.getDate();

let todayE= year + '-' + month + '-' + date 
console.log(year + '-' + month + '-' + date);

$dateObj.innerHTML = todayE;
}

//저장버튼 클릭되었을때
function noticeSubmit($formObj){
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');		
		let ajaxMethod = $(this).attr('method');
		let sendData = $(this).serialize();		
		
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseData){
				console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);
	
			}
		});
		return false;
	});
}

//저장취소 버큰 클릭되었을때
function modifyCancelBtClick(){
	let $modifyCancelBt = $('fieldset>form>button.addcancel');
	$modifyCancelBt.click(function(){
		$.ajax({
			url: './ntclist',
			method:'get',
			success:function(responseData){
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
			} 	
		});
		return false;
	});
}

function uploadNoticeFile(){
	
}
