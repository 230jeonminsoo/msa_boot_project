/*//로그인이 클릭되었을 떄
function loginClick(){
	$('header>nav>ul>li>a[id=login]').click(function(){
		
        let url = './html/login.html';
        let target = 'login';
        let features = 'top=300, left=600, width=600px, height=400px';
        window.open(url, target, features);
		return false;
    });
}*/
/*//회원가입이 클릭되었을때
function signupClick(){
	$('header>nav>ul>li>a[id=signup]').click(function(){
        let url = './html/signup.html';
        let target = 'signup';
        let features = 'top=300, left=600,width=600px, height=300px';
        window.open(url, target, features);
		return false;
    });
}*/

/*function communityClick(){
	$('header>nav>ul>li>a[id=community]').click(function(){
	 let url = './html/login.html';
        let target = 'login';
        let features = 'top=300, left=600, width=600px, height=500px';
        window.open(url, target, features);
		return false;
	});	
}*/


/**
 * 메뉴가 클릭되었을때
 */
 function menuClick(){
    let $menuObj = $('header>nav>ul>li>a');
    $menuObj.click(function(){
        let menuHref = $(this).attr('href'); //href속성값 얻기(여기서 this는 클릭된 메뉴 객체를 의미)
        console.log("메뉴 href=" + menuHref);

		
        let ajaxUrl = ""; //요청할URL
        //let ajaxMethod = ""; //요청방식
        switch(menuHref){
	
		    case 'login.html':
	            ajaxurl ='./html/login.html';
	            ajaxmethod = "get";
	            $('section>div.articles0').empty();
	            $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	            });
	            return false;
			
            case 'signup.html':
	            ajaxurl = './html/signup.html';
	            ajaxmethod = "get";	
	            $('section>div.articles0').empty();
	            $('section>div.articles0').load(ajaxurl,function(responsetext,textstatus,jqxhr){
	            });
	            return false;					
			//로그아웃
			case 'logout':
				ajaxUrl = menuHref;
                $.ajax({
                    url: ajaxUrl,
                    success:function(){
                        location.href="./";
                    },
                    error:function(xhr){
                        alert('응답실패:' + xhr.status);
                    }
                });
                return false;			
			
			//menu에서 커뮤니티가 클릭되었을때
            case 'ntclist':
                ajaxUrl = menuHref;
                $('section>div.articles').load(ajaxUrl, function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);            
                    }    
                }); 
                return false;

            //menu에서 마이페이지가 클릭되었을때
			
          }
    });
 }





