$(document).ready(function() {
	//마커를 담을 배열입니다
	var markers = [];
	
	var clickedMarkers1 = [], clickedMarkers2 = [], clickedMarkers3 = [], clickedMarkers4 = [], findMarkerArray = [], clickedMarkers6 = [],
		clickedMarkers7 = [], clickedMarkers8 = [], clickedMarkers9 = [], clickedMarkers10 = [];
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
		    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
		    level: 3 // 지도의 확대 레벨
		};
	
	
	//지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption);
	
	//장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places();  
	
	//검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	
	//키워드로 장소를 검색합니다
	searchPlaces();
	
	//키워드 검색을 요청하는 함수입니다
	function searchPlaces() {
	
	var keyword = document.getElementById('keyword').value;
	
	if (!keyword.replace(/^\s+|\s+$/g, '')) {
	    alert('키워드를 입력해주세요!');
	    return false;
	}
	
	// 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
	ps.keywordSearch( keyword, placesSearchCB); 
	}
	
	//장소검색이 완료됐을 때 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
	if (status === kakao.maps.services.Status.OK) {
		  	
	    // 정상적으로 검색이 완료됐으면
	    // 검색 목록과 마커를 표출합니다
	    displayPlaces(data);
		
	    // 페이지 번호를 표출합니다
	    displayPagination(pagination);
	
	} else if (status === kakao.maps.services.Status.ZERO_RESULT) {
	
	    alert('검색 결과가 존재하지 않습니다.');
	    return;
	
	} else if (status === kakao.maps.services.Status.ERROR) {
	
	    alert('검색 결과 중 오류가 발생했습니다.');
	    return;
	
	}
	}
	
	//검색 결과 목록과 마커를 표출하는 함수입니다
	function displayPlaces(places) {
		
		var listEl = document.getElementById('placesList'), 
		menuEl = document.getElementById('menu_wrap'),
		fragment = document.createDocumentFragment(), //새로운 빈 DocumentFragment 를 생성합니다. DocumentFragment 인터페이스는 부모가 없는 아주 작은 document 객체를 나타냅니다. 
		bounds = new kakao.maps.LatLngBounds(), //LatLngBounds - WGS84 좌표계에서 사각영역 정보를 표현하는 객체를 생성한다.
												//WGS84 좌표계는 지심 좌표계인데 이것은 지구 타원체의 중심을 원점으로 하고 X, Y ,Z 방향의 축을 따라 좌표를 결정
		listStr = '';
		
		// 검색 결과 목록에 추가된 항목들을 제거합니다
		removeAllChildNods(listEl);
		
		// 지도에 표시되고 있는 마커를 제거합니다
		removeMarker();
		
		for ( var i=0; i<places.length; i++ ) {
			
		    // 마커를 생성하고 지도에 표시합니다
		    var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x), //LatLng - WGS84 좌표 정보를 가지고 있는 객체를 생성한다.
		        marker = addMarker(placePosition, i), 
		        itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
				
				console.log(places[i]);
				
		    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		    // LatLngBounds 객체에 좌표를 추가합니다
		    bounds.extend(placePosition); //extend() - 다수의 객체를 하나의 객체로 합치는 merge기능을 수행
	
		    // 마커와 검색결과 항목에 click 했을때
		    // 해당 장소에 인포윈도우에 장소명을 표시합니다
		    (function(marker, title, category, address) {
		    	//addListener(target, type, handler) - 다음 지도 API 객체에 이벤트를 등록한다. 
		    	//target : 이벤트를 지원하는 다음 지도 API 객체, type : 이벤트 이름, handler : 이벤트를 처리할 함수
		    	
		    	kakao.maps.event.addListener(marker, 'mouseover', function() { //마커에 마우스 올렸을 때
		            displayInfowindow(marker, title); // displayInfowindow()에서 처리
		        });
		
		        kakao.maps.event.addListener(marker, 'mouseout', function() { // 마커에 마우스 치웠을 때 인포창 닫기
		            infowindow.close();
		        });
		        
		        itemEl.onmouseover =  function () { //검색목록에 마우스 올렸을 때
		            displayInfowindow(marker, title); // displayInfowindow()에서 처리
		        };
		
		        itemEl.onmouseout =  function () { // 검색목록에 마우스 치웠을 때 인포창 닫기
		            infowindow.close();
		        };
	
		        kakao.maps.event.addListener(marker, 'click', function() { // 마커 클릭 시
					// 현재 보여지고 있는 탭 박스의 form을 타겟으로 선언
					var target1 = $('div.active').children('.inputDiv').children('form');
					
					// 타겟의 data-count(상세 일정 개수) value 변수 선언
					var value = target1.attr('data-count');

					// inputdata()에서 처리
					inputdata(marker, target1, value, title, category, address);
		        });
		    	                     
		        itemEl.onclick =  function () { // 검색 목록창 클릭 시
					// 현재 보여지고 있는 탭 박스의 form을 타겟으로 선언
					var target1 = $('div.active').children('.inputDiv').children('form');
					
					// 타겟의 data-count(상세 일정 개수) value 변수 선언
					var value = target1.attr('data-count');
					
					// inputdata()에서 처리
					inputdata(marker, target1, value, title, category, address);           
		        }; 
		    })(marker, places[i].place_name, places[i].category_group_code, places[i].address_name);
		
		    fragment.appendChild(itemEl); //appendChild() - 새로운 노드를 해당 노드의 자식 노드 리스트(child node list)의 맨 마지막에 추가        
		}
		
		// 검색결과 항목들을 검색결과 목록 Element에 추가합니다
		listEl.appendChild(fragment);
		menuEl.scrollTop = 0; //scrollTop - 현재 스크롤의 위치값
		
		// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		map.setBounds(bounds); //setBounds() - 주어진 영역이 화면 안에 전부 나타날 수 있도록 지도의 중심 좌표와 확대 수준을 설정한다.
	}
	
	
	
	//검색결과 항목을 Element로 반환하는 함수입니다
	function getListItem(index, places) {
	
	var el = document.createElement('li'),
	itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
	            '<div class="info">' +
	            '   <h5>' + places.place_name + '</h5>';
	
	        	             
	            
	if (places.road_address_name) {
	    itemStr += '    <span>' + places.road_address_name + '</span>' +
	                '   <span class="jibun gray">' +  places.address_name  + '</span>';
	} else {
	    itemStr += '    <span>' +  places.address_name  + '</span>'; 
	}                 
	  itemStr += '  <span class="tel">' + places.phone  + '</span>' +
	            '</div>';       
	            
	el.innerHTML = itemStr;
	el.className = 'item';
	
	return el;
	}
	
	//마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
	function addMarker(position, idx, title) {
	var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
	    imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
	    imgOptions =  {
	        spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
	        spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
	        offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
	    },
	    markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
	        marker = new kakao.maps.Marker({
	        position: position, // 마커의 위치
	        image: markerImage
	    });
	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers.push(marker);  // 배열에 생성된 마커를 추가합니다
	
	return marker;
	}
	
	//지도 위에 표시되고 있는 마커를 모두 제거합니다
	function removeMarker() {
	for ( var i = 0; i < markers.length; i++ ) {
	    markers[i].setMap(null);
	}   
	markers = [];
	}
	
	//검색결과 목록 하단에 페이지번호를 표시는 함수입니다
	function displayPagination(pagination) {
	var paginationEl = document.getElementById('pagination'),
	    fragment = document.createDocumentFragment(),
	    i; 
	
	// 기존에 추가된 페이지번호를 삭제합니다
	while (paginationEl.hasChildNodes()) { //Node.hasChildNodes() - 현재 노드(Node)에게 자식노드(child nodes)가 있는지를 Boolean 값으로 반환
	    paginationEl.removeChild (paginationEl.lastChild); //removeChild() - DOM에서 자식 노드를 제거하고 제거된 노드를 반환
	}
	
	for (i=1; i<=pagination.last; i++) {
	    var el = document.createElement('a');
	    el.href = "#";
	    el.innerHTML = i;
	
	    if (i===pagination.current) {
	        el.className = 'on';
	    } else {
	        el.onclick = (function(i) {
	            return function() {
	                pagination.gotoPage(i);
	            }
	        })(i);
	    }
	
	    fragment.appendChild(el);
	}
	paginationEl.appendChild(fragment);
	}
	
	//검색결과 목록 또는 마커에 마우스 올렸을 때 호출되는 함수입니다
	//인포윈도우에 장소명을 표시합니다
	function displayInfowindow(marker, title) {
	var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
	
	infowindow.setContent(content);
	infowindow.open(map, marker);
	}
	
	//마커와 검색결과 목록 클릭 시 input에 data 입력
	function inputdata(marker, target1, value, title, category, address) {
		// 현재 상세 일정에 추가할 것이므로 일정 개수(value) + 1한 값을 변수로 선언
		var plusVal = Number(value)+1;
		
		// 현재 value가 10인 경우, 10개 이상 만들지 못하게 return false
		if ( value > 9 ) {
			alert('하루에 열개 이상의 일정을 생성할 수 없습니다.');
			return false;
	
		// 현재 value가 1개 이상 10개 미만일 때, 새로운 상세일정 빈 박스를 생성하고 plusVal 변수로 인덱싱
		} else if ( value != 0 ){
			var boxHtml = '<div class="detail' + plusVal + ' mt-2 py-2 border bg-light rounded">'
						+ '<h3 class="font-italic ml-2 d-inline mt-2">Place</h3>'
						+ '<!-- placeName -->'
						+ '<input type="text" class="form-control col-8 d-inline ml-3" name="placeName" readonly/>'
						+ '<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2 mt-1" ><i class="fa-solid fa-minus"></i></button>'
						+ '<button type="button" class="btn btn-sm btn-dark detailBtn float-right mr-2 mt-1" data-count="0"><i class="fa-solid fa-angles-down"></i></button>'
						+ '<hr />'
						+ '<div class="inputbox row mx-0 justify-content-between">'
						+ '<!-- [pk] planDtNum -->'
						+ '<input type="hidden" class="form-control" name="planDtNum" value="0" readonly/>'
						+ '<!-- day -->'
						+ '<input type="hidden" class="form-control" name="planDay" readonly/>'
						+ '<!-- placeCount -->'
						+ '<input type="hidden" class="form-control" name="placeCount" readonly/>'
						+ '<!-- planDate -->'
						+ '<input type="hidden" class="form-control" name="planDate" readonly/>'
						+ '<!-- latitude -->'
						+ '<input type="hidden" class="form-control" name="latitude" readonly/>'
						+ '<!-- longitude -->'
						+ '<input type="hidden" class="form-control" name="longitude" readonly/>'
						+ '<!-- address -->'
						+ '<input type="hidden" class="form-control" name="address" readonly/>'
						+ '<!-- category -->'
						+ '<input type="hidden" class="form-control" name="category" readonly/>'
						+ '<!-- startTime -->'
						+ '<div class="form-group col-4">'
						+ '<label for="startTime">StartTime</label>'
						+ '<input type="time" class="form-control" name="startTime" value="" />'
						+ '</div>'
						+ '<!-- endTime -->'
						+ '<div class="form-group col-4">'
						+ '<label for="endTime">EndTime</label>'
						+ '<input type="time" class="form-control" name="endTime" value="" />'
						+ '</div>'
						+ '<!-- theme -->'
						+ '<div class="form-group col-4">'
						+ '<label for="theme">목적</label>'
						+ '<select class="custom-select my-1 mr-sm-2 " id="theme" name="theme">'
						+ '<option value="방문" selected>방문</option>'
						+ '<option value="데이트">데이트</option>'
						+ '<option value="가족여행">가족여행</option>'
						+ '<option value="친구들과">친구들과</option>'
						+ '<option value="맛집탐방">맛집탐방</option>'
						+ '<option value="비즈니스">비즈니스</option>'
						+ '<option value="소개팅">소개팅</option>'
						+ '<option value="미용">미용</option>'
						+ '<option value="운동">운동</option>'
						+ '<option value="문화생활">문화생활</option>'
						+ '<option value="여가생활">여가생활</option>'
						+ '</select>'
						+ '</div>'
						+ '<!-- transportation -->'
						+ '<div class="form-group col-12 toggle none">'
						+ '<label for="transportation">교통수단</label>'
						+ '<input type="text" class="form-control" name="transportation" value="" />'
						+ '</div>'
						+ '<!-- details -->'
						+ '<div class="form-group col-12 toggle none">'
						+ '<label for="details">상세 일정</label>'
						+ '<textarea rows="5" class="form-control" name="details" value="" ></textarea>'
						+ '</div>'
						+ '</div>'
						+ '</div>';
			
			// form 안에 제일 마지막 child로 박스 생성
			target1.append(boxHtml);
	
		}
		
		// 맵에서 나온 값을 입력할 .detail 박스
		var target2 = target1.children('.detail' + plusVal);
		
		// 위에서 생성한 .detail 박스안에 input이 들어있는 박스
		var target3 = target1.children('.detail' + plusVal).children('.inputbox');
	
		// detail박스에 placeName input에 marker의 title 입력
		target2.children('input[name=placeName]').val(title);
	
		// 맵에서 나오는 정보를 갖는 input에 해당 데이터 모두 입력
		target3.children('input[name=latitude]').val(marker.getPosition().getLat());
		target3.children('input[name=longitude]').val(marker.getPosition().getLng());
		
		target3.children('input[name=address]').val(address);
		target3.children('input[name=category]').val(category);
	
		// form에 attr를 이용하여 planDay, planDate 입력
		target3.children('input[name=planDay]').val(target1.attr('data-day'));
		target3.children('input[name=planDate]').val(target1.attr('data-date'));
	
		// form attr를 +1하고, 현재 일정 개수를 표시해주는 텍스트도 변경
		target1.attr('data-count', plusVal);					
		target1.parent().siblings('p.mt-2').children('.showIndex').text(plusVal);
		
		
		//클릭한 마커에 임의로 지정한 마커 생성
		var imageSrc = 'images/marker.png', // 마커이미지의 경로    
	    imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
	    imageOption = {offset: new kakao.maps.Point(10, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption), //마커 이미지 옵션을 markerImage객체에 담기
	    markerPosition = new kakao.maps.LatLng(marker.getPosition().getLat(), marker.getPosition().getLng()); // 마커가 표시될 위치입니다
		
		var marker = new kakao.maps.Marker({ //설정한 좌표와 이미지 마커 객체에 담기
		  position: markerPosition, //마커 좌표 설정
		  image: markerImage, // 마커이미지 설정	 
		});
	
		marker.setMap(map); // 마커를 맵에 생성
		
		// 현재 작성중인 planDay를 변수로 선언
		var planDay = target1.attr('data-day');
		
		// 현재 작성중인 planDay에 맞는 배열에 marker를 저장
		addMarkerArray(planDay, marker);

	}
	
	// 검색결과 목록의 자식 Element를 제거하는 함수입니다
	function removeAllChildNods(el) {   
		while (el.hasChildNodes()) {
		    el.removeChild (el.lastChild);
		}
	}


	// planMst.startDate, planMst.endDate를 Date 객체로 변환	
	var sDate = strToDate($('#frm0 input[name=startDate]').val());
	var eDate = strToDate($('#frm0 input[name=endDate]').val());

	// planMst.dateCount
	var dateCount = $('#frm0 input[name=dateCount]').val();

	// sDate부터 eDate까지의 날짜를 문자열 배열로 생성
	var dates = getPlanDate(sDate, eDate);

	// 일정 날짜 수(dateCount)만큼 tabdiv 동적 생성 (1번은 미리 생성해두고 시작)
	for ( var i = 2; i <= Number(dateCount); i++ ) {
		// 미리 생성해둔 #tab-1의 html을 가져와서 header index만 추가해서 동적 생성
		var tab_div = '<div id="tab-' + i + '" class="tab-content">' 
			        + $('#tab-1').html();
		            + '</div>';

		$('.planbox').append(tab_div);
		
		// 현재의 탭 박스가 몇번째 DAY이고, 실제 planDate가 몇일인지 표시
		$('#tab-' + i + '>h3').text('DAY ' + i + ' : ' + dates[i-1]);
		
		// tab박스 안에 frm의 name과 id에 똑같이 index 추가
		$('#tab-' + i + ' #frm1').attr('name', 'frm' + i);
		$('#tab-' + i + ' #frm1').attr('id', 'frm' + i);
		
		// #frm 안에 data-date에 planDate, data-day에 planDay 저장(동적 박스 생성시 input값에 부여)
		$('#frm' + i).attr('data-date', dates[i-1]);
		$('#frm' + i).attr('data-day', 'day' + i);

		// #frm 안에 input planDate, planDay에 value 입력
		$('#frm' + i + ' .detail1 .inputbox input[name=planDay]').val('day' + i);
		$('#frm' + i + ' input[name=planDate]').val(dates[i-1]);
	};

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
		var index = $(this).attr('data-index');
		
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
			
			$.ajax({
				url: 'plan/detail.do',
				type: 'post',
				data: data,
				success: function(data) {
					
					if ( data == "success" ) {
						// 성공시 feed 페이지로 이동
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
	
	/* 동적으로 생성한 엘리먼트까지 이벤트를 부여할 때, $(document).on 사용 */
	
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
	
	
	// 상세 일정에 마이너스 버튼 클릭시 이벤트
	$(document).on('click', '.deleteBtn', function() {
		let target = $(this).parent().parent('form');
		
		// 현재 상세 일정의 개수를 변수로 선언
		let currValue = Number(target.attr('data-count'));
		
		// 지워지고 난 일정의 개수를 변수로 선언 (
		let delValue = Number(currValue)-1;
		
		// 지우려고 하는 박스가 몇번째 child인지 구해서+1 시킴
		let index = Number($(this).parent().index())+1;
		
		// 현재 작성중인 일정의 planDay를 변수로 저장
		let planDay = target.attr('data-day');
		
		// 현재 value가 0인 경우, return false
		if ( delValue < 0 ) {
			alert('최소 1개 이상의 일정이 필요합니다');
			return false;
		
		// 현재 value가 1인 경우 박스를 지우지 않고 박스안에 값을 전부 초기화	
		} else if ( delValue < 1 ) {
			$(this).siblings('input[name=placeName]').val('');

			var inputBox = $(this).siblings('.inputbox');
			// 해당되는 마커를 삭제
			removeMarkerArray(planDay, (Number(index)-1));
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
					// 해당되는 마커를 삭제					
					removeMarkerArray(planDay, (Number(index)-1));
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
	
	// javascript date객체인 startDate와 endDate로 모든 일정의 날짜를 구하는 메서드
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

	// 마커를 각 일정에 맞는 배열에 저장하는 메서드
	function addMarkerArray(planDay, marker) {
		
		if ( planDay == 'day1' ) {
			clickedMarkers1.push(marker);
			
		} else if ( planDay == 'day2' ) {
			clickedMarkers2.push(marker);
			
		} else if ( planDay == 'day3' ) {
			clickedMarkers3.push(marker);
			
		} else if ( planDay == 'day4' ) {
			clickedMarkers4.push(marker);
			
		} else if ( planDay == 'day5' ) {
			clickedMarkers5.push(marker);
			
		} else if ( planDay == 'day6' ) {
			clickedMarkers6.push(marker);
			
		} else if ( planDay == 'day7' ) {
			clickedMarkers7.push(marker);
			
		} else if ( planDay == 'day8' ) {
			clickedMarkers8.push(marker);
			
		} else if ( planDay == 'day9' ) {
			clickedMarkers9.push(marker);
			
		} else if ( planDay == 'day10' ) {
			clickedMarkers10.push(marker);
		}
	};


	// 마커를 삭제하는 메서드
	function removeMarkerArray(planDay, index) {
		if ( planDay == 'day1' ) {
			if ( index >= 0 ) {
				clickedMarkers1[index].setMap(null);
				clickedMarkers1.splice(index, 1);
			}
		} else if ( planDay == 'day2' ) {
			if ( index >= 0 ) {
				clickedMarkers2[index].setMap(null);
				clickedMarkers2.splice(index, 1);
			}
			
		} else if ( planDay == 'day3' ) {
			if ( index >= 0 ) {
				clickedMarkers3[index].setMap(null);
				clickedMarkers3.splice(index, 1);
			}
			
		} else if ( planDay == 'day4' ) {
			if ( index >= 0 ) {
				clickedMarkers4[index].setMap(null);
				clickedMarkers4.splice(index, 1);
			}
			
		} else if ( planDay == 'day5' ) {
			if ( index >= 0 ) {
				clickedMarkers5[index].setMap(null);
				clickedMarkers5.splice(index, 1);
			}
			
		} else if ( planDay == 'day6' ) {
			if ( index >= 0 ) {
				clickedMarkers6[index].setMap(null);
				clickedMarkers6.splice(index, 1);
			}
			
		} else if ( planDay == 'day7' ) {
			if ( index >= 0 ) {
				clickedMarkers7[index].setMap(null);
				clickedMarkers7.splice(index, 1);
			}
			
		} else if ( planDay == 'day8' ) {
			if ( index >= 0 ) {
				clickedMarkers8[index].setMap(null);
				clickedMarkers8.splice(index, 1);
			}
			
		} else if ( planDay == 'day9' ) {
			if ( index >= 0 ) {
				clickedMarkers9[index].setMap(null);
				clickedMarkers9.splice(index, 1);
			}
			
		} else if ( planDay == 'day10' ) {
			if ( index >= 0 ) {
				clickedMarkers10[index].setMap(null);
				clickedMarkers10.splice(index, 1);
			}
		}
	};


});

