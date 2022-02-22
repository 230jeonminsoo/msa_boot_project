
//카카오 최초로그인 시 닉네임 중복유저의 경우 추가 닉네임 입력 후 가입버튼 클릭시 닉네임 중복확인작업
function kakaoSignUpClick(){

	let $submitBtObj = $('div.signup>form>button[type=submit]');
	let $nicknameObj = $('div.signup>form>input[name=nickname]');

	
	let $code = $('#code').html().trim();
	console.log($code);
	
	let $email = $('#email').html().trim();
	console.log($email);
		
	let $pwd = $('#pwd').html().trim();
	console.log($pwd);
	
	
	
	$submitBtObj.click(function(){
		let ajaxUrl = "./kakaosignup";
		let ajaxMethod = 'post'; 
		let nickname =  $nicknameObj.val().trim();
		console.log(nickname);
		
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {nickname:nickname, code:$code, email:$email, pwd:$pwd},
			success:function(responseObj){
				if(responseObj.status == 0){ //중복된 닉네임. 가입실패 경고창띄우기
                    alert('이미 사용중인 닉네임입니다'); 	
                }else{
					alert('가입성공'); 	
					ajaxurl = './';
				    ajaxmethod = "get";	
				    $('section>div.articles0').empty();
				    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
								
				}); 
					
					return false;
				}
			}
		});
		return false;
	});
}