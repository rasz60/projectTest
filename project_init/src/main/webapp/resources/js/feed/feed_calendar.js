$(document).ready(function() {
	/* ---------------------------- Feed Calendar Page Init ---------------------------- */
	// fullcalendar load
	var Calendar = FullCalendar.Calendar;
		
	// calendar를 보여줄 domElement
	var calendarEl = document.getElementById('calendar');

	var events = [];

	// dateclick에서 쓰일 count 변수
	var count = 0;
	
	// calendar 초기 세팅
	var calendar = new Calendar(calendarEl, {

		// 국가(언어) 설정
		locale: 'ko',
		
		// 헤더 툴바 설정
		headerToolbar: {
	    	left: 'prev', // 왼쪽 (한달 전, 한달 후, 오늘로 이동 버튼)
	    	center: 'title', // 가운데 (년 월 표시)
	    	right: 'today,next' // 오른쪽 (월단위 지도, 주단위 지도, 일단위 지도)
	  	},

		// 월별 달력 생성
	  	initialView: 'dayGridMonth',
		dayMaxEventRows: true,
		dayMaxEvents: true,
		
		views: {
			dayGrid: {
				dayMaxEventRows: 3,
				dayMaxEvents: 3
			}
		},
	
		// 달력 날짜 클릭시
		dateClick: function(info) {
			// validation 문제 없을 시 클릭카운트++
			count++;

			if ( count == 1 ) {
				// 첫번째 클릭시 startDate로 입력
				$('#startDate').val(info.dateStr);
				
			} else {
				// 두번째 클릭시 endDate로 입력
				$('#endDate').val(info.dateStr);
				
				// 클릭 카운트를 0으로 초기화
				count = 0;
			}
		},

		// 생성되어있는 event를 클릭했을 때 내용 (PlanMst 수정 or 삭제 및 PlanDt 조회하는 modal창 trigger)
		eventClick: function(info) {
			// info는 해당 이벤트가 가지고 있는 데이터 값
			// event 블럭을 클릭하면 endDate를 캘린더에 표시된 날짜로 출력 (endDate.date - 1)
			var eventEndDate = strToDate(info.event.endStr);
			eventEndDate.setDate(eventEndDate.getDate() - 1);
			eventEndDate = dateToStr(eventEndDate);
			
			console.log(eventEndDate);
			
			
			// modal창을 띄우기 전에 직전에 클릭한 event의 정보를 초기화 시킴
			if( $('.modal-header #planName').val() != "" ) {
				$('.plan-details div[id^=details]:nth-child(n+3)').remove();
						
				for(var i = 1; i <= 10; i++ ) {
					$('.planDt' + i + ' .placeName').text('');
					$('.planDt' + i + ' .startTime').text('');
					$('.planDt' + i + ' .endTime').text('');
				}
				
				$('#plan-day').text('day 1');
				$('#prev-btn').attr('data-index', 0);
				$('#next-btn').attr('data-index', 2);
			}
			
			// 해당 일정의 db를 가져옴
			$.ajax({
				url: 'feed/modify_modal.do',
				type: 'post',
				// event가 가지고 있는 planNum을 data로 보냄
				data: info.event.id,
				contentType: 'application/json; charset=UTF-8',
				beforeSend: function(xhr){
		 		   	var token = $("meta[name='_csrf']").attr('content');
		 			var header = $("meta[name='_csrf_header']").attr('content');
	 		        xhr.setRequestHeader(header, token);
	 		    },
				success: function(data) {
					// modal창을 띄우고 각 input에 현재 db에 저장된 value를 뿌려줌
			    	$('#modalBtn').trigger('click');
					$('.modal-header #plan-name').text(info.event.title);
					$('#modal-planNum').val(info.event.id);
					$('#modal-planName').val(info.event.title);
					$('#modal-startDate').val(info.event.startStr);
					$('#modal-endDate').val(eventEndDate);
					$('#modal-eventColor').val(info.event.backgroundColor);
					$('#modal-originDateCount').val(info.event.extendedProps.dateCount);
					$('.modal-body .detail-days').attr('data-count', info.event.extendedProps.dateCount);
					$('#btn-detail').attr('href', '/init/plan/detail_modify?planNum=' + info.event.id);
					// modal창에 ajax로 가져온 data를 활용하여 plan_dt list를 만들고 정보를 뿌려주는 메서드
					modifyModal(data, Number(info.event.extendedProps.dateCount));
					
				},
				error : function() {
					alert('일시적인 오류로 수정에 실패하였습니다. 문제가 지속될 시 게시판으로 문의해주시면 감사하겠습니다.');
				}
			});
		}
	});

	// 캘린더를 만듦
	calendar.render();	
	// getAllPlans() 메서드 호출 : 모든 일정을 가져와서 이벤트 블럭을 생성
	getAllPlans();


	// 일정 생성(Create 버튼 눌렀을 때)
	$('#submit').click(function() {
		// 각각의 form에 입력된 값을 변수에 저장
		var planName = $('#planName').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		
		// startDate와 endDate의 차이를 구해서 dataCount 값을 input에 추가
		var start = strToDate(startDate);
		var end = strToDate(endDate);
		var dateCount = ((end - start) / (1000*60*60*24)) + 1;
		$('#dateCount').val(dateCount);
		
		// validation method 실행
 		var validation = planMstValidations(planName, startDate, endDate, dateCount);
		console.log(validation);
		// validation 결과가 참이면 최종 컨펌 후 submit
		if ( validation == true ) {
			if( confirm('선택한 일자로 일정을 만들까요?') == true ) {
				$('#frm').submit();
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	});
	
	// modal창에 상세일정 표시 부분에 previous 버튼 클릭 시
	$('#prev-btn').click(function() {
		// 버튼에 부여한 data-index 값(= .active인 탭 박스에 index - 1) 을 가져옴
		var index = $(this).attr('data-index');
		
		// index < 1  ==  첫 번째 탭 박스가 active일 경우 작동하지 않음
		if ( index < 1 ) {
			return false;
		
		// 첫 번째 날짜의 상세일정 페이지가 아닌 경우
		} else {
			
			// 현재 보여지는 탭 박스의 .active 제거
			$('#details' + (Number(index)+1)).removeClass('active');
			// 다음에 보여질 탭 박스의 .active 추가
			$('#details' + index).addClass('active');
			// planDay가 몇인지 표시 
			$('#plan-day').text('day ' + index);
			// data-index 변경 : prev = .active 탭 박스 인덱스의 -1 // next = .active 탭 박스 인덱스의 +1 
			$(this).attr('data-index', Number(index)-1);
			$('#next-btn').attr('data-index', Number(index)+1);
		}
	});

	// modal창에 상세일정 표시 부분에 next 버튼 클릭 시
	$('#next-btn').click(function() {
		// 버튼에 부여한 data-index 값(= .active인 탭 박스에 index - 1) 을 가져옴
		var index = Number($(this).attr('data-index'));
		
		// index < 2  ==  첫 번째 탭 박스가 .active, index가 총 상세 일정의 개수보다 클 경우 == 마지막 탭 박스가 .active
		if ( index < 2 || index > $(this).parent().attr('data-count')) {
			return false;
		} else {
			// 현재 보여지는 탭 박스의 .active 제거
			$('#details' + Number(index-1)).removeClass('active');
			// 다음에 보여질 탭 박스의 .active 추가
			$('#details' + index).addClass('active');
			// planDay가 몇인지 표시 
			$('#plan-day').text('day ' + index);
			// data-index 변경 : prev = .active 탭 박스 인덱스의 -1 // next = .active 탭 박스 인덱스의 +1 
			$(this).attr('data-index', Number(index)+1);
			$('#prev-btn').attr('data-index', Number(index)-1);
		}
	});

	// planMst 수정버튼 (modal창)	
	$('#btn-modify').click(function(e) {
		e.preventDefault();
		// 각각의 form에 입력된 값을 변수에 저장
		var planName = $('#modal-planName').val();
		var startDate = $('#modal-startDate').val();
		var endDate = $('#modal-endDate').val();
		
		// startDate와 endDate의 차이를 구해서 dataCount 값을 newDateCount input에 추가
		var start = strToDate(startDate);
		var end = strToDate(endDate);
		var dateCount = ((end - start) / (1000*60*60*24)) + 1;
		$('#modal-newDateCount').val(dateCount);
		
		// validation method 실행
		var validation = planMstValidations(planName, startDate, endDate, dateCount);
		
		// validation false이면 submit 안됨
		if ( validation == false ) {
			return false;
		
		// validation 결과가 true이면 원래의 일정과 날짜가 달라진 경우 확인
		} else {
			// last Validation : 수정 전 dateCount 값을 가져옴
			var originDates = $('#modal-originDateCount').val();
			
			// case 1 : 원래의 일정보다 짧아진 경우
			if ( originDates < dateCount ) {
				if( confirm('원래 일정(' + originDates + '일)보다 긴 ' + dateCount + '일를 지정했습니다. 수정할까요?') == false ) {
					return false;
				}
			}
			
			// case 2 : 원래 일정보다 길어진 경우
			else if ( originDates > dateCount ) {
				if( confirm('원래 일정(' + originDates + '일)보다 짧은 ' + dateCount + '일을 지정했습니다. 수정할까요? (새로운 일정 수를 초과하는 상세 일정은 모두 삭제됩니다.)')  == false ) {
					return false;
				}
			
			}
			
			// 5th Validation : 마지막 확인 절차
			else {
				if( confirm('선택한 일자로 일정을 만들까요?') == false ) {
					return false;
				}
			}
			
			$.ajax({
				url: 'feed/modify_plan.do',
				type: 'post',
				data: $('#modify_form').serialize(),
				success: function() {
					$('#modalCloseBtn').trigger('click');
					// 캘린더에 표시되어있는 모든 일정을 삭제
					calendar.removeAllEvents();
					// 다시 모든 일정을 불러옴
					getAllPlans();
				},
				error: function() {
					alert('일시적인 오류로 수정에 실패하였습니다. 문제가 지속될 시 게시판으로 문의해주시면 감사하겠습니다.');
				}
			});
		}

	});
	
	// modal에서 일정 삭제 버튼 클릭시
	$('#btn-delete').click(function() {
		// planNum을 가져옴
		var planNum = $('#modal-planNum').val();

		// 최종 확인 후 submit
		if( confirm('모든 상세 일정도 함께 삭제됩니다. 삭제할까요?') == false ) {
			return false;
		}
		
		$.ajax({
			url: "feed/delete_plan.do",
			type: "post",
			data: planNum,
			contentType: 'application/json; charset=UTF-8',
			beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
			success: function() {
				$('#modalCloseBtn').trigger('click');
				// 캘린더에 표시되어있는 모든 일정을 삭제
				calendar.removeAllEvents();
				// 다시 모든 일정을 불러옴
				getAllPlans();
			},
			error : function() {
				alert('일시적인 오류로 삭제에 실패하였습니다. 문제가 지속될 시 게시판으로 문의해주시면 감사하겠습니다.');
			}
		});
	});
	
	// modal창이 보여지게 설정된 이후, map을 생성하는 로직
	$('#detailModal').on('shown.bs.modal', function() {
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			mapOption = {
			    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			    level: 3 // 지도의 확대 레벨
			};
		var map = new kakao.maps.Map(mapContainer, mapOption);
	});
	
	
	
	
	/* ---------------------------- Feed Calendar Page Use Method ---------------------------- */
	
	// DB에 저장된 일정 불러와 달력에 표시하는 메서드
	function getAllPlans()  {
		$.ajax({
	 			url : "feed/getAllPlans.do",
	 			type : "post",
				// security page에서 ajax시 _csrf.token, _csrf.header를 싣어서 보내야함
	 		    beforeSend: function(xhr){
		 		   	var token = $("meta[name='_csrf']").attr('content');
		 			var header = $("meta[name='_csrf_header']").attr('content');
	 		        xhr.setRequestHeader(header, token);
	 		    },
	 			success : function(data) {
	 				// arraylist를 json으로 변환한 데이터를 하나씩 꺼내와서 calendar에 event로 추가
					for (var i = 0; i < data.length; i++ ) {
	 					var color = data[i].eventColor;
						var eventEnd = strToDate(data[i].endDate);
						eventEnd.setDate(eventEnd.getDate() + 1);
						dateToStr(eventEnd);
						// 이벤트를 생성하는 fullcalendar 메서드
						calendar.addEvent({
							id: data[i].planNum,
	 						title : data[i].planName,
	 						start : data[i].startDate,
	 						end : dateToStr(eventEnd),
							backgroundColor : color,
							dateCount: data[i].dateCount
	 					})
	 				}
					events.length = 0;
					events = calendar.getEvents();
	 			},
	 			error : function(data) {
	 				console.log(data);
	 				alert('일시적인 오류로 캘린더 초기화를 실패하였습니다. 문제가 지속될 시 게시판으로 문의해주시면 감사하겠습니다.');
	 			}
	 		})
	};
	
	// string으로 된 데이터를 date 객체로 바꿔주는 메서드
	function strToDate(str) {
		let y = str.slice(0, 4);
		let m = Number(str.slice(5, 7)) - 1;
		let d = str.slice(8);
		
		return new Date(y, m, d);
	};
	
	// Date 객체를 yyyy-mm-dd 포맷의 string으로 바꿔주는 메서드
	function dateToStr(date) {
		let y = date.getFullYear();
		let m = date.getMonth() + 1;
		let d = date.getDate();
		
		if ( m < 10 ) { m = "0" + m;	}
		
		if ( d < 10 ) { d = "0" + d; }

		return y+"-"+m+"-"+d;
	};
	
	// 이미 생성된 이벤트를 확인하여 같은 날짜에 3개 이상의 이벤트가 있는 경우 false
	function duplicateEventsValidation(startDate, dateCount) {
		var check = true;
		
		var start = strToDate(startDate);
		var duplicateEventsCount = 0;

		// startdate 부터 하루씩 증가
		for ( var i = 0; i < Number(dateCount); i++ ) {
			if ( i != 0 ) {
				start.setDate(start.getDate() + 1);
			}
			// for문을 돌면서 startDate 부터 i만큼 증가된 날짜
			var planDate = dateToStr(start);
			
			// 전체 이벤트를 가지고 있는 events 배열만큼 for문
			for ( var j = 0; j < events.length; j++ ) {
				// calendar eventEndDate가 allday속성으로 인해 +1일이 되므로 다시 -1시켜서 비교 
				var eventEnd = events[j].end;
				eventEnd.setDate(eventEnd.getDate() - 1);
				eventEnd = dateToStr(eventEnd);
				
				// 이미 생성된 이벤트 중에 planDate 날짜를 포함한 일정이 있으면 duplicateEventsCount++시킴
				if ( events[j].startStr <= planDate && eventEnd >= planDate ) {
					duplicateEventsCount++;
				}
			}
			
			// 하루라도 이벤트가 3개 이상인 날이 있으면 return 값을 false로 바꾸고 반복문 종료
			if ( duplicateEventsCount >= 3 ) {
				check = false;
				break;
			}
			// 다음 날짜 확인을 위해 duplicateEventsCount을 다시 0으로 초기화
			duplicateEventsCount = 0;
		}

		return check;
	};
	
	// planMst 생성, 수정시 공통 validation method
	function planMstValidations(planName, startDate, endDate, dateCount) {
		var validation = true;
		// 이미 생성된 이벤트를 확인하여 같은 날짜에 3개 이상의 이벤트가 있는 경우 false
		if ( duplicateEventsValidation(startDate, dateCount) == false ) {
			alert('같은 날짜에 3개 이상의 일정을 생성할 수 업습니다.');
			validation = false;
		}
		
		// 1st Validation : input null check
		if( planName == "" ) {
			alert('일정 이름을 입력해주세요.');
			validation = false;
			
		} else if( startDate =="" || endDate =="" ) {
			alert('일자를 선택해주세요.');
			validation = false;
		}
		
		 
		// 2nd Validation : 종료일자가 시작일자보다 빠르면 폼을 return = false / submit 되지 않게 함.
		if ( startDate > endDate ) {
			alert("종료일자가 시작일자보다 빠를 수 없습니다.");
			validation = false;
		} 
		
		
		// 3rd Validation : 10일 초과 일정 생성 불가
		if ( dateCount > 10 ) {
			alert("10일을 초과한 일정을 생성할 수 없습니다.");
			$('.mp_btn #reset').trigger('click');
			validation = false;
		}

		return validation;
	};
	
	
	
	// 이벤트 블럭 클릭하면 모달창에 정보 가져와서 tab menu 만드는 메서드
	function modifyModal(data, dateCount) {
		// tab 박스의 부모 element가 될 위치를 선택
		var target1 = $('.plan-details');
		
		// 기본으로 생성되어 있는 details1의 html을 가져오고 div#details로 감싸줌
		var boxHtml = '<div id="details" class="col-4 px-0">'
					+ $('#details1').html()
					+ '</div>';

		// details 박스를 동적으로 dateCount만큼 생성하고, 각 date의 상세 일정 개수만큼 상세 일정 정보 입력
		for(var i = 1; i <= dateCount; i++ ) {
			if ( i == 1 ) {
				// 하루짜리 일정인 경우, 기본으로 생성되어 있는 #details1에 active 클래스를 부여하여 화면에 보이게만 설정
				$('#details' + i).addClass('active');
			} else {
				// 2일 이상의 일정의 경우, target박스 안에 #details 박스를 생성
				target1.append(boxHtml);
				// 생성 완료한 후, id 값에 인덱스 번호를 붙여줌 (2 부터 시작)
				$('div#details').attr('id', 'details'+ i);
			}
			
			// 박스 생성과 인덱스 추가를 완료한 후, 해당 일자에 맞는 정보 입력
			var target2 = $('#details'+ i);
			// 해당 일자 안에 상세 일정 개수를 표시할 객체
			var count = 1;
			
			// 일자 안에 상세 일정 개수만큼 반복
			for ( var j = 0; j < data.length; j++ ) {
				console.log(data[j].endTime);
				if ( data[j].planDay == "day" + i ) {
					// PlaceName이 null인 경우
					if ( data[j].placeName == null ) {
						data[j].placeName = 'Place';
					}
					// startTime이 null 인 경우
					if ( data[j].startTime == null ) {
						data[j].startTime = '- - : - - ';
					}
					// startTime이 null 인 경우
					if ( data[j].endTime == null ) {
						data[j].endTime = '- - : - - ';
					}

					// 각각의 위치에 텍스트 입력
					target2.children('.planDt' + count).children('h4').text(data[j].placeName);
					target2.children('.planDt' + count).children('.startTime').text('start : ' + data[j].startTime);
					target2.children('.planDt' + count).children('.endTime').text('end : ' + data[j].endTime);
					
					// 상세 일정 개수만큼 ++
					count++;
				}
			}
		}
	};

});					