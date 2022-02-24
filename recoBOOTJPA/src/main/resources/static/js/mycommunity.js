

/**
 *  공지사항 목록에서 글 하나 클릭되었을때 
 */
function noticeDetail(option){
	let $articlesObj = $('section>div.articles');
	if(option == 'undefined'){ //이반 사용자가 공지사항 목록에서 글 하나 클릭한 경우		
		 
	}else if(option == 'mycommunity'){ //마이페이지의 커뮤니티관리에서 내가 쓴 공지사항 목록에서 글 하나 클릭한 경우 
	  $articlesObj = $('div.detail');
		
	}
	
    let $noticeObj = $('div.noticelist>ul>li>span');

    $noticeObj.click(function(){
        let $ntcIdx = $(this).attr('id');	
        let ajaxUrl = './ntcdetail';
		/*window.open(window.location.href);*/
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {ntcIdx: $ntcIdx},
            success:function(responseData){
				console.log(responseData);
               // let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(150, 150);							
            }
        }); 
       return false;
    });	
}


/**전체체크 기능 */
function selectAll(selectAll)  {
  const checkboxes 
       = document.getElementsByName('ntcIdx');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked;
  })
}


/**체크된 공지사항 글 삭제 */
function myNoticerm(){
	let $myNoticermObj = $('button.myNoticerm');
	$myNoticermObj.click(function(){
		// 선택된 목록 가져오기
		const query = 'input[name="ntcIdx"]:checked';
		console.log(query);	  	
	  	const selectedEls = 
	      	document.querySelectorAll(query);
	  
	  	// 선택된 목록에서 value 찾기
	  	let data = '';
	  	selectedEls.forEach((el,index) => {
	    data += 'ntcIdx'+index+'='+el.value+'&';
		});
		console.log(data);
			if(data != ''){
				$.ajax({
					url: './myntcremove',
					method: 'get',
					data: data,
					success: function(responseData){
						    let $articlesObj = $('section>div.articles');
			                $articlesObj.empty();
			                $articlesObj.html(responseData);
							/*$('section>div.articles0>div.tab>ul.myinfotab>li>a[href=mycommunity]').trigger('click');*/
					},
					error: function(xhr){
						alert(xhr.status);
					}
				});
			}
			return false;			
		});	
}


//--다른페이지 클릭했을때 시작
$('div.pagegroup').on('click','span.active',function(){
//$('div.pagegroup>span.active').click(function(){	
	let url = $(this).attr("class").split(/\s+/)[0];//정규표현식, \s는 공백
	console.log(url);
	$.ajax({
		url: url,
		method: 'get',
		success: function(responseData){
			    let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
		},
		error: function(xhr){
			alert(xhr.status);
		}
	});
	return false;
});
//--다른페이지 클릭했을때 끝


//-----자유게시판 시작-------

/**
 *  마이페이지 자유게시판 목록에서 글 하나 클릭되었을때 
 */
function boardDetail(option){
	let $articlesObj = $('section>div.articles');
	if(option == 'undefined'){ //이반 사용자가 공지사항 목록에서 글 하나 클릭한 경우		
		 
	}else if(option == 'mycommunity'){ //마이페이지의 커뮤니티관리에서 내가 쓴 공지사항 목록에서 글 하나 클릭한 경우 
	  $articlesObj = $('div.brddetail');
		
	}
	
    let $boardObj = $('div.boardlist>ul>li>span');
    $boardObj.click(function(){
        //let brdIdx = $(this).attr('id');	
		let $brdIdx = $('#brdIdx').html().trim();
        let ajaxUrl = './brddetail';
		/*window.open(window.location.href);*/
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {brdIdx: $brdIdx},
            success:function(responseData){
				console.log(responseData);
               // let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(900, 900);							
            }
        }); 
       return false;
    });	
}


/**전체체크 기능 */
function checkBoxAll(checkBoxAll)  {
  const checkboxes 
       = document.getElementsByName('brdIdx');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = checkBoxAll.checked;
  })
}

