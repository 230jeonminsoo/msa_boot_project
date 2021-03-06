
/**
 * 공지사항 목록에서 글쓰기 버튼이 클릭되었을때
 */

function noticeWriteClick(){
	//body > section > div > section > div > div > div > div.notice_write_button > label
	let $noticeWriteBt = $('div.container div.notice_write_button>label');	
	console.log($noticeWriteBt);
	    $noticeWriteBt.click(function(){
		//let ajaxUrl = 'noticewritepage';
        let ajaxUrl = './html/noticewrite.html'; 
        $.ajax({
            url: ajaxUrl,
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


/**
 *  공지사항 목록에서 글 하나 클릭되었을때 
 */


function noticeDetail(){
    let $noticeObj = $('div.ntc_list>div.noticelist');

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


/**
 *  공지사항 목록에서 검색버튼 클릭했을때
 */

 function searchClick(){	
    var $searchButtonObj = $('div.search>button.searchButton'); //버튼 객체 찾음
	
	$searchButtonObj.click(function(){					
		let $searchWordObj = $('div.search>input[name=word]'); /**검색창 입력값 가져옴*/	
		let searchWord = $searchWordObj.val().trim();
		if(searchWord == ''){
            alert('검색어를 입력하세요');
            $searchWordObj.focus();
            return false;
        }

		let f = $("select[name=f]").val(); /**f는 select 옵션값 */
		let ajaxUrl = "./ntcsearch";
		$.ajax({
			url: ajaxUrl+'/'+searchWord+'/'+f,
			method: "get",    
			success:function(responseData){
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(0, 0);
					
            },
			error:function(xhr){
				alert("응답실패"+xhr.status);
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
