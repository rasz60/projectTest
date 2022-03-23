<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page session="false" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd"></script>
<link rel="stylesheet" type="text/css" href="../css/feed/kakaomap/kakaomap.css" />

<!-- date 계산, tab-menu 생성 -->
<script>
function strToDate(str) {
	let y = str.slice(0, 4);
	let m = Number(str.slice(5, 7)) - 1;
	let d = str.slice(8);
	
	return new Date(y, m, d);
}

function dateToStr(date) {
	let y = date.getFullYear();
	let m = date.getMonth() + 1;
	let d = date.getDate();
	
	if ( m < 9 ) {
		m = "0" + m;
	}
	
	if ( d < 9 ) {
		d = "0" + d;
	}
	
	return y+"-"+m+"-"+d;
}

function getPlanDate(start, end) {
	var dates = [];
	dates.push(dateToStr(start));

	var date = new Date(start);
	
	while ( date < end ) {
		date.setDate(date.getDate() + 1);
		
		dates.push(dateToStr(date));
	}
	
	return dates;
}

console.log('<c:out value="${plan.startDate}" />')

var sDate = strToDate('<c:out value="${plan.startDate}" />');
console.log(sDate);


var eDate = strToDate('<c:out value="${plan.endDate}" />');
var dateCount = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);
var dates = getPlanDate(sDate, eDate);

$(document).ready(function(){
	
	for ( var i = 2; i <= dateCount; i++ ) {
		var tab_link = "<li class='tab-link' data-tab='tab-" + i  + "'>date" + i  + "</li>";
		
		var tab_div = "<div id='tab-" + i + "' class='container mt-2 tab-content'>" 
					+ "<h3>DATE" + i + " : " + dates[i-1] + "</h3>"
					+ "<hr/>"
					+ "<div class='planlist row'>"
					+ "<div id='kakaobox' class='map_wrap col-6'>"
					+ "<div id='map" + i + "' style='width:100%;height:100%;position:relative;overflow:hidden;'></div>"
					+ "<div id='menu_wrap' class='bg_white'>"
					+ "<div class='option'>"
					+ "<div>"
					+ "<form onsubmit='searchPlaces(); return false;'>"
					+ "키워드 : <input type='text' value='이태원 맛집' id='keyword' size='15'>"
					+ "<button type='submit'>검색하기</button>"
					+ "</form>"
					+ "</div>"
					+ "</div>"
			    	+ "<hr/>"
			    	+ "<ul id='placesList'></ul>"
			    	+ "<div id='pagination'></div>"
			    	+ "</div>"
			    	+ "</div>"
					+ "<div class='col-6'>"
					+ "<div>"
					+ "총 갯수 : <span id='showIndex'></span> / 10"
					+ "</div>"
					+ "<button type='button' id='insertButton' class='btn btn-success' style='float: right;'>추가</button>"
					+ "<form id='frm" + i + "' name='frm" + i + "' action='insertMap' method='post'>"
					+ "<input type='text' name='planNum' id='planNum' value='${plan.planNum}' readonly/>"
					+ "<input type='text' name='planDtNum' id='planDtNum' value=0 readonly/>"
					+ "<input type='text' name='date'" + i + " id='planDate' value='" + dates[i-1] + "' readonly />"
					+ "<button type='submit' id='submit' class='btn btn-primary' style='float: right;'>저장</button>"
					+ "</form>"
					+ "</div>"
					+ "</div>"
					+ "</div>";
			
		$('.tabs').append(tab_link);
		$('#tabdiv').append(tab_div);
	};
	
	
	$('ul.tabs li').click(function(){
	    var tab_id = $(this).attr('data-tab');

	    $('ul.tabs li').removeClass('current');
	    $('.tab-content').removeClass('current');

	    $(this).addClass('current');
		$("#"+tab_id).addClass('current');
	});

})

</script>

<!-- tab-menu css -->
<style>
html, body {
	height: 100%;
}

div.container {
	height: auto;
}

.planlist #kakaobox {
	max-width: 100%;
	min-height: 800px;
}

#tabdiv{
  width: 100%;
  margin: 0 auto;
}

ul.tabs{
  margin: 0px;
  padding: 0px;
  list-style: none;
}
ul.tabs li{
  background: none;
  color: #222;
  display: inline-block;
  padding: 10px 15px;
  cursor: pointer;
}

