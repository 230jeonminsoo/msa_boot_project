/*

function kakaologinClick(){
	let $kakaoLoginBt = $('button.kakaoLogin');
	$kakaoLoginBt.click(function(){
		let ajaxUrl = "https://kauth.kakao.com/oauth/authorize?client_id=2177f6a7b4ac54c3449ddca01ead7def&redirect_uri=http://localhost:9998/recoBOOTJPA/kakaologin&response_type=code";
		
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
}*/