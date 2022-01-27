//탭메뉴에서 add 클릭했을때 
function tabaddClick(){
	$('div.tab>ul>li>a[id=clickadd]').click(function(){
		let url = 'calwrite.html';
        let target = 'category+Thbumbnail';
        let features = 'top=300, left=500, width=500px, height=500px';
        window.open(url, target, features);
		return false;
	});
}




//캘린더 생성 후 캘린더 메뉴탭 클릭했을때 
function calMenuClick(){ //callistresult.jsp
	//alert("calMenuClick()");
	let $calMenuObj = $('div.tab>ul>li>a');
	/*console.log('$calMenuObj = '); 
	console.log($calMenuObj);
	console.log('-------------');*/
	$calMenuObj.click(function(){
		let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
		switch(menuHref){
 			case '#':
				break;
			default :
			//alert("in tab.js menuHref=" + menuHref);
				//tab에서 캘린더 카테고리 클릭되었을때
				//calpost 작동하면 변경해야함.
				ajaxUrl = menuHref;
				//let calIdx = $(this).attr('class');
                $('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
				return false;
			}

	});
}


function tabMenuClick(){
	let $tabMenuObj = $('div.tab>ul>li>a');
	 $tabMenuObj.click(function(){
        let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
        
		switch(menuHref){
			//tab에서 공지사항이 클릭되었을때
			case 'ntclist':
                ajaxUrl = menuHref;
                $('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
				return false;
				
			//tab에서 faq가 클릭되었을때
			case 'faqlist.html':
				ajaxUrl = menuHref;
				
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;
			
			//tab에서 자유게시판이 클릭되었을때
			case 'brdlist':
				ajaxUrl = menuHref;
               	$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;				
          }
	});
}


function tabChange(){
	let $communityBtObj = $('header>nav>ul>li>a[href=ntclist]');
	$communityBtObj.click(function(){
		$('div.tab>ul.communitytab').css('display','table');
		$('div.tab>ul.caltab').css('display','none');
	});

}
