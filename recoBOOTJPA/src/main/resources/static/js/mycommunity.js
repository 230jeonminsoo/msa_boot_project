/**
 *  공지사항 목록에서 글 하나 클릭되었을때 
 */
function noticeDetail(){
    let $noticeObj = $('div.noticelist');

    $noticeObj.click(function(){
        let $ntcIdx = $(this).attr('id');	
        let ajaxUrl = './ntcdetail';
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



