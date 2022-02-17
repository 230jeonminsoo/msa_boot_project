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
	
}
