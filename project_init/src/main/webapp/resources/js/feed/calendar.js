$(document).ready(function() {
	var Calendar = FullCalendar.Calendar;
		
	var calendarEl = document.getElementById('calendar');
	// initialize the calendar
	// -----------------------------------------------------------------
	
	var calendar = new Calendar(calendarEl, {
		locale: 'ko', // 국가 설정
		
		headerToolbar: { // 헤더 툴바 설정
	    	left: 'prev', // 왼쪽 (한달 전, 한달 후, 오늘로 이동 버튼)
	    	center: 'title', // 가운데 (년 월 표시)
	    	right: 'today,next' // 오른쪽 (월단위 지도, 주단위 지도, 일단위 지도)
	  	},
	  	initialView: 'dayGridMonth',
		selectable: true, // 달력일자를 드래그 선택 가능한지 설정
		
	    select: function(info) {
	    	//customCode : date를 클릭하거나 드래그하면 input에 시작날짜와 종료날짜가 입
    		$('#startDate').val(info.startStr);
    		$('#endDate').val(info.endStr);
	    },
	    
	    eventAdd: function(obj) { // 이벤트가 추가되면 콘솔에 해당 object.toString()
	    	console.log(obj);
    	},
    	
    	eventChange: function(obj) { // 이벤트가 수정되면 콘솔에 해당 object.toString()
    		if( confirm('수정하시겠습니까?') == true ) {
    			
    		} else {
    			return;
    		}
    	},
    	
    	eventRemove: function(obj){ // 이벤트가 삭제되면 콘솔에 해당 object.toString()
    		console.log(obj);
    	}, 
		eventClick: function(info) {
	    	//customCode : 캘린더에 등록된 이벤트를 클릭하면 confirm창을 띄워 true일 경우 수정 페이지로 이동
	    	$('#modalBtn1').trigger('click');
    		$('.modal-header #plan-name').text(info.event.title);
		}
	});
	
	// DB에 저장된 일정 불러와 달력에 표시하기
	function getAllPlans()  {
		$.ajax({
	 			url : "feed/getAllPlans.do" ,
	 			type : "POST",
	 		    beforeSend: function(xhr){
		 		   	var token = $("meta[name='_csrf']").attr('content');
		 			var header = $("meta[name='_csrf_header']").attr('content');
	 		        xhr.setRequestHeader(header, token);
	 		    },
	 			success : function(data) {
	 				console.log(data);
	 				
	 				for (var i = 0; i < data.length; i++ ) {
	 					calendar.addEvent({
	 						title : data[i].planName,
	 						start : data[i].startDate,
	 						end : data[i].endDate,
	 					})
	 				}		
	 			},
	 			error : function(data) {
	 				console.log(data);
	 				alert('오류');
	 			}
	 		})
	};
	
	// 캘린더 다시 불러오기
	calendar.render();
	// getAllPlans 메서드 호출 
	getAllPlans();
	
	//customCode : 일정 이름, startDate, endDate Create 버튼 눌렀을 때
	$('#submit').click(function(e) {
		// 기본 기능인 form submit 기능 차단
		e.preventDefault();
		
		var frm = $('#frm');
		var planName = $('#planName').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		
		// 1st Validation : input null check
		if( planName == "" ) {
			alert('일정 이름을 입력해주세요.');
			return false;
		} else if( startDate =="" || endDate =="" ) {
			alert('일자를 선택해주세요.');
			return false;
		}
		
		// 2nd Validation : 선택한 일정이 맞는지 다시 한 번 체크
		if( confirm('선택한 일자로 일정을 만들까요?') == true ) {
			
			// 달력에 생성한 일정 박스 생성
			calendar1.addEvent({
				title : planName,
				start : startDate,
				end : endDate
			});
			
			// 선택한 값을 json 형태 자료로 생성
			let json = {
					planName : planName,
					startDate : startDate,
					endDate : endDate
			};
			
			// ajax로 json 객체를 controller로 보내서 db 추가
			$.ajax({
				url : 'feed/insertPlan.do',
				type : 'post',
				contentType : 'application/json; charset=UTF-8',
				data : JSON.stringify(json),
	 		    beforeSend: function(xhr){
		 		   	var token = $("meta[name='_csrf']").attr('content');
		 			var header = $("meta[name='_csrf_header']").attr('content');
	 		        xhr.setRequestHeader(header, token);
	 		    },
				success : function(data) {
					console.log(data);
				},
				error: function() {
					console.log('오류');
				}
			});
			console.log(JSON.stringify(json));
			// form에 있던 값을 리셋시킴
			frm[0].reset();
			
			// calendar를 다시 세팅
			calendar1.render();
		} else {
			return false;
		}
	})

});					