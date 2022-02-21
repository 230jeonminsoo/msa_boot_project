var nickValidate = 0;
var emailValidate = 0;

//닉네임 중복확인
function nickDupchk($nicknameObj, $submitBtObj){
	$nicknameObj.focusout(function(){		
		let ajaxUrl = "./nickdupchk";
		let ajaxMethod = 'get'; 
		let nicknameValue =  $nicknameObj.val().trim();
		console.log(nicknameValue);
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {nickname:nicknameValue},
			success:function(responseObj){
				if(responseObj.status == 0){
					nickValidate = 0;
					if(nickValidate + emailValidate == 1){
						$submitBtObj.css('display','none');
					}
                    alert('이미 사용중인 닉네임입니다'); 				
					console.log("닉넴중복시"+nickValidate+emailValidate);
                }else{
					nickValidate = 1;
					//이메일, 닉네임 중복아닐시 가입 버튼 출력
					if(nickValidate + emailValidate == 2){
						$submitBtObj.css('display','inline');
					}
					console.log(nickValidate+emailValidate);
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
		console.log(emailValue);
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {email:emailValue},
			success:function(responseObj){
				if(responseObj.status == 0){
					emailValidate = 0;
					if(nickValidate + emailValidate == 1){
						$submitBtObj.css('display','none');
					}
                    alert('이미 사용중인 이메일입니다');		
				}else{
					emailValidate = 1;
					//이메일, 닉네임 중복아닐시 가입 버튼 출력
					if(nickValidate + emailValidate == 2){
						$submitBtObj.css('display','inline');
					}
					console.log(nickValidate+emailValidate);
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
		console.log("전달될 가입내용"+sendData);
		$.ajax({
            url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,//{id:idValue, pwd:pwdValue, name:nameValue},
            success:function(responseObj){
                alert(responseObj.resultMsg);
                if(responseObj.status == 1){ //가입성공       
					ajaxurl = './html/login.html';
				    ajaxmethod = "get";	
				    $('section>div.articles0').empty();
				    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
				    });
				    return false;
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
		ajaxurl = './html/login.html';
	    ajaxmethod = "get";	
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	    });
	    return false;
	});		
}











