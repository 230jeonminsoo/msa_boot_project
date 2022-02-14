

/**
 *  공지사항 목록에서 글 하나 클릭되었을때 
 */
function noticeDetail(){
    let $noticeObj = $('div.noticelist>ul>li>span');

    $noticeObj.click(function(){
        let $ntcIdx = $(this).attr('id');	
        let ajaxUrl = './ntcdetail';
		window.open(window.location.href);
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {ntcIdx: $ntcIdx},
            success:function(responseData){
				console.log(responseData);
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(0, 0);							
            }
        }); 
       
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



