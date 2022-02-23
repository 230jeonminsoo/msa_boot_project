/** 확인 버튼 클릭되었을 떄*/
function checkBtClick(pwd){
	console.log(pwd);
	
	let $checkBtObj = $('button.pwdcheck');
	$checkBtObj.click(function(){
		let $pwd = $('div.pwdcheck>input[type=password]').val();
		console.log($pwd);
		if(pwd == $pwd){
			console.log($pwd);
			ajaxUrl = 'myprivate';
            $('section>div.articles').empty();
            $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
            	if(jqXHR.status != 200){
                	alert('응답실패:' + jqXHR.status);
                }
            });
			$('div.tab>ul.myinfotab').css('display','table');
			$('div.tab>ul.communitytab').css('display','none');
			$('div.tab>ul.caltab').css('display','none');
			return false;
		}else{
			alert("비밀번호가 일치하지 않습니다.");
		}
	});
}


/** 취소 버튼 클릭되었을 떄*/
function cancelBtClick(){
	let $cancelBtObj = $('button.cancel');
	$cancelBtObj.click(function(){
		location.href="./";
	});
}	

