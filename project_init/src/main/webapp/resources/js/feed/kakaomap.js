//마커를 담을 배열입니다
var markers = [];

var clickedMarkers1 = [], clickedMarkers2 = [], clickedMarkers3 = [], clickedMarkers4 = [], clickedMarkers5 = [], 
	clickedMarkers6 = [], clickedMarkers7 = [], clickedMarkers8 = [], clickedMarkers9 = [], clickedMarkers10 = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
	    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
	    level: 3 // 지도의 확대 레벨
	};


//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption);
setTimeout(function(){ map.relayout(); }, 0);
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

