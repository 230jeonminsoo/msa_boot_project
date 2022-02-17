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
					$('div.CNSend').css('display','inline-block');
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
  $('div.CNSend>button.RRN').click(function(){
    let phoneNumber = $('div.CNSend>input[name=phoneNumber]').val();
    Swal.fire('인증번호 발송 완료!');

    $.ajax({
        type: "GET",
        url: "/check/sendSMS",
        data: {
            "phoneNumber" : phoneNumber
        },
        success: function(res){
                if($.trim(res) ==$('#inputCertifiedNumber').val()){
                    Swal.fire(
                        '인증성공!',
                        '휴대폰 인증이 정상적으로 완료되었습니다.',
                        'success'
                    )
                }else{
                    Swal.fire({
                        icon: 'error',
                        title: '인증오류',
                        text: '인증번호가 올바르지 않습니다!',
                    })
                }
        }
	});
  });
}