ul.tabs li.current{
  background: #ededed;
  color: #222;
}

.tab-content{
  display: none;
  background: #ededed;
  padding: 15px;
}

.tab-content.current{
  display: inherit;
}

textarea {
	resize: none;
}

#deleteBtn {
	height: 50%;
}

.toggle-box {
	display: none;
}

</style>

<title>Insert title here</title>

</head>
<body>
<div class="container-fluid" id="tabdiv">
	<ul class="tabs">
		<li class='tab-link current' data-tab='tab-1'>date1</li>
	</ul>
	<div id="tab-1" class="mt-2 tab-content current">
		<h3>DATE 1 : ${plan.startDate }</h3>
		<hr/>
		
		<div class="planlist row">
			<!-- 맵 생성 -->
			<div id="kakaobox" class="map_wrap col-6">
				<div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
				<div id="menu_wrap" class="bg_white">
			    	<div class="option">
			        	<div>
			            	<form onsubmit="searchPlaces(); return false;">
			                	키워드 : <input type="text" value="이태원 맛집" id="keyword" size="15"> 
			                	<button type="submit">검색하기</button> 
			            	</form>
			        	</div>
			    	</div>
			    	<hr/>
			    	<ul id="placesList"></ul>
			    	<div id="pagination"></div>
				</div>
			</div>
			
			
			
			<!-- input창 -->
			<div id="inputContainer" class="col-6">
				<div class="mb-4">
					총 갯수 : <span class="showIndex"></span> / 10
					<button type="button" class="btn btn-success insertButton btn-sm float-right">추가</button>
					<button type="button" class="btn btn-sm btn-primary submit float-right mr-1">저장</button>
				</div>


				<form id="frm1" name="frm1" action="insertMap" method="post" data-index="1" data-count="1">
					<!-- [pk] planDtNum : new input ? value=0 : value=planDtNum -->
					<input type='hidden' class="form-control" name='planDtNum' id='planDtNum' value=0 readonly/>
					<!-- [fk] planNum : planMst_planNum-->
					<input type="hidden" class="form-control" name="planNum" id="planNum" value="${plan.planNum}" readonly/>
					<!-- planDate : planDt_planDate -->
					<input type="hidden" class="form-control" name="planDate" id="planDate" value="${plan.startDate}" readonly/>
					
					<!-- User submit Input -->
					<div class="details1 mt-5 py-2 border">
						<h3 class="font-italic ml-2 d-inline">Place</h3>
						<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2" data-num="1">-</button>
						<hr />
						<div class="frm1_detail_top row mx-0 justify-content-between">
							<div class="form-group col-4">
								<label for="frm1_placeName1">placeName</label>
								<input type="text" class="form-control" name="frm1_placeName1" id="frm1_placeName1" readonly/>
							</div>
							
							<div class="form-group col-4">
								<label for="frm1_startTime1">StartTime</label>
								<input type="time" class="form-control" name="frm1_startTime1" id="frm1_startTime1"/>
							</div>
							
							<div class="form-group col-4">
								<label for="frm1_endTime1">EndTime</label>
								<input type="time" class="form-control" name="frm1_endTime1" id="frm1_endTime1"/>
							</div>
							
							<div class="form-inline col-9 ml-0">
								<label for="frm1_transpotation1" class="col-3">교통수단</label>
								<input type="text" class="form-control col-9" name="frm1_transpotation1" id="frm1_transpotation1"/>
							</div>
							
							<button type="button" class="btn btn-sm btn-outline-secondary details-btn col-2 mr-3" data-count="0">상세 일정</button>
							
							<div class="form-group col-12 toggle-box">
								<label for="frm1_details1">상세 일정</label>
								<textarea rows="5" class="form-control" name="frm1_details1" id="frm1_details1"></textarea>
							</div>
						</div>
					</div> 
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 저장 누를 시 생성되는 modal창 -->
<div class="container">
	<input id="modalBtn" type="hidden" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" value="modal"/>
	<!-- modal창 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm text-center">
			<div class="modal-content">
				<div class="modal-header bg-light">
					<h4 class="modal-title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;POST작성</h4>
				</div>
				<div class="modal-body bg-light">
					<h4>작성되었습니다.</h4>
				</div>
				<div class="modal-footer bg-light">
					<button id="closeBtn" type="button" class="btn btn-default btn-success" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="../js/feed/kakaomap/kakaomap.js"></script>
