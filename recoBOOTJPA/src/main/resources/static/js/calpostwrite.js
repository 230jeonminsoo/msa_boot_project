
/*-calpostwrite화면에서 작성완료 클릭했을때-*/
function addCalPostClick(){
	let $addCalPostObj = $('form>table>tr>td>button[id=1]');
	 
		$addCalPostObj.click(function(){
	        //let menuHref = $(this).attr('id="1"'); 
	        let ajaxUrl = "CalPostAdd"; 
	        
			$.ajax({
	            url: ajaxUrl,
	            method : 'post',
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
			            }
		        }); 
		        return false;
		});
	}
	
	
/*-calpostwrite화면에서 캘린더보기 클릭했을때-*/
function calPostViewClick(){
	let $calPostViewObj = $('form>table>tr>td>button[id=2]');
	 
		$calPostViewObj.click(function(){
	       //let menuHref = $(this).attr('id="2"'); 
	        let ajaxUrl = "calpostlistresult.jsp"; 
	        
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
	
	