function modifypwdBtClick(uIdx){
	//비밀번호값 유효성검사
	let $modifypwdBt = $('div.myprivate>div.modifypwd>button.modifypwdbutton');
	$modifypwdBt.click(function(){
        let $passwordObjArr = $('div.myprivate>div.modifypwd>input[type=password]');
        let $newpwd = $($passwordObjArr[0]);
        let $newpwd1 = $($passwordObjArr[1]);
        console.log($newpwd.val());
        console.log($newpwd1.val());

        if($newpwd.val() != $newpwd1.val()){
            alert('비밀번호가 일치하지 않습니다');
            $newpwd1.focus();
            return false;
		}			
		console.log("로그인한 사람 인덱스"+uIdx+"로그인한 사람 새로운패스워드"+$newpwd.val());
		
		$.ajax({
			url:"modifypwd",
	        data:{uIdx:uIdx, pwd:$newpwd.val()},
	        success:function(responseObj){
	            if(responseObj.status == 1){ //비번변경성공       
					location.href = "./";
	            }
	        },error:function(xhr){
	            alert("응답실패:" + xhr.status);
	        }           
	    });	
		return false;
	});				
}


function withdrawBtClick(uIdx){
		//비밀번호값 유효성검사
	let $modifypwdBt = $('div.myprivate>div.withdraw>button.withdrawbutton');
	$modifypwdBt.click(function(){
        let $passwordObjArr = $('div.myprivate>div.withdraw>input[type=password]');
        let $pwd = $($passwordObjArr[0]);
        let $pwd1 = $($passwordObjArr[1]);
        console.log($pwd.val());
        console.log($pwd1.val());

        if($pwd.val() != $pwd1.val()){
            alert('비밀번호가 일치하지 않습니다');
            $pwd1.focus();
            return false;
		}			
			
		$.ajax({
			url:"withdraw",
	        data:{uIdx:uIdx},
	        success:function(responseObj){
	            if(responseObj.status == 1){ //탈퇴성공      
					location.href = "./";
	            }
	        },error:function(xhr){
	            alert("응답실패:" + xhr.status);
	        }           
	    });	
		return false;
	});	
}

