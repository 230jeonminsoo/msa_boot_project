
function newPwdSend(){
	$('div.findPwd>button.findPwd').click(function(){
	let email = $('div.findPwd>input[name=findPwd]').val().trim();
	let password = $('div.findPwd>input[name=findPwdSec]').val().trim();
	console.log(email);
		$.ajax({
			url: './findPwd',
			method: 'get',
			data:{email:email,password:password},
			success: function(responseObj){
				if(responseObj.status == 0){//전송실패
					//alert(responseObj.msg);
					$('div.findPwd>input[name=findPwd]').focus();
				}else{//전송성공
					alert("비밀번호가 변경되었습니다.");
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