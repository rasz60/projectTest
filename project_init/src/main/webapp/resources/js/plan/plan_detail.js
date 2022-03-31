$(document).ready(function() {
	var sDate = strToDate('<c:out value="${plan.startDate}" />');
	var eDate = strToDate('<c:out value="${plan.endDate}" />');
	var dateCount = '<c:out value="${plan.dateCount}" />';
	var dates = getPlanDate(sDate, eDate);
	
	
	
	
	// 일정 날짜 수(dateCount)만큼 tab-link와 tabdiv 생성
	for ( var i = 2; i <= dateCount; i++ ) {
		
		console.log();
		
		var tab_link = "<li class='nav-item' data-tab='tab-" + i  + "' data-inputForm='frm" + i  + "'  ><p class='nav-link'>date" + i  + "</p></li>";

		var tab_div = '<div id="tab-' + i + '" class="mt-2 tab-content current">' 
			        + $('#tab-1').html();
		            + '</div>';
		            
		$('.nav-tabs').append(tab_link);
		$('.planbox').append(tab_div);
		
		$('#tab-' + i ).removeClass('current');
		$('#tab-' + i + '>h3').text('DAY ' + i + ' : ' + dates[i-1]);
		$('#tab-' + i + ' #frm1').attr('name', 'frm' + i);
		$('#tab-' + i + ' #frm1').attr('id', 'frm' + i);
		$('#tab-' + i + ' #frm' + i).attr('data-date', dates[i-1]);
		$('#frm' + i).attr('data-day', 'day' + i);
		$('#frm' + i + ' .detail1 .inputbox input[name=planDay]').val('Day' + i);
		$('#frm' + i + ' input[name=planDate]').val(dates[i-1]);
	};

	// tab-link를 클릭 변경 이벤트
	$('ul.nav-tabs li').click(function(){
	    var tab_id = $(this).attr('data-tab');
		
	    // 다른 tab-link와 tab-div의 active 삭제
	    $('.nav-link').removeClass('active');
	    $('.tab-content').removeClass('current');
	    
	    // 본인에게만 active  부여
	    $(this).children('.nav-link').addClass('active');
		$("#"+tab_id).addClass('current');
	});
	
	
	$('#submitAll').click(function(e) {
		e.preventDefault();
		
		$('.modal-body').text('모든 일정 작성을 완료하고 피드로 이동할까요?');
		$('#modalBtn').trigger('click');
		
		$('#trueBtn').click(function(e) {
			e.preventDefault();

			var form = $('form[id^=frm]').get();
			
			console.log(form.length);
			
			// PlanDt - placeCount
			for ( var i = 1; i < form.length; i++ ) {
				var count = $('#frm' + i).data('count');
				$('#frm' + i).children('div').children('.inputbox').children('input[name=placeCount]').val(count);
			}

						
			let data = $('form').serialize();
			
			$.ajax({
				url: 'plan/detail.do',
				type: 'post',
				data: data,
				success: function(data) {
					console.log(data);
					if ( data == "success" ) {
						location.href = "feed";
					} else {
						$('.modal-body').text('저장에 실패하였습니다.');
					}
				},
				error: function() {
					console.log('error');				
				}
			})
		})
	});
	
	$(document).on('change', 'input[name=startTime]', function(){
		var startTime = $(this).val();
		var endTime = $(this).parent().next().children('input[name=endTime]').val();
		
		if ( endTime == '' ) {
			return;
		} else if ( startTime > endTime ) {
			alert('시작시간보다 종료시간이 빠를 수 없습니다.');
			$(this).val('').focus();
		}
	});	
	
	
	$(document).on('change', 'input[name=endTime]', function(){
		var endTime= $(this).val();
		var startTime= $(this).parent().prev().children('input[name=startTime]').val();
		
		if ( startTime > endTime ) {
			alert('시작시간보다 종료시간이 빠를 수 없습니다.');
			$(this).val('').focus();
		}		
	});
	

	
	
	$(document).on('click', '.deleteBtn', function() {
		// 삭제된 상세일정의 planDtNum을 '/'를 구분자로 input에 쌓아둔다.
		let deleteDtNum = $(this).siblings('.inputbox').children('input[name=planDtNum]').val();
		let dtInputValue = $('#frm0 input[name=deleteDtNum]').val();
		
		// input에 value가 없으면 구분자 없이 입력하고 아니면 prefix '/'를 덧붙인다.
		if ( dtInputValue == "" ) {
			$('#frm0 input[name=deleteDtNum]').val(deleteDtNum);
		} else {
			$('#frm0 input[name=deleteDtNum]').val(dtInputValue + '/' + deleteDtNum);
		}
		
		let target = $(this).parent().parent('form');
		let currValue = Number(target.attr('data-count'));
		let delValue = Number(target.attr('data-count')) - 1 ;
		let index = Number($(this).parent().index()) + 1;

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

			target.attr('data-count', delValue);
			target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);
		
		} else {
			
			console.log(index);
			console.log(currValue);
			for ( var i = Number(index); i <= currValue; i++ ) {
				if ( i == Number(index) ) {
					target.attr('data-count', delValue);
					target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);					

				} else {
					var box = $('.detail' + i);
					var delBtn = box.children('.deleteBtn');
		
					box.removeClass('detail' + i);
					box.addClass('detail' + (i-1));
			
					target.attr('data-count', delValue);
					target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);
				}
				$(this).parent().remove();
			}
		}
	});
					
	$(document).on('click', '.detailBtn', function() {
		if( $(this).attr('data-count') == '0' ) {
			$(this).siblings('.inputbox').children('.toggle').removeClass('none')
			$(this).siblings('.inputbox').children('.toggle').addClass('show');
			$(this).attr('data-count', '1');	
		} else {
			$(this).attr('data-count', '0');
			$(this).siblings('.inputbox').children('.toggle').removeClass('show')
			$(this).siblings('.inputbox').children('.toggle').addClass('none');
		}
	});


	/* method repository */
	
	// string으로 된 date를 javascript date객체로 변환
	function strToDate(str) {
		let y = str.slice(0, 4);
		let m = Number(str.slice(5, 7)) - 1;
		let d = str.slice(8);
		
		return new Date(y, m, d);
	}
	
	// javascript date객체를 string으로 변환
	function dateToStr(date) {
		let y = date.getFullYear();
		let m = date.getMonth() + 1;
		let d = date.getDate();
		
		if ( m <= 9 ) {
			m = "0" + m;
		}
		
		if ( d <= 9 ) {
			d = "0" + d;
		}
		
		return y+"-"+m+"-"+d;
	}
	
	function getPlanDate(start, end) {
		var dates = [];
		dates.push(dateToStr(start));
		
		var date = new Date(start);
		console.log('진입');
		while ( date < end ) {
			date.setDate(date.getDate() + 1);
			
			dates.push(dateToStr(date));
		}
		return dates;
	}


});


