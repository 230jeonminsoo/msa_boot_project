function noticeModifyClick(){
	let $modifyBtObj=$('button.notice_modify');
	//console.log($removeBtObj);
	$modifyBtObj.click(function(){
		let $ntcIdx = $('#ntcIdx').html();
		console.log($ntcIdx);
		let $ntcTitle = $('#ntcTitle').html().trim();
		let $ntcContent = $('#ntcContent').html().trim();
		let $ntcAttachment = $('#ntcAttachment').html();
		
		
		$.ajax({
			url:'noticemodify.jsp',
			method:'get',
			data:{ntcIdx:$ntcIdx, ntcTitle:$ntcTitle, ntcContent: $ntcContent,ntcAttachment:$ntcAttachment},
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

function noticeRemoveClick(){
	let $removeBtObj=$('button.notice_remove');
	$removeBtObj.click(function(){
		let $ntcIdxValue = $(this).attr("id");
		let ajaxUrl = "./ntcremove";
        $.ajax({
            url: ajaxUrl,
			method: ajaxMethod,
			data: {ntcIdx:$ntcIdxValue},
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

function noticeListClick(){
	$('button.notice_list').click(function(){
		let ajaxUrl = "./ntclist";
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