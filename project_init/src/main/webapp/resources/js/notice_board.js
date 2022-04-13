$(document).ready(function() {
	 
	let username = "<c:out value='${username}'/>";
	let bName = $('#uname').val();
	
	
	if ( username == "" || username != bName ) {
		$('#title').attr('readonly',true);
		$('#content').attr('readonly',true);
		
		$('#modBtn').css('display','none');
		$('#delBtn').css('display','none');
	}
	
	
	$('#modBtn').click(function(e) {
		e.preventDefault();
		
		if ( confirm('게시글을 수정할까요?') == true ) {
			$('#frm').submit();
		} else {
			return false;
		}
	});

	$('#delBtn').click(function(e) {
		e.preventDefault();
		
		if ( confirm('게시글을 삭제할까요?') == true ) {
			location.href = $(this).attr('href');			
			
		} else {
			return false;
		}
	});
	

	$('#rv').click(function(e) {
		e.preventDefault();
		
		$.ajax({
			url: $(this).attr('href'),
			type: 'get',
			success: function(data) {
				$('#main').html(data);
				
			},
			error: function(data) {
				console.log(data);
			}
		});
	});
	
	
});