// Date 객체 생성 
var date = new Date();

var renderCalendar = () => {    
    const viewYear = date.getFullYear();
    const viewMonth = date.getMonth();
	
	document.getElementById('currnetMonth').value= new Date().toISOString().slice(0, 7);
    document.querySelector('div.body>div.calendar>div.header>.year-month').textContent = `${viewYear}년 ${viewMonth + 1}월`;

    // 지난 달 마지막 Date, 이번 달 마지막 Date
    const prevLast = new Date(viewYear, viewMonth, 0);
    const thisLast = new Date(viewYear, viewMonth + 1, 0);

    const PLDate = prevLast.getDate();
    const PLDay = prevLast.getDay();

    const TLDate = thisLast.getDate();
    const TLDay = thisLast.getDay();

    // Dates 기본 배열들 
    const prevDates = []; 
    const thisDates = [...Array(TLDate + 1).keys()].slice(1);
    const nextDates = []; 

    // prevDates 계산
    if (PLDate !== 6) {
        for (let i=0; i<PLDay+1; i++) {
            prevDates.unshift(PLDate - i);
        }
    }

    // nextDates 계산
    for (let i=1; i<7-TLDay; i++) {
        nextDates.push(i);
    }

    // Dates 합치기 
    const dates = prevDates.concat(thisDates, nextDates);
    const firstDateIndex = dates.indexOf(1);
    const lastDateIndex = dates.lastIndexOf(TLDate);

    // Dates 정리
    dates.forEach((date,i) => {
        const condition = i >= firstDateIndex && i < lastDateIndex + 1
                          ? 'this'
                          : 'other';      
        dates[i] = `<div class="date"><span class=${condition}>${date}</span></div>`;
    })

    // Dates 그리기 
    document.querySelector('.dates').innerHTML = dates.join('');

    // 오늘 날짜 그리기
    const today = new Date();
    if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
        for (let date of document.querySelectorAll('.this')) {
        if (+date.innerText === today.getDate()) {
            date.classList.add('today');
            break;
        }
        }

    }
};

renderCalendar();

var prevMonth = () => {
    date.setDate(1); // setMonth 하기전에 setDate메서드로 날짜를 1로 설정 
    date.setMonth(date.getMonth() - 1);
    renderCalendar()
};

var nextMonth = () => {
    date.setDate(1); 
    date.setMonth(date.getMonth() + 1);
    renderCalendar();
};

var goToday = () => {
    date = new Date();
    renderCalendar();
};


//calpostlist화면에서 date 클릭했을때
function dateClick(){  
	let $dateObj = $('div.body>div.calendar>div.main>a.dates');
	console.log($dateObj);
	$dateObj.click(function(){
		let menuHref = $(this).attr('href'); 
		/*let calCategory =*/
		console.log("메뉴 href=" + menuHref); 
		/*let ajaxUrl = './html/calpostwrite.html';*/ 
		
		let calIdx = $(this).attr('id'); 
		let calCategory = $(this).attr('href');
		
		let ajaxUrl = 'calpostwrite'; //index controller에서 calpostwrite.jsp 처리
		         
		$.ajax({
            url: ajaxUrl,
            method : 'get',
			data:{calIdx:calIdx, calCategory: calCategory},
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
           }
		});
		
	 	return false;
	});
}


