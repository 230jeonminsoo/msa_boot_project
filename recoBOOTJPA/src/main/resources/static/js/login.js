/** 로그인 버튼 클릭되었을 떄*/
function loginClick(){
		let $loginFormObj = $('div.login>form');
		
		$loginFormObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
        	let emailValue = $(this).find('input[name=email]').val();
        	let pwdValue = $(this).find('input[name=pwd]').val();



			$.ajax({
				url: ajaxUrl,
				method: ajaxMethod,
				data:{email:emailValue, pwd:pwdValue},
				success: function(responseObj){
					if(responseObj.status == 0){//로그인실패
						alert(responseObj.msg);
						$('div.login>form>div.login_form>input[id=pwd]').focus();
					}else{
						location.href="./";
					}
				},
				error: function(xhr){
					alert("응답실패 status:" + xhr.status);
				}
			});
			return false;
		});
}

//로그인화면에서 회원가입 버튼 클릭할때
function beforeSignupClick(){
	$('button.beforeSignup').click(function(){		
		ajaxurl ='./html/signup.html';
		ajaxmethod = "get";
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	    });
	    return false;	
	});
}

/*--비밀번호찾기 창 이동--*/
function findPwdPage(){
		$('button.findPwdPage').click(function(){		
		ajaxurl ='./html/findPwd.html';
		ajaxmethod = "get";
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	    });
	    return false;	
	});
}

function findEmailPage(){
	$('button.findEmailPage').click(function(){		
		ajaxurl ='findEmailPage';
		ajaxmethod = "get";
	    $('section>div.articles0').empty();
	    $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	    });
	    return false;	
	});
}