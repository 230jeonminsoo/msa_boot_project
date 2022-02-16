//callistresult.jsp -> tab.jsp

//탭메뉴에서 add 클릭했을때 
function tabaddClick(){
	let $tabadd = $('div.tab>ul>li>a[id=clickadd]');
	console.log($tabadd);
	$tabadd.click(function(){
		/*let url = './html/calInfowrite.html';*/
		/*let url = 'calInfowrite.jsp';*/
        /*let target = 'category+Thbumbnail';

		let _width = '400';
		let _height = '200';
		
		let _top = Math.ceil((window.screen.height - _height)/2);
		let _left = Math.ceil((window.screen.width - _width)/2);
		
		let features = ('width='+ _width + ',height='+ _height +',left='+ _left + ',top='+ _top);
        window.open(url, target, features);*/
	
		
        //let menuHref = $(this).attr('id="1"'); 
        let menuHref = $(this).attr('href'); 
		let ajaxUrl = './html/calInfowrite.html';  
        
		$.ajax({
            url: ajaxUrl,
            method : 'get',
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
		            }
	        }); 
		return false;
	});
}




//캘린더 생성 후 캘린더 메뉴탭 클릭했을때 
function calMenuClick(){ //callistresult.jsp
	//alert("calMenuClick()");
	let $calMenuObj = $('div.tab>ul.caltab>li>a');
	/*console.log('$calMenuObj = '); 
	console.log($calMenuObj);
	console.log('-------------');*/
	$calMenuObj.click(function(){
		let menuHref = $(this).attr('href'); 
        //console.log("메뉴 href=" + menuHref); //81번행과 중복 
		
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
                //$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
					//console.log(responseText);
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
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
			console.log(uNickname);
				$.ajax({
			        url: './myntc/'+uNickname,
			        success:function(responseData){
			            let $articlesObj = $('section>div.articles');
			            $articlesObj.empty();
			            $articlesObj.html(responseData);
						window.scrollTo(0, 0);
			        } 		
				});
				return false;
			//indexcontroller
			//tab에서 개인정보 관리를 클릭되었을때
			case 'myprivate':
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

//마이페이지 누를시 비번확인으로인해 주석처리됨.
/*function tabChange2(){
	let $myPageBtObj = $('header>nav>ul>li>a[href=mypage]');
	$myPageBtObj.click(function(){
		$('div.tab>ul.myinfotab').css('display','table');
		$('div.tab>ul.communitytab').css('display','none');
		$('div.tab>ul.caltab').css('display','none');
	});
}*/


