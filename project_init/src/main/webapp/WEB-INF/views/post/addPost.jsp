<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet" type="text/css" href="../css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="../css/includes/footer.css" />
<title>List</title>

<script>
var dateCount = '<c:out value="${plan1.dateCount}" />'

var planDt = new Array();

<c:forEach items="${plan2}" var="item" >

	<c:choose>
		<c:when test="${empty item.placeName}">
			var placeName = "Place";
		</c:when>
		
		<c:otherwise>
			var placeName = "${item.placeName}";
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${empty item.startTime}">
			var startTime = "- - : - -";
		</c:when>
		
		<c:otherwise>
			var startTime = "${item.startTime}";
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${empty item.endTime}">
			var endTime = "- - : - -";
		</c:when>
		
		<c:otherwise>
			var endTime = "${item.endTime}";
		</c:otherwise>
	</c:choose>


	var dto = {
			planDtNum : '${item.planDtNum}',
			planDay : '${item.planDay}',
			placeName : placeName,
			startTime : startTime,
			endTime : endTime,
			theme : '${item.theme}'
	}
	
	planDt.push(dto);
</c:forEach>


</script>

<style>
section.container {
	padding-top: 4rem;
	height: auto;
}

body::-webkit-scrollbar,
textarea::-webkit-scrollbar{
    width: 10px;
}

body::-webkit-scrollbar-thumb,
textarea::-webkit-scrollbar-thumb {
    background: #A0A0A0 ;
    border-radius: 15px;
}

body::-webkit-scrollbar-track,
textarea::-webkit-scrollbar-track {
    background: #CCCCCC;
}

.detail-days, .plan-details {
	width: 100%;
}

#plan-day {
	font-size: 35px;
	font-weight: 500;
}


.plan-details div[id^=details] div.list-group-item {
	height: 75px;
}

.plan-details div[id^=details] {
	display: none;
}

.plan-details div[id^=details].active {
	display: block;
}

.postImg {
	max-width: 100%;
	height: 800px;
	max-height: 800px;
}

#addForm .form-group textarea {
	width: 100%;
	resize: none;
}

p#notice {
	font-size: 12px;
	font-style: italic;
}

