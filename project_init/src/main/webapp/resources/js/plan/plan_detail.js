$(document).ready(function() {
	
	// 일정 날짜 수(dateCount)만큼 tab-link와 tabdiv 생성
	for ( var i = 2; i <= dateCount; i++ ) {
		var tab_link = "<li class='nav-item' data-tab='tab-" + i  + "' data-inputForm='frm" + i  + "'  ><p class='nav-link'>date" + i  + "</p></li>";

		var tab_div = '<div id="tab-' + i + '" class="mt-2 tab-content current">' 
			        + $('#tab-1').html();
		            + '</div>';
		            
		$('.nav-tabs').append(tab_link);
		$('.planbox').append(tab_div);
		
		$('#tab-' + i ).removeClass('current');
		$('#tab-' + i + '>h3').text('DATE ' + i + ' : ' + dates[i-1]);
		$('#tab-' + i + ' #frm1').attr('name', 'frm' + i);
		$('#tab-' + i + ' #frm1').attr('id', 'frm' + i);
		$('#tab-' + i + ' #frm' + i).attr('data-date', dates[i-1]);
		$('#frm' + i).attr('data-day', 'day' + i);
		$('#frm' + i + ' .detail1 .inputbox input[name=planDay]').val('day' + i);
		$('#frm' + i + ' input[name=planDate]').val(dates[i-1]);
	};

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
	
	$(document).on('click', '.deleteBtn', function() {
		var target = $(this).parent().parent('form');
		var currValue = Number(target.attr('data-count'));
		var delValue = Number(target.attr('data-count')) - 1 ;
		var index = $(this).attr('data-index');
		
		if ( delValue < 0 ) {
			alert('최소 1개 이상의 일정이 필요합니다');
			return false;
			
		} else if ( delValue < 1 ) {
			$(this).siblings('input[name=placeName]').val('');
			
			var inputBox = $(this).siblings('.inputbox');
			
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

			for ( var i = Number(index); i <= currValue; i++ ) {
				if ( i == Number(index) ) {
					target.attr('data-count', delValue);
					target.parent().siblings('p.mt-2').children('.showIndex').text(delValue);					

				} else {
					var box = $('.detail' + i);
					var delBtn = box.children('.deleteBtn');
					
					box.attr('data-index', Number(box.attr('data-index'))-1 );
					delBtn.attr('data-index', Number(box.attr('data-index'))-1 );
		
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



