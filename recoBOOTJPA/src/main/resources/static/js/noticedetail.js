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
			url:'noticemodifypage',
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
		if (confirm("작성한 내용은 저장되지 않습니다. 취소하시겠습니까??") == true){
			let $ntcIdxValue = $(this).attr("id");
			let ajaxUrl = "./ntcremove";
	        $.ajax({
	            url: ajaxUrl,
				method: ajaxMethod,
				data: {ntcIdx:$ntcIdxValue},
	            success:function(responseData){
					 let $articlesObj = $('section>div.articles');
	               	 $articlesObj.empty();
	                 $articlesObj.html(responseData);
			         window.scrollTo(0, 0);
	            }
	        });
		}else{   //취소
			return false;
		}
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


function noticeDownloadClick(){
	$('div.ntcDetail>ul.ntcDetail>li>div.ntcAttachment').click(function(){
		let fileName =  $('#ntcAttachment').html().trim();
		let href = "./noticedownload?fileName="+fileName;
		window.location.href=href;
		return false;
	});
}