$(document).ready(function() {
	
	// 동적으로 생성된 tabdiv의 설정 값을 바꿔 줌
	for(var i = 1; i <= dateCount; i++ ) {
		var placeCount = $('#frm'+i+'>.detail0>.inputbox>input[name=placeCount]').val();
		var planDate = $('#frm'+i+'>.detail0>.inputbox>input[name=planDate]').val();
		
		//i번째 form에 data-count, data-date 값 설정
		$('#frm' + i).attr('data-count', placeCount);
		$('#frm' + i).attr('data-date', planDate);
		
		// client에게 보여지는 text에 각 일정 개수와, planDate 입력
		$('#frm' + i).parent().siblings('p.mt-2').children('.showIndex').text(placeCount);
		$('#frm' + i).parent().siblings('#date-title').text('DAY ' + i + ' : ' + planDate);
		
		// 세부 일정이 0일 때
		if ( placeCount == '0' ) {
			// 기존에 생성되어 있는 detail0 박스의 인덱스만 수정
			$('#frm' + i + '>div:nth-child(1)').addClass('detail1');
			$('#frm' + i + '>div:nth-child(1)').removeClass('detail0');
			
		// 세부 일정이 0이 아닐 때
		} else {
			// placeCount 만큼 반복하면서 인덱스를 바꿔 줌
			for ( var j = 1; j <= placeCount; j++ ) {
				$('#frm' + i + '>div:nth-child(' + j + ')').addClass('detail' + j);
				$('#frm' + i + '>div:nth-child(' + j + ')').removeClass('detail0');
			}
			
		}
	}

	// 상세 일정 표시 부분에 previous 버튼 클릭 시
	$('#prev-btn').click(function() {
		// 버튼에 부여한 data-index 값(= .active인 탭 박스에 index - 1) 을 가져옴
		var index = $(this).attr('data-index');
		
		// index < 1  ==  첫 번째 탭 박스가 active일 경우 작동하지 않음
		if ( index < 1 ) {
			return false;
		
		// 첫 번째 탭 박스가 아닌 경우
		} else {
			// 현재 보여지는 탭 박스의 .active 제거
			$('#tab-' + (Number(index)+1)).removeClass('active');
			// 다음에 보여질 탭 박스의 .active 추가
			$('#tab-' + index).addClass('active');
			$(this).attr('data-index', Number(index)-1);
			$('#next-btn').attr('data-index', Number(index)+1);
		}
	});

	// 상세 일정 표시 부분에 next 버튼 클릭 시
	$('#next-btn').click(function() {
		// 버튼에 부여한 data-index 값(= .active인 탭 박스에 index + 1) 을 가져옴
		var index = Number($(this).attr('data-index'));
		
		// index < 2  ==  첫 번째 탭 박스가 .active || index가 총 상세 일정의 개수보다 클 경우 == 마지막 탭 박스가 .active
		if ( index < 2 || index > $(this).parent().attr('data-count')) {
			return false;
		} else {
			// 현재 보여지는 탭 박스의 .active 제거
			$('#tab-' + Number(index-1)).removeClass('active');
			// 다음에 보여질 탭 박스의 .active 추가
			$('#tab-' + index).addClass('active');
			// data-index 변경 : prev = .active 탭 박스 인덱스의 -1 // next = .active 탭 박스 인덱스의 +1 
			$(this).attr('data-index', Number(index)+1);
			$('#prev-btn').attr('data-index', Number(index)-1);
		}
	});
	
	
	// 전체 일정을 submit하는 버튼 클릭 시
	$('#submitAll').click(function(e) {
		e.preventDefault();
		
		// modal창으로 저장 여부 체크
		$('.modal-body').text('모든 일정 작성을 완료하고 피드로 이동할까요?');
		$('#modalBtn').trigger('click');
		
		$('#trueBtn').click(function(e) {
			e.preventDefault();
			
			// id가 frm으로 시작하는 form을 배열로 모두 선택
			var form = $('form[id^=frm]').get();

			// 각각의 form 안에 placeCount input 값을 form에 data-count 값으로 입력 (각각의 일정마다의 상세일정 개수를 저장하는 과정)
			for ( var i = 1; i < form.length; i++ ) {
				var count = $('#frm' + i).data('count');
				$('#frm' + i).children('div').children('.inputbox').children('input[name=placeCount]').val(count);
			}
			
			// 모든 form value를 seralize()해서 ajax 처리	
			let data = $('form').serialize();
			console.log(data);
			$.ajax({
				url: '/init/plan/detail_modify.do',
				type: 'post',
				data: data,
				success: function(data) {
					location.href = "/init/feed";
				},
				error: function() {
					console.log('error');				
				}
			})
		})
	});
	
	// 상세 일정 박스 삭제시	
	$(document).on('click', '.deleteBtn', function() {
		// 삭제된 상세일정의 planDtNum을 '/'를 구분자로 input에 쌓아둔다.
		let deleteDtNum = $(this).siblings('.inputbox').children('input[name=planDtNum]').val();
		let dtInputValue = $('#frm0 input[name=deleteDtNum]').val();
		
		// input에 value가 없으면 구분자 없이 입력하고 아니면 prefix '/'를 덧붙인다.
		if ( dtInputValue == "" ) {
			$('#frm0 input[name=deleteDtNum]').val(deleteDtNum);
		} 
		
		// 수정페이지 작성시 새로 추가한 일정은 planDtNum이 0이기 때문에 db를 조작할 필요 없음
		if ( dtInputValue != "" && deleteDtNum != 0 ) {
			$('#frm0 input[name=deleteDtNum]').val(dtInputValue + '/' + deleteDtNum);
		}
		
		let target = $(this).parent().parent('form');
		
		// 현재 상세 일정의 개수를 변수로 선언
		let currValue = Number(target.attr('data-count'));
		
		// 지워지고 난 일정의 개수를 변수로 선언 (
		let delValue = Number(currValue)-1;
		
		// 지우려고 하는 박스가 몇번째 child인지 구해서+1 시킴
		let index = Number($(this).parent().index())+1;

		// 현재 value가 0인 경우, return false
		if ( delValue < 0 ) {
			alert('최소 1개 이상의 일정이 필요합니다');
			return false;
			
		} else if ( delValue < 1 ) {
			$(this).siblings('input[name=placeName]').val('');
			
			var inputBox = $(this).siblings('.inputbox');
			
			inputBox.children('input[name=planDtNum]').val('0');
			inputBox.children('input[name=placeName]').val('');
			inputBox.children('input[name=latitude]').val('');
			inputBox.children('input[name=longitude]').val('');
			inputBox.children('input[name=address]').val('');
			inputBox.children('input[name=category]').val('');
			inputBox.children('.form-group').children('input[name=startTime]').val('');			
			inputBox.children('.form-group').children('input[name=endTime]').val('');
			inputBox.children('.form-group').children('select[name=theme]').val('방문');
			inputBox.children('.form-group').children('input[name=transportation]').val('');
			inputBox.children('.form-group').children('input[name=details]').val('');
			
			// form에 data-count와 표시되는 현재 일정 개수를 0으로 바꿔줌
			target.attr('data-count', delValue);
			target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);
		
		} else {
			// 지워지는 박스보다 다음에 생긴 박스에 index를 조정 ex> 2번이 지워지면 3,4,5,6번을 2,3,4,5번으로 바꿈
			for ( var i = Number(index); i <= currValue; i++ ) {
				// 자기 자신의 박스를 삭제
				if ( i == Number(index) ) {
					// 버튼을 누른 상세일정 박스를 삭제
					$(this).parent().remove();
					
				// 자기 자신 다음 번호의 박스의 인덱스 변경
				} else {
					var box = $('.detail' + i);
							
					box.removeClass('detail' + i);
					box.addClass('detail' + (i-1));
				}
			}	
			// form에 data-count와 표시되는 현재 일정 개수를 0으로 바꿔줌
			target.attr('data-count', delValue);
			target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);
		}
	});
	
	// detailBtn을 누르면 교통 수단과 상세 일정을 입력하는 input toggle
	$(document).on('click', '.detailBtn', function() {
		// button에 data-count 값이 0일 때
		if( $(this).attr('data-count') == '0' ) {
			// input 박스를 보이게 바꾸고 data-count를 1로 바꿈
			$(this).siblings('.inputbox').children('.toggle').removeClass('none')
			$(this).siblings('.inputbox').children('.toggle').addClass('show');
			$(this).attr('data-count', '1');
			
		// button에 data-count 값이 1일 때
		} else {
			// input 박스를 안보이게 바꾸고 data-count를 0으로 바꿈
			$(this).attr('data-count', '0');
			$(this).siblings('.inputbox').children('.toggle').removeClass('show')
			$(this).siblings('.inputbox').children('.toggle').addClass('none');
		}
	});
	
	// startTime input에 값이 입력되고 focus가 풀렸을 경우
	$(document).on('change', 'input[name=startTime]', function(){
		var startTime = $(this).val();
		// endTime의 value
		var endTime = $(this).parent().next().children('input[name=endTime]').val();
		
		// endTime의 value가 null이면, 아직 입력하기 전이거나 입력하지 않은 경우는 아무 이벤트를 발생시키지 않음
		if ( endTime == '' ) {
			return;
		
		// endTime이 startTime보다 빠를 경우 alert
		} else if ( startTime > endTime ) {
			alert('시작시간보다 종료시간이 빠를 수 없습니다.');
			$(this).val('').focus();
		}
	});	
	
	// endTime input에 값이 입력되고 focus가 풀렸을 경우
	$(document).on('change', 'input[name=endTime]', function(){
		var endTime= $(this).val();
		// startTime의 value
		var startTime= $(this).parent().prev().children('input[name=startTime]').val();
		
		// endTime의 value가 null이면, 아직 입력하기 전이거나 입력하지 않은 것이므로 아무 이벤트를 발생시키지 않음
		if ( startTime > endTime ) {
			alert('시작시간보다 종료시간이 빠를 수 없습니다.');
			$(this).val('').focus();
		}		
	});
	

});