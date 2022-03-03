function cerRRN(){
	$('div.findEmail>button.RRN').click(function(){
	let $name = $('div.findEmail>input[name=myName]').val().trim();
	let $RRN = $('div.findEmail>input[name=RRN]').val().trim();	
	if($name ==''){
		console.log($name);
		alert("이름은 필수입력항목입니다!");
		return false;
	}
	if($RRN==''){
		console.log($RRN);
		alert("주민등록번호는 필수입력항목입니다!");
		return false;
	}
	console.log($name);
	console.log($RRN);
	$.ajax({
		url:'./findByNameAndRRN',
		data:{name:$name, rrn:$RRN},
		success: function(responseObj){
				if(responseObj.status == 0){//인증실패
					alert(responseObj.resultMsg);
					$('div.findEmail>input[name=myName]').focus();
				}else{
					let $articlesObj = $('div.findEmail>div.CNSend>input[name=email]');
	                console.log(responseObj.email);
					$articlesObj.val(responseObj.email);
					$('div.CNSend').css('display','inline-block');
					$('div.findEmail>input[name=myName]').prop('readonly',true);
					$('div.findEmail>input[name=RRN]').prop('readonly',true);
				}
			},
			error: function(xhr){
				alert("응답실패 status:" + xhr.status);
			}
		});
		return false;
	});
}

function CNSend(){
  $('div.CNSend>fieldset>button.RRN').click(function(){
    let phoneNumber = $('div.CNSend>fieldset>input[name=phoneNumber]').val();
    alert('인증번호 발송 완료!');
	
    $.ajax({
        type: "GET",
        url: "./check/sendSMS",
        data: {
            "phoneNumber" : phoneNumber
        },
        success: function(res){
			CNSendCheck(res);
        }
	});
  });
}

function CNSendCheck(res){
	$('div.CNSend>fieldset>button.CN').click(function(){
		console.log(res);
	    if($.trim(res) ==$('div.CNSend>input[name=CN]').val()){
	        alert('인증성공!');
			$('div.findEmail>div.CNSend>input[name=email]').css('display','inline-block');
			
	    }else{
	        alert('인증번호가 올바르지 않습니다!');
	    }
	});
}

