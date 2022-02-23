//callistresult.jsp - title_list.jsp
//섹션 
/*-mycallist.jsp화면에서 체크박스 클릭하고 삭제했을때 */
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


