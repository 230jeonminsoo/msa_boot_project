//callistresult.jsp -> tab.jsp
//url 바로 이동하기 : 탭메뉴에서 add 클릭했을때 
function tabaddClick(){
	let $tabadd = $('div.tab>ul>li>a[id=clickadd]');
	console.log($tabadd);
	$tabadd.click(function(){
		let menuHref = $(this).attr('href'); 
		let ajaxUrl = './html/calInfowrite.html';  
        
		$.ajax({
            url: ajaxUrl,
            method : 'get',
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
		        },
				error:function(xhr){
					alert("응답실패"+xhr.status);
					//location.href="./";
			}
	    }); 
		return false;
	});
}


//팝업창 열기 : 탭메뉴에서 add 클릭했을때 
/*function tabaddClick(){
	$('div.tab>ul>li>a[id=clickadd]').click(function(){
		let url = './html/calInfowrite.html';
        let target = 'category+Thbumbnail';
        let features = 'top=300, left=500, width=500px, height=500px';

		let _width = '500';
		let _height = '400';
		
		let _top = Math.ceil((window.screen.height - _height)/2);
		let _left = Math.ceil((window.screen.width - _width)/2);
		
		features = ('width='+ _width + ',height='+ _height +',left='+ _left + ',top='+ _top);
        window.open(url, target, features);
		return false;
	});
}*/


//캘린더 생성 후 캘린더 메뉴탭 클릭했을때 
function calMenuClick(){ //callistresult.jsp
	//alert("calMenuClick()");
	let $calMenuObj = $('div.tab>ul.caltab>li>a');
	/*console.log('$calMenuObj = '); 
	console.log($calMenuObj);
	console.log('-------------');*/
	$calMenuObj.click(function(){
		let menuHref = $(this).attr('href'); 
        //console.log("메뉴 href=" + menuHref); 
		//tabMenuClick()의 콘솔창으로도 출력이되서 생락함 
		
        let ajaxUrl = ""; 
		switch(menuHref){
 			case '#':
				break;
			default :
				/*alert("in tab.js menuHref=" + menuHref);*/
				/*alert("카테고리 = " + $(this).html());*/
				//tab에서 캘린더 카테고리 클릭되었을때
				//calpost 작동하면 변경해야함.
				ajaxUrl = menuHref;
                //$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
					//console.log(responseText);
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }else{
						alert("응답실패"+xhr.status);
						//location.href="./";
					}
                });
				return false;
			}

	});
}



function tabMenuClick(uNickname){
	let $tabMenuObj = $('div.tab>ul>li>a');
	 $tabMenuObj.click(function(){
        let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
        
		switch(menuHref){
			
			//indexcontroller
			//tab에서 공지사항이 클릭되었을때
			case 'ntclist':
                ajaxUrl = menuHref;
                //$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
					console.log(responseText);
					console.log("끝");
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
				return false;
			
			//indexcontroller	
			//tab에서 faq가 클릭되었을때
			case './html/faqlist.html':
				ajaxUrl = menuHref;
				
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;
			
			//indexcontroller
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

			//indexcontroller
			//tab에서 캘린더 관리이 클릭되었을때
			case 'mycallist':
				ajaxUrl = menuHref;
               	$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;	
			//indexcontroller	
			//tab에서 커뮤니티 글관리이 클릭되었을때
			case 'mycommunity':
				ajaxUrl = './mycommunity/'+uNickname;
               	$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
				return false;
			/*case 'mycommunity':
				console.log(uNickname);
	            let $articlesObj = $('section>div.articles');
	            $articlesObj.empty();
				$.ajax({
			        url: './test',
			        success:function(responseData){
			            $articlesObj.html(responseData);
						window.scrollTo(0, 0);
			        } 		
				});
				return false;*/
			//indexcontroller
			//tab에서 개인정보 관리를 클릭되었을때
			case 'pwdcheck':
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
		$('div.tab>ul.myinfotab').css('display','none');
	});
}


function tabChange2(){
	let $myPageBtObj = $('header>nav>ul>li>a[href=mycallist]');
	$myPageBtObj.click(function(){
		$('div.tab>ul.myinfotab').css('display','table');
		$('div.tab>ul.communitytab').css('display','none');
		$('div.tab>ul.caltab').css('display','none');
	});
}