h4.placeName,
h6.startTime,
h6.endTime {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.addLocBtn {
	height: 45%;
	display: none;
}

.addLocBtn.active,
.addLocBtn[disabled=disabled] {
	display: block;
}

.locations-box {
	width: auto;
	height: 30px;
	overflow-x: auto;
	display: none;
}

.locations-box::-webkit-scrollbar,
.imgView::-webkit-scrollbar {
    width: 2px;
    height: 4px;
}

.locations-box::-webkit-scrollbar-thumb,
.imgView::-webkit-scrollbar-thumb {
    background: #A0A0A0 ;
    border-radius: 5px;
}

.locations-box::-webkit-scrollbar-track,
.imgView::-webkit-scrollbar-track {
    background: #CCCCCC;
}

.location-item {
	height: 90%;
	font-size: 13px;
	font-style: italic;
	white-space: pre;
}

span.delLocBtn {
	cursor: pointer;
}

.imgView {
	border: none;
	overflow-x: auto;
}

.addImgBtn {
	border-color: #726BDF;
	background-color: #726BDF;
	width: 82%;
	height: 90%;
	font-size: 30px;
}

.reimg {
	position: relation;
	top: 0;
	color: red;
	cursor: pointer;
}
</style>
</head>

<body class="bg-light">

<%@ include file="../includes/header.jsp" %>
 <br/><br/>
 <section class="container mb-5">
 
 	<div class="posting-box row mx-0">
 		<div class="col-4">
			<div class="detail-days row mx-0 d-flex" data-count="">
				<%-- 3-1- 현재 보이는 planDay --%>
				<h4 id="plan-day" class="display-4 col-9 font-italic">DAY 1</h4>

				<%-- 3-2- 이전 planDay 이동 버튼 --%>
				<button type="button" class="btn btn-outline-default text-dark col-1" id="prev-btn" data-index="0">
					<i class="fa-solid fa-angle-left"></i>
				</button>
				
				<%-- 3-3- 다음 planDay 이동 버튼 --%>
				<button type="button" class="btn btn-outline-default text-dark col-1" id="next-btn" data-index="2">
					<i class="fa-solid fa-angle-right"></i>
				</button>
			</div>
			
			<%-- 4- 상세 일정 출력 박스 --%>
			<div class="plan-details mt-1">
				<%-- 4-2- 상세 일정의 장소 이름, 시작/종료 시간을 출력 --%>
				<div id="details1" class="px-0">
					<c:forEach begin="1" end="10" var="i">
						<div class="list-group-item planDt${i} row flex-wrap d-flex mx-0 px-1 pt-2">
							<h4 class="placeName col-10 font-italic"></h4>
							<button type="button" class="btn btn-sm btn-success col-1 px-0 py-0 addLocBtn">+</button>
							<h6 class="font-italic col-6 startTime"></h6>
							<h6 class="font-italic col-6 endTime"></h6>
						</div>
					</c:forEach>
				</div>
			</div>
 		</div>
 		
		<form id="addForm" action="uploadMulti?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data" class="col-8">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
			<input type="hidden" name="email" value="${user}"/>		

			<input type="hidden" name="planDtNum" class="planDtNum" value="" />
			<input type="hidden" name="location" class="location" value="" />
			<div class="list-group-item py-1 mb-1">
				<input type="hidden" />
				<h4 class="px-0">Location</h4>
				<p id="notice">최대 10개의 장소를 매칭할 수 있습니다.</p>
				<div class="locations-box">
				</div>
			</div>
			
			<div class="list-group-item mb-1 row d-flex mx-0 px-0">
				<div class="col-10">
					<h4>Image</h4>
					<p id="notice">최대 10장, 전체 용량 20MB를 초과할 수 없습니다.</p>
				</div>
				
				<div class="col-2 text-center mt-1">
					<button type="button" class="btn btn-xl btn-primary addImgBtn">
						<i class="fa-brands fa-instagram"></i>
					</button>
					<div class="input-group" style="display: none;">
						<div class="custom-file">
							<input name="img" type="file" class="img custom-file-input" placeholder="img" id="inputGroupFile01" multiple="multiple" required>
							<label class="custom-file-label" for="inputGroupFile01">Choose file</label>
						</div>
					</div>
				</div>
				
				
				<hr />
				
				<!-- 이미지 보이는 박스 -->
				<div class="imgView" style="display: flex; flex-wrap: nowrap;"></div>
				<!-- 이미지 보이는 박스 -->
				<input name="addImg" type="file" class="addImg" multiple="multiple" style="display: none;">
			</div>



			<div class="list-group-item form-group mb-1">
				<h4>Content</h4>
				<p id="notice">해시태그는 최대 10개 이상을 초과할 수 없습니다.&nbsp;<span class="textCount" style="font-weight: 600;">( 0/300자 )</span></p>
				<hr />
				<input name="usertag" type="text" class="title form-control" placeholder="@user">
				<textarea class="form-control content mt-2" name="content" rows="10" cols="40" placeholder="content" required></textarea>
				<input name="hashtag" type="text" class="title form-control mt-2 hashtag" placeholder="#HASHTAG">
			</div>
			
			<div class="list-group-item button-group row mx-0 d-flex justify-content-around">					
		        <input type="reset" class="btn btn-sm btn-dark col-4 border-white" value="초기화"/>
		        <input type="button" class="btn btn-sm btn-primary col-4 border-white" onclick="checkfrm()" value="등록"/>
		        <input type="button" class="btn btn-sm btn-danger col-4 border-white" data-dismiss="modal" value="피드로 가기"/>
			</div>
		</form>
	
	</div>
</section>
<%@ include file="../includes/footer.jsp" %>

<script type="text/javascript">

function setPlanDt(data, dateCount) {
	// tab 박스의 부모 element가 될 위치를 선택
	var target1 = $('.plan-details');

	// 기본으로 생성되어 있는 details1의 html을 가져오고 div#details로 감싸줌
	var boxHtml = '<div id="details" class="px-0">'
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
			
			if ( data[j].planDay == "day" + i ) {
				// 각각의 위치에 텍스트 입력
				target2.children('.planDt' + count).children('input.planDtNum').val(data[j].planDtNum);
				target2.children('.planDt' + count).children('h4').text(data[j].placeName);
				target2.children('.planDt' + count).children('.startTime').text('start : ' + data[j].startTime);
				target2.children('.planDt' + count).children('.endTime').text('end : ' + data[j].endTime);
				target2.children('.planDt' + count).children('.addLocBtn').attr('data-index', data[j].planDtNum);
				
				if ( data[j].placeName == 'Place' ) {
					target2.children('.planDt' + count).children('.addLocBtn').attr('disabled', 'disabled');
					
				} else {
					target2.children('.planDt' + count).children('.addLocBtn').addClass('active');
				}
				
				count++;
			}
		}
	}
}