<script>
$(document).ready(function () {

	$('#inputContainer button').click(function(e) {
		e.preventDefault();
		
		var target = $(this);
		
		if ( target.hasClass('details-btn') === true ) {
			if ( $(this).attr('data-count') == 0 ) {
				$(this).siblings('.toggle-box').css('display','block');
				$(this).attr('data-count', '1');
			} else {
				$(this).siblings('.toggle-box').css('display','none');
				$(this).attr('data-count', '0');			
			}
		}
		else if ( target.hasClass('deleteBtn') === true) {
			var targetClass = "details" + $(this).attr('data-num');
			var motherForm = $(this).parent().parent('form');
			var formCount = Number(motherForm.attr('data-count'));
			
			if ( formCount == 1 ) {
				alert('최소 한개 이상의 일정이 있어야 합니다.');
				return false;
			} else {
				if ( $(this).parent().hasClass(targetClass) === true ) {
					$(this).parent().remove();
					motherForm.attr('data-count', formCount - 1);
					console.log(motherForm.attr('data-count'));
				}
			}
		}
		else if ( target.hasClass('insertButton') === true ) {
			var targetForm = $(this).parent().siblings('form');
			var namePrefix = targetForm.attr('id');
			var index = targetForm.attr('data-index');
			var detailsCount = Number(targetForm.attr('data-count'));
			console.log(detailsCount);
			var insertElement = '<div class="details' + index + ' mt-5 py-2 border">'
							  + '<h3 class="font-italic ml-2 d-inline">Place</h3>'
							  + '<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2" data-num="' + index + '">-</button>'
							  + '<hr />'
							  + '<div class="frm1_detail_top row mx-0 justify-content-between">'
							  + '<div class="form-group col-4">'
							  + '<label for="' + namePrefix + '_placeName' + index + '">placeName</label>'
							  + '<input type="text" class="form-control" name="' + namePrefix + '_placeName' + index + '" id="' + namePrefix + '_placeName' + index + '" readonly/>'
							  + '</div>'
							  + '<div class="form-group col-4">'
							  + '<label for="' + namePrefix + '_startTime' + index + '">StartTime</label>'
							  + '<input type="time" class="form-control" name="' + namePrefix + '_startTime' + index + '" id="startTime' + index + '"/>'
							  + '</div>'
							  + '<div class="form-group col-4">'
							  + '<label for="endTime' + index + '">EndTime</label>'
							  + '<input type="time" class="form-control" name="' + namePrefix + '_endTime' + index + '" id="' + namePrefix + '_endTime' + index + '"/>'
							  + '</div>'
							  + '<div class="form-inline col-9 ml-0">'
							  + '<label for="' + namePrefix + '_transpotation' + index + '" class="col-3">교통수단</label>'
							  + '<input type="text" class="form-control col-9" name="' + namePrefix + '_transpotation' + index + '" id="' + namePrefix + '_transpotation' + index + '"/> '
							  + '</div>'
							  + '<button type="button" class="btn btn-sm btn-outline-secondary details-btn col-2 mr-3" data-count="0">상세 일정</button>'
							  + '<div class="form-group col-12 toggle-box">'
							  + '<label for="' + namePrefix + '_details' + index + '">상세 일정</label>'
							  + '<textarea rows="5" class="form-control" name="' + namePrefix + '_details' + index + '" id="' + namePrefix + '_details' + index + '"></textarea>'
							  + '</div>'
							  + '</div>'
							  + '</div>';

			if ( index <= 10 ) {
				targetForm.attr('data-index', index);
				targetForm.append(insertElement);
				targetForm.attr('data-count', detailsCount + 1);
				console.log(targetForm.attr('data-count'));
			} else {
				alert('하루 일정이 10개를 초과할 수 없습니다.');
				return false;
			}
			
		};
		
	});
	
	/*
	//latitude, longitude, placeName 값이 들어갈 input창 생성
	var Form = $("#frm")
	var index = 0    	
	 $("#insertButton").on("click", function () { 
	     if(index == 10) {  // input창 생성 제한
	        alert("10개 까지만 됩니다.")
	        return false;
	    };
	     
	    var newDiv = document.createElement("div")
	    newDiv.setAttribute("class", "newDiv")
	    newDiv.setAttribute("name", "index")
	
	    var newInput1 = document.createElement("input") //위도 input
	    newInput1.setAttribute("id", "latitude"+index)
	    newInput1.setAttribute("type", "text")
	    newInput1.setAttribute("name", "latitude"+index)
	    newInput1.setAttribute("value", "")
	    newInput1.setAttribute("readonly", "true")
	    var newInput2 = document.createElement("input") //경도 input
	    newInput2.setAttribute("id", "longitude"+index)
	    newInput2.setAttribute("type", "text")
	    newInput2.setAttribute("name", "longitude"+index)
	    newInput2.setAttribute("value", "")
	    newInput2.setAttribute("readonly", "true")
	    var newInput3 = document.createElement("input") //장소명 input
	    newInput3.setAttribute("id", "placeName"+index)
	    newInput3.setAttribute("type", "text")
	    newInput3.setAttribute("name", "placeName"+index)
	    newInput3.setAttribute("value", "")
	    newInput3.setAttribute("readonly", "true")    
	    var newInput4 = document.createElement("input") //카테고리 input
	    newInput4.setAttribute("id", "category"+index)
	    newInput4.setAttribute("type", "text")
	    newInput4.setAttribute("name", "category"+index)
	    newInput4.setAttribute("value", "")
	    newInput4.setAttribute("readonly", "true")
	    var newInput5 = document.createElement("input") //카테고리 input
	    newInput5.setAttribute("id", "address"+index)
	    newInput5.setAttribute("type", "text")
	    newInput5.setAttribute("name", "address"+index)
	    newInput5.setAttribute("value", "")
	    newInput5.setAttribute("readonly", "true")
	     
	    var removeInput = document.createElement("button") //삭제 버튼
	    removeInput.setAttribute("type", "button")
	    removeInput.setAttribute("id", "removebtn")
	    removeInput.setAttribute("class", "btn btn-danger")
	    removeInput.textContent = "삭제"
		       	
	    newDiv.append(newInput1)
		newDiv.append(newInput2)
	    newDiv.append(newInput3)
	    newDiv.append(newInput4)
	    newDiv.append(newInput5)
	    newDiv.append(removeInput)
	    Form.append(newDiv)
	    			
	    index+=1
	    console.log(newDiv);
	    $("#showIndex").text(index)
	    $("#index").val(index)
	}); 

	$(document).on("click", "#removebtn", function () { // 삭제
	    $(this).parent(".newDiv").remove()
	    resetIndex()
	});

	function resetIndex(){ //삭제하면 인덱스 번호도 reset 
	    index = 0
	    Form.children('div').each(function (){
	        var target = $(this).children(index)
	        target.attr("id", target.attr("latitude"+index))
	        target.attr("id", target.attr("longitude"+index))
	        target.attr("id", target.attr("placeName"+index))
	        target.attr("id", target.attr("category"+index))
	        target.attr("id", target.attr("address"+index))
	        index+=1
	    })
	    $("#showIndex").text(index)
	    $("#index").val(index)
	};

	$("#insertButton").trigger("click") // 추가 버튼
	$("#showIndex").text(index)       	

	$("#frm").submit(function(event){ //#frm의 data 전체를 ajax로 서버에 보내기
		event.preventDefault(); //원래 form의 기능인 submit를 ajax로 처리
		$.ajax({
			type : $("#frm").attr("method"),
			url : $("#frm").attr("action"),
			data : $("#frm").serialize(),
		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
			        xhr.setRequestHeader(header, token);
			},
			success : function(data){
				console.log(data);
				if(data.search("insert-success") > -1){
					$(".modal-body").text("작성되었습니다.");
					$("#modalBtn").trigger("click");
						$("#closeBtn").click(function(event){
						event.preventDefault();
						location.href = "/feed"; // 성공시 이동 페이지
					});
				}
				else{
					$(".modal-body").text("다시입력해주세요");
					$("#modalBtn").trigger("click");
						$("#closeBtn").click(function(event){
						event.preventDefault();
						location.href = "mappage"; //실패시 이동 페이지 
					});
				}
			},
			error : function(data){
				$(".modal-body").text("다시입력해주세요");
				$("#modalBtn").trigger("click");
					$("#closeBtn").click(function(event){
					event.preventDefault();
					location.href = "mappage"; //실패시 이동 페이지
				});
			}			
		});
	});
	*/
});
</script>
</body>
</html>