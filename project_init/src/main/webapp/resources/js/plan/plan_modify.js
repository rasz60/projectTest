$(document).ready(function() {

	for(var i = 1; i <= dateCount; i++ ) {
		var placeCount = $('#frm'+i+'>.detail0>.inputbox>input[name=placeCount]').val();
		var planDate = $('#frm'+i+'>.detail0>.inputbox>input[name=planDate]').val();
		
		$('#frm' + i).attr('data-count', placeCount);	
		$('#frm' + i).parent().siblings('p.mt-2').children('.showIndex').text(placeCount);
		$('#frm' + i).parent().siblings('#date-title').text($('#frm' + i).attr('data-day') + ' : ' + planDate);
		$('#frm' + i).attr('data-date', planDate);
		if ( placeCount == '0' ) {
			console.log(placeCount);
			$('#frm' + i + '>div:nth-child(1)').addClass('detail1');
			$('#frm' + i + '>div:nth-child(1)').removeClass('detail0');
		} else {
			
			for ( var j = 1; j <= placeCount; j++ ) {
				console.log('#frm' + i + ' div:nth-child(' + j + ')');
				$('#frm' + i + '>div:nth-child(' + j + ')').addClass('detail' + j);
				$('#frm' + i + '>div:nth-child(' + j + ')').removeClass('detail0');
			}
			
		}
		
	}


	// tab-link를 클릭 변경 이벤트
	$('ul.nav-tabs li').click(function(){
	    var tab_id = $(this).attr('data-tab');
		
	    // 다른 tab-link와 tab-div의 active 삭제
	    $('.nav-item').removeAttr('id');
	    $('.nav-link').removeClass('active');
	    $('.tab-content').removeClass('current');
	    
	    // 본인에게만 active  부여
	    $(this).children('.nav-link').addClass('active');
	    $(this).attr('id', 'active-tab');
		$("#"+tab_id).addClass('current');
	});
	
	
	
	
	$('#submitAll').click(function(e) {
		e.preventDefault();
				
		$('.modal-body').text('모든 일정 작성을 완료하고 피드로 이동할까요?');
		$('#modalBtn').trigger('click');
		
		$('#trueBtn').click(function(e) {
			e.preventDefault();
			
			/*			
			var form = $('form[id^=frm]').get();
			console.log(form.length);
			
			// PlanDt - placeCount
			for ( var i = 1; i < form.length; i++ ) {
				var count = $('#frm' + i).data('count');
				$('#frm' + i).children('div').children('.inputbox').children('input[name=placeCount]').val(count);
			}
			*/
						
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

	


});