/**체크된 자유게시판 글 삭제 */
function myBoardrm(){
	let $myboardrmObj = $('button.myboardrm');
	$myboardrmObj.click(function(){
		// 선택된 목록 가져오기
		const query = 'input[name="brdIdx"]:checked';
		console.log(query);	  	
	  	const selectedEls = 
	      	document.querySelectorAll(query);
	  
	  	// 선택된 목록에서 value 찾기
	  	let data = '';
	  	selectedEls.forEach((el,index) => {
	    data += 'brdIdx'+index+'='+el.value+'&';
		});
		console.log(data);
			if(data != ''){
				$.ajax({
					url: './mybrdremove',
					method: 'get',
					data: data,
					success: function(responseData){
						    let $articlesObj = $('section>div.articles');
			                $articlesObj.empty();
			                $articlesObj.html(responseData);
							/*$('section>div.articles0>div.tab>ul.myinfotab>li>a[href=mycommunity]').trigger('click');*/
							/*$('header>nav>ul>li>a[href=orderlist]').trigger('click');*/
							
					},
					error: function(xhr){
						alert(xhr.status);
					}
				});
			}
			return false;			
		});	
}


//--다른페이지 클릭했을때 시작
$('div.boardpagegroup').on('click','span.active',function(){
//$('div.pagegroup>span.active').click(function(){	
	let url = $(this).attr("class").split(/\s+/)[0];//정규표현식, \s는 공백
	console.log(url);
	$.ajax({
		url: url,
		method: 'get',
		success: function(responseData){
			    let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
		},
		error: function(xhr){
			alert(xhr.status);
		}
	});
	return false;
});
//--다른페이지 클릭했을때 끝

//------자유게시판 끝------







//------댓글 시작---------



/**댓글 클릭시 해당 댓글이 달린 게시글 페이지로 이동 */
function commentDetail(){
	let $articlesObj = $('section>div.articles');
	
    let $commentObj = $('div.cmt_list>div.commentlist>ul>li>span');
    $commentObj.click(function(){
		if (confirm("해당 게시글로 이동하시겠습니까?") == true){
        //let brdIdx = $(this).attr('id');	
		let $brdIdx = $('#brdIdxMy').html().trim();
        let ajaxUrl = './brddetail';
		/*window.open(window.location.href);*/
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {brdIdx: $brdIdx},
            success:function(responseData){
				console.log(responseData);
				
               // let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(0, 0);					
            }
        }); 
       }else{
		 return false;
	  }
    });	
}




/**전체체크 기능 */
function cmtCheckBoxAll(cmtCheckBoxAll)  {
  const checkboxes 
       = document.getElementsByName('cmtIdxMy');
  
  checkboxes.forEach((checkbox) => {
    checkbox.checked = cmtCheckBoxAll.checked;
  })
}




/**체크된 내가쓴 댓글 삭제 */
function myCommentrm(){
	let $mycommentrmObj = $('button.mycommentrm');
	$mycommentrmObj.click(function(){
	
	  
	  	// 선택된 목록에서 value 찾기
	  	//let brdIdx = '';
		//let cmtIdx = '';
		let data = '';
	    $('input[name="cmtIdxMy"]:checked').each(function(index, item){
		  	data += 'brdIdx'+index+'='+ $(item).attr("value1")+'&'; //brdidx0=9&
			data += 'cmtIdx'+index+'='+ $(item).attr("value2")+'&';//cmtidx0=1&
	    });
		
		
		console.log(data);
			//if(brdIdx != '' & cmtIdx != ''){
				$.ajax({
					url: './mycmtremove/'+uNickname,
					method: 'get',
					data: data,
					success: function(responseData){
						    let $articlesObj = $('section>div.articles');
			                $articlesObj.empty();
			                $articlesObj.html(responseData);
							/*$('section>div.articles0>div.tab>ul.myinfotab>li>a[href=mycommunity]').trigger('click');
					*/},
					error: function(xhr){
						alert(xhr.status);
					}
				});
			//}
			return false;			
		});	
}



//--다른페이지 클릭했을때 시작
$('div.commentpagegroup').on('click','span.active',function(){
//$('div.pagegroup>span.active').click(function(){	
	let url = $(this).attr("class").split(/\s+/)[0];//정규표현식, \s는 공백
	console.log(url);
	$.ajax({
		url: url,
		method: 'get',
		success: function(responseData){
			    let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
		},
		error: function(xhr){
			alert(xhr.status);
		}
	});
	return false;
});

//------댓글 끝------------
