//callistresult.jsp - title_list.jsp
//섹션 
/*-callistresult.jsp화면에서 캘린더 썸네일 클릭했을때-title_list.jsp페이지*/
function calThumbnailClick(){
	 	let $calThumbnailObj = $('div.calIdx img');
	 		console.log("calThumbnailClick()");
		$calThumbnailObj.click(function(){
			/*let $dateValue = $('section>div.articles>div.nowdate').html();*/
			let $calIdxObj =  $(this).parents('.calIdx'); /*값을 2가지 경우로 나누기 위해 분리해줌*/
			let calIdx = $calIdxObj.attr('id'); /*1번 : calIdx값 */
			let calCategory = $calIdxObj.find('p.title_front').html(); /*2번 calCategory값*/
			
			let tableName = $(this).attr('id');
	        console.log("tableName=" + tableName + "calCategory" + calCategory);
			let ajaxUrl = "./calpostlist";	 
			    
			$.ajax({
	            url: ajaxUrl,
	            method : 'get',
				//data: {dateValue:$dateValue},
				data:{calIdx:calIdx, calCategory: calCategory},  //{dateValue:'2021/12'},
	            success:function(responseData){
					let $articlesObj = $('section>div.articles');//callistresutl.jsp의 섹션
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
			     	window.scrollTo(0, 0);
			    }
	        }); 
	        return false;
				
		});
	}


/*-callistresult화면에서 캘린더 add 클릭했을때-*/	

function caladdClick(){
	$('section>div.articles>ul>li>div.title_add>a>img').click(function(){
		let menuHref = $(this).attr('href'); 
		/*let ajaxUrl = './html/calInfowrite.html';*/  
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


/*function caladdClick(){
	let $caladdObj = $('section>div.articles>ul>li>div.title_add>a>img');
 
	$caladdObj.click(function(){
        let menuHref = $(this).attr('src'); 
        let ajaxUrl = ""; 
        
			$.ajax({
            url: ajaxUrl,
            method : 'get',
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
			
				
				/*let url = './html/calInfowrite.html';
		        let target = 'category+Thbumbnail';
		
		        let _width = '400';
				let _height = '200';
				
				let _top = Math.ceil((window.screen.height - _height)/2);
				let _left = Math.ceil((window.screen.width - _width)/2);
				
				let features = ('width='+ _width + ',height='+ _height +',left='+ _left + ',top='+ _top);
		        window.open(url, target, features);
				
	            }
        }); 
	        return false;
			
	});
}*/