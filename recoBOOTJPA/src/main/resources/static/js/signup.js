let nickValidate = false;
let emailValidate = false;


//닉네임 중복확인
function nickDupchk($nicknameObj, $submitBtObj){
	$nicknameObj.focusout(function(){
		
		let ajaxUrl = "./nickdupchk";
		let ajaxMethod = 'get'; 
		let nicknameValue =  $nicknameObj.val().trim();
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {nickname:nicknameValue},
			success:function(responseObj){
				if(responseObj.status == 0){
                    alert('이미 사용중인 닉네임입니다'); 
                }else{
					nickValidate = true;
				}
			},
		});
	});
	return false;
}

//이메일 중복확인
function emailDupchk($emailObj, $submitBtObj){
	$emailObj.focusout(function(){

		let ajaxUrl = "./emaildupchk";
		let ajaxMethod = 'get'; 
		let emailValue =  $emailObj.val().trim();
		var e_RegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		

		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {email:emailValue},
			success:function(responseObj){
				if(responseObj.status == 0){
                    alert('이미 사용중인 이메일입니다');				
				}else{
					emailValidate = true;
					if(nickValidate ==true && emailValidate == true){
					$submitBtObj.css('display','block');
				
				}

				}
			}
		});
	});	
	return false;
}


//가입버튼 클릭되엇을때
function signupSubmit($formObj){
	
	$formObj.submit(function(){
		//비밀번호값 유효성검사
        let $passwordObjArr = $('div.signup>form>input[type=password]');
        let $pwd = $($passwordObjArr[0]);
        let $pwd1 = $($passwordObjArr[1]);
        console.log($pwd.val());
        console.log($pwd1.val());

        if($pwd.val() != $pwd1.val()){
            alert('비밀번호가 일치하지 않습니다');
            $pwd1.focus();
            return false;
		}

		let ajaxUrl = $(this).attr('action');
        let ajaxMethod = $(this).attr('method'); 
		let sendData = $(this).serialize();
		
		$.ajax({
            url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,//{id:idValue, pwd:pwdValue, name:nameValue},
            success:function(responseObj){
                alert(responseObj.msg);
                if(responseObj.status == 1){ //가입성공                
					opener.parent.location.reload();
					window.self.close();	
                }
            },error:function(xhr){
                alert("응답실패:" + xhr.status);
            }           
        });	
		return false;
	});		
}



//회원가입의 로그인 버튼 클릭되었을때
function beforeLoginClick(){
	$('button.beforeLogin').click(function(){
		let url = 'login.html';
        let target = 'login';
        let features = 'top=300, left=600, width=600px, height=500px';
        window.open(url, target, features);
		window.close();
		return false;
	});
	
		
}