$(document).ready(function() {
	setPlanDt(planDt, dateCount);

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
			$('#plan-day').text('DAY ' + index);
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
		if ( index < 2 || index > dateCount) {
			return false;
			
		} else {
			// 현재 보여지는 탭 박스의 .active 제거
			$('#details' + Number(index-1)).removeClass('active');
			// 다음에 보여질 탭 박스의 .active 추가
			$('#details' + index).addClass('active');
			// planDay가 몇인지 표시 
			$('#plan-day').text('DAY ' + index);
			// data-index 변경 : prev = .active 탭 박스 인덱스의 -1 // next = .active 탭 박스 인덱스의 +1 
			$(this).attr('data-index', Number(index)+1);
			$('#prev-btn').attr('data-index', Number(index)-1);
		}
	});
	
	
	$(document).on('click', '.addLocBtn', function() {
		var addLocBtn = $(this);
		var placeName = addLocBtn.siblings('h4.placeName').text();
		var planDtNum = $(this).attr('data-index');
		
		
		if ( $('.locations-box').children('.location-item').length == 0 ) {
			$('.locations-box').css('height', '28px');
			$('.locations-box').css('display', 'flex');
			$('.locations-box').css('flex-wrap', 'nowrap');
		}
		
		
		if ( $('.locations-box').children('.location-item').length == 10 ) {
			alert('장소는 10개까지 추가할 수 있습니다.');
			return false;
		}

		var item = '<div class="mr-1 px-1 location-item border bg-light rounded">' + placeName + '&nbsp;<span class="text-danger delLocBtn" data-index="' + planDtNum + '">&times;</span></div>';
		
		$('.locations-box').append(item);
		
		addLocBtn.attr('disabled', 'disabled');	
	});

	
	$(document).on('click', '.delLocBtn', function() {
		var index = $(this).attr('data-index');
		
		$('.addLocBtn[data-index='+ index + ']').removeAttr('disabled');
		
		
		$(this).parent().remove();
		
		if ( $('.locations-box').children('.location-item').length == 0 ) {
			$('.locations-box').css('height', '0px');
			$('.locations-box').css('display', 'none');
		}
		
	});
	
	$('.content').keyup(function () {
		let tmp = $(this).val().length;
		
		if (tmp != 0 || tmp != '') {
			 $('.textCount').text(tmp + '/300자');
		}  
		if (tmp > 299) {
			$(this).val($(this).val().substring(0, 299));
		};
	});

	$('.hashtag').keyup(function () {
		
		let element = $(this);
		let count = element.val().split('#').length-1;
		let hashtag = element.val().split('#');
		let result = '';
		
		if(count>=11){

			for(let i=0; i<11; i++){
			
				if(i==0){
				
					result=hashtag[0];
				} else {
					result +='#'+hashtag[i];
				}
			}
			alert('해쉬태그는 10개까지만 등록가능합니다\n'+result);
			element.val(result); 
			
		} else {
			for(let i=0; i<hashtag.length; i++) {
				if(i==0) {
					result=hashtag[0];
				} else {
					result +='#'+hashtag[i];									
				}
			}
		}	
		
		$(this).attr('value',result);	
	});

	$(document).on('click','.reimg',function(){
		
		const dataTransfer = new DataTransfer(); 
		let changeData ="";
		let arr = $('.img')[0].files;
		let index = $(this).attr('index');
			
		let fileArray = Array.from(arr); //변수에 할당된 파일을 배열로 변환(FileList -> Array) 
		fileArray.splice(index, 1); //해당하는 index의 파일을 배열에서 제거 
		fileArray.forEach(file => { dataTransfer.items.add(file); }); //남은 배열을 dataTransfer로 처리(Array -> FileList) 
		$('.img')[0].files = dataTransfer.files; //제거 처리된 FileList를 돌려줌
			
		changeData = $('.img')[0].files;
		imgView='';
		for(var i=0; i<changeData.length; i++){
			imgView +='<img src="'+URL.createObjectURL(changeData[i])+'" style="width :23%">'
			imgView +='<i class="fa-solid fa-x reimg" index="'+i+'"></i>';
			imgView +='<br/>';
		}
			
			//동일 이미지 등록 가능 (추가)
			$('.addImg').val('');
			
		$('.imgView').html(imgView);	
		
	});
	
	$('.addImg').change(function(){ //파일 추가
		const dataTransfer = new DataTransfer(); 
		let arr = $('.img')[0].files;
		let arr2 =$('.addImg')[0].files;
		
		let totalSize = 0;
		
		console.log(totalSize);
		
		if(arr.length+arr2.length>10){
			alert('10장 이상 등록할수 없습니다.\n다시 선택해주세요');
			//파일값 초기화
			for(var i=0; i<arr.length; i++){
				totalSize+=arr[i].size;
			}
			for(var i=0; i<arr2.length; i++){
				totalSize+=arr2[i].size;		
			}
			
			if(totalSize>100000000-1){
				alert('이미지의 총 용량이 10MB를 초과합니다.\n 다른 이미지를 올려주세요');
			}
			
		}else {
			let fileArray = Array.from(arr); //변수에 할당된 파일을 배열로 변환(FileList -> Array) 
			for(var i=0; i<arr2.length; i++){				
				fileArray.push(arr2[i]);				
			}
			
			fileArray.forEach(file => { dataTransfer.items.add(file); }); //남은 배열을 dataTransfer로 처리(Array -> FileList) 
			$('.img')[0].files = dataTransfer.files; 
			arr = $('.img')[0].files;
			
		}
		

		let imgView='';
		
		for(var i=0; i<arr.length; i++){
			
			imgView +='<img src="'+URL.createObjectURL(arr[i])+'" style="width :23%">'
			imgView +='<i class="fa-solid fa-x reimg" index="'+i+'"></i>';
			imgView +='<br/>'
		}

		$('.imgView').html(imgView);
		$('.addImg').val('');	
	});
	
	$(document).on('click','.addImgBtn',function(){
		$('input[name=addImg]').trigger('click');
	});
	
});

function checkfrm() { 
	if($('.img').val()==''){
		alert('1장 이상의 사진등록은 필수입니다.');
		return false;
	} else {
		var	dtNums = $('.delLocBtn').length;
		var target1 = $('input.planDtNum');
		var target2 = $('input.location');

		if ( dtNums != 0 ) {
			for(var i = 0; i < Number(dtNums); i++ ) {
				var dtNum = $('.delLocBtn').eq(i).attr('data-index');
				
				if ( i == 0 ) {
					target1.val(dtNum);
					console.log($('.location-item').eq(i).text());
				} else {
					target1.val(target1.val() + '/' + dtNum);
					console.log($('>' + '.location-item').eq(i).text());
				}
			}
		}

		$('#addForm').submit();
	}
}




</script>


</body>
</html>