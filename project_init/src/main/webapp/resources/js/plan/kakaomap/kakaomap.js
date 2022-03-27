//마커를 담을 배열입니다
var markers = [];

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
				var key = $('p.active').parent().data('inputform');
				var target1 = $('#'+key);
				var value = target1.attr('data-count');

				inputdata(marker, target1, value, title, category, address);// inputdata()에서 처리
	        });
	    	                     
	        itemEl.onclick =  function () { // 검색 목록창 클릭 시
				var key = $('p.active').parent().data('inputform');
				var target1 = $('#'+key);
				var value = target1.attr('data-count');

				inputdata(marker, target1, value, title, category, address);// inputdata()에서 처리              
	        }; 
	    })(marker, places[i].place_name, places[i].category_name, places[i].address_name);
	
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
//input에 insert하기 위한 index번호 생성

function inputdata(marker, target1, value, title, category, address) {

	if ( value > 9 ) {
		alert('하루에 열개 이상의 일정을 생성할 수 없습니다.');
		return false;

	} else if ( value != 0 ){
		var boxHtml = '<div class="detail' + value + ' mt-2 py-2 border bg-light rounded">'
					+ '<h3 class="font-italic ml-2 d-inline mt-2">Place</h3>'
					+ '<!-- placeName -->'
					+ '<input type="text" class="form-control col-8 d-inline ml-3" name="placeName" readonly/>'
					+ '<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2 mt-1"><i class="fa-solid fa-minus"></i></button>'
					+ '<button type="button" class="btn btn-sm btn-dark detailBtn float-right mr-2 mt-1" data-count="0"><i class="fa-solid fa-angles-down"></i></button>'
					+ '<hr />'
					+ '<div class="inputbox row mx-0 justify-content-between">'
					+ '<!-- [pk] planDtNum -->'
					+ '<input type="hidden" class="form-control" name="planDtNum" value="0" readonly/>'
					+ '<!-- day -->'
					+ '<input type="hidden" class="form-control" name="planDay" readonly/>'
					+ '<!-- placeCount -->'
					+ '<input type="hidden" class="form-control" name="placeIndex" value="0" readonly/>'
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
					+ '<div class="form-group col-6">'
					+ '<label for="startTime">StartTime</label>'
					+ '<input type="time" class="form-control" name="startTime" value="" />'
					+ '</div>'
					+ '<!-- endTime -->'
					+ '<div class="form-group col-6">'
					+ '<label for="endTime">EndTime</label>'
					+ '<input type="time" class="form-control" name="endTime" value="" />'
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
		target1.append(boxHtml);
	}

	var target2 = target1.children('.detail' + value);
	var target3 = target1.children('.detail' + value).children('.inputbox');
	
	
	target3.children('input[name=planDay]').val(target1.attr('data-day'));
	target3.children('input[name=planDate]').val(target1.attr('data-date'));
	target2.children('input[name=placeName]').val(title);
	target3.children('input[name=latitude]').val(marker.getPosition().getLat());
	target3.children('input[name=longitude]').val(marker.getPosition().getLng());
	target3.children('input[name=address]').val(address);
	target3.children('input[name=category]').val(category);


	target1.attr('data-count', Number(value) + 1);					
	target1.parent().siblings('p.mt-2').children('.showIndex').text(Number(value) + 1);
	target3.children('input[name=placeIndex]').val(Number(value) + 1);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {   
while (el.hasChildNodes()) {
    el.removeChild (el.lastChild);
}
}
