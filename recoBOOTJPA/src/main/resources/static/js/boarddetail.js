
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
			url:'boardmodify.jsp',
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
		let $brdIdxValue = $(this).attr("id");
		let ajaxUrl = "./brdremove";
        $.ajax({
            url: ajaxUrl,
			method: ajaxMethod,
			data: {brdIdx:$brdIdxValue},
            success:function(responseData){
				alert("게시글을 삭제하시겠습니까?");
				 let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
				 window.scrollTo(0, 0);
            }
        });
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
				alert("댓글을 삭제하시겠습니까?");
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				
            }
        });
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


