
/**
 자유게시판 글 상세보기에서 글 수정 버튼 클릭
 */
function boardModifyClick(){
	let $modifyBtObj=$('button.board_modify'); 
	//console.log($removeBtObj);
	$modifyBtObj.click(function(){
		let $brdIdx = $('#brdIdx').html().trim();
		let $brdType = $('#brdType').html().trim();
		let $brdTitle = $('#brdTitle').html().trim();
		let $brdContent = $('#brdContent').html().trim();
		let $brdAttachment = $('#brdAttachment').html();
		
		
		$.ajax({
			url:'boardmodifypage',
			method:'get',
			data:{brdIdx:$brdIdx, brdType:$brdType, brdTitle: $brdTitle,brdContent:$brdContent,brdAttachment:$brdAttachment},
			success:function(responseData){				
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
		         window.scrollTo(0, 0);
			}
		});
          
        return false;
	});
}


function boardRemoveClick(){
	let $removeBtObj=$('button.board_remove');
	$removeBtObj.click(function(){
		if (confirm("삭제하시겠습니까??") == true){
			let $brdIdxValue = $(this).attr("id");
			let ajaxUrl = "./brdremove";
	        $.ajax({
	            url: ajaxUrl,
				method: ajaxMethod,
				data: {brdIdx:$brdIdxValue},
	            success:function(responseData){
					 let $articlesObj = $('section>div.articles');
	               	 $articlesObj.empty();
	                 $articlesObj.html(responseData);
					 window.scrollTo(0, 0);
	            }
	        });
		}else{
	        return false;
		}
		return false;
    });
}




function boardListClick(){
	$('button.board_list').click(function(){
		let ajaxUrl = "./brdlist";
        let method = "get";
		$.ajax({
            url: ajaxUrl,
            method: method,
            success:function(responseData){
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(0, 0);
            }
        });
        return false;
	});
}


function commentAddClick(){
	let $formObj = $('fieldset>form'); //form객체 찾음
	
	$formObj.submit(function(){	
		let ajaxUrl = $(this).attr('action');	
		console.log(ajaxUrl);	
		let ajaxMethod = $(this).attr('method');
		console.log(ajaxMethod);
		let sendData= $(this).serialize();
		sendData += "&brdIdx="+$('#brdIdx').html().trim()
		console.log(sendData);
				
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseData){	
				console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);
	
			}
		});
		return false;
	});
}
	


function commentModifyBtClick(){
	$('button.comment_modify').click(function(){
		let $cmtIdx = $(this).parent().parent().children('span').html();
		$('div.community_comment>div.comment_modify_input[id='+$cmtIdx+']').css('display','inline');
	});
}

function commentModifyClick(){
	$('button.comment_modify_complete').click(function(){
		let $cmtIdx = $(this).parent().parent().children('span').html();
		let $brdIdx = $('#brdIdx').html().trim();
		let $cmtContent = $('div.community_comment>div.comment_modify_input>input[id='+$cmtIdx+']').val();
		console.log($cmtIdx);
		console.log($brdIdx);
		console.log($cmtContent);
		$.ajax({
            url: './cmtmodify',
            method: 'get',
			data: {brdIdx:$brdIdx, cmtIdx:$cmtIdx, cmtContent:$cmtContent},
            success:function(responseData){
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				
            }
        });
		return false;	
		
	});
}

function commentRemoveClick(){
	$('button.comment_remove').click(function(){
			if (confirm("삭제하시겠습니까??") == true){
				let ajaxUrl = "./cmtremove";
		        let method = "get";
				let $brdIdx = $('#brdIdx').html().trim();
				let $cmtIdx = $(this).parent().parent().children('span').html();
				console.log($brdIdx);
				console.log($cmtIdx);
				$.ajax({
		            url: ajaxUrl,
		            method: method,
					data: {brdIdx:$brdIdx, cmtIdx:$cmtIdx},
		            success:function(responseData){
						let $articlesObj = $('section>div.articles');
		                $articlesObj.empty();
		                $articlesObj.html(responseData);						
		            }
		        });	
			}else{
				 return false;
		}		
	    return false;
	});	
}



function comment2AddBtClick(){
	$('button.comment_comment_add').click(function(){
		let $cmtIdx = $(this).parent().parent().children('span').html();
		$('div.community_comment>form>div.comment_comment_input[id='+$cmtIdx+']').css('display','inline');
	});
}

function comment2AddClick(){
	let $formObj = $('div.community_comment>form'); //form객체 찾음
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');	
		console.log(ajaxUrl);	
		let ajaxMethod = $(this).attr('method');
		console.log(ajaxMethod);
		let sendData= $(this).serialize();
		let $cmtIdx = $(this).parent().parent().children('span').html();
		sendData += "&brdIdx="+$('#brdIdx').html().trim()
		console.log(sendData);
				
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseData){	
				console.log(responseData);
					let $articlesObj = $('section>div.articles');
               		$articlesObj.empty();
                 	$articlesObj.html(responseData);
	
			}
		});
		return false;
	});
}



function boardDownloadClick(letter){
	$('div.brdDetail>ul.brdDetail>li>div.brdAttachment').click(function(){
		let fileName = letter;
		console.log(letter);
		let href = "./boarddownload?fileName="+fileName;
		window.location.href=href;
		return false;
	});
}



//--페이지그룹중 페이지하나 클릭 시작--
$("div.pagegroup").on("click", "span.active",function(){ //on함수쓰면 현재 존재하지 않는 객체에도 미리 이벤트 핸들러를 등록해놀 수 있다.그래서 그 객체가 만들어지고 이벤트가 생겼을때 처리 될 수 있는 것,
//$("div.pagegroup>span.active").click(function(){          //on함수 대신 click함수로 바꾼것. 하지만 !!!! 중요한점은 아직 showList()함수가 호출되기 전이므로 페이지그룹핑도 안됐으니 페이지도 존재하지 않음. 즉 객체가 없음. 그래서 on써야됌
	let url = $(this).attr("class").split(/\s+/)[0]; //정규표현식 \s는 공백 으로 자르기 했을때 가장 앞에나온 문자
	//alert(url);
	let brdIdx = $('button.board_modify').attr('id')

	//table tbody tr.row 원본객체만 제외하고 모두 삭제하기. 또는 table tbody tr.copy만 삭제하기
	//$("div.tbody>div.tr").not("div.row").remove();
	//$("div.tbody>div.tr.copy").remove();
	
	$.ajax({
		url: url, //ex) /board/list/2
		method: 'get',
		data : {brdIdx: brdIdx},
		success: function(responseData){
			  let $articlesObj = $('section>div.articles');
              $articlesObj.empty();
              $articlesObj.html(responseData);
		},
		error: function(xhr){
			alert(xhr.status);
		}
	});	
});
//--페이지그룹중 페이지하나 클릭 끝--

