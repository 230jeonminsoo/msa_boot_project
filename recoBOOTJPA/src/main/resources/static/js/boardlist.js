/**
 <!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 START-->
 */
function boardWriteClick(){
	let $boardWriteBt = $('div.board_write_button>label');
	
	$boardWriteBt.click(function(){
	    let ajaxUrl = 'boardwrite.jsp';
        $.ajax({
            url: ajaxUrl,
            method : 'get',
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
 <!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 END-->
  */


 /**
 <!-- 자유게시판 목록에서 글 하나 클릭되었을때 START-->  
 */    

 function boardDetail(){
    let $boardObj = $('div.brd_list>div');
    $boardObj.click(function(){
        let brdIdx = $(this).attr('id');
        let ajaxUrl = './brddetail';
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {brdIdx: brdIdx},
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
 <!-- 자유게시판 목록에서 글 하나 클릭되었을때 END-->
 */


 /**
 <!-- 자유게시판 목록에서 검색 클릭시 START-->
 */   

 function searchClick(){	
    var $searchButtonObj = $('div.search>button.searchButton'); //버튼 객체 찾음
	
	$searchButtonObj.click(function(){					
		let $searchWordObj = $('div.search>input[name=q]'); /**검색창 입력값 가져옴*/	
		let searchWord = $searchWordObj.val().trim();
		if(searchWord == ''){
            alert('검색어를 입력하세요');
            $searchWordObj.focus();
            return false;
        }

	
		let f = $("select[name=f]").val(); /**f는 select 옵션값 */
		let ajaxUrl = "./brdsearch";
		$.ajax({
			url: ajaxUrl,
			method: "get",
			data : {f:f, q:searchWord},     
			success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
            },
			error:function(xhr){
				alert("응답실패"+xhr.status);
			}
		});

		return false;        
           
 });
		    
}
 /**
 <!-- 자유게시판 목록에서 검색 클릭시 END-->
  */


/**
자유게시판 목록에서 분류 클릭시 START
*/
	function brdTypeClick(){
		var $searchAObj = $('div.dropdown-content a');  //a태그인 분류들 찾음
		$searchAObj.click(function(){
			let f = $(this).attr('id')
			let ajaxUrl = "./boardfilter";
	
			$.ajax({
			url: ajaxUrl,
			method: "get",  
			data : {f:f},     
			success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
            },
			error:function(xhr){
				alert("응답실패"+xhr.status);
			}
		});

		return false;        
           
 });
		    
}
	
/**
자유게시판 목록에서 분류 클릭시 END
*/
