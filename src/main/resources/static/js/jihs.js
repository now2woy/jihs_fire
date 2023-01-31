/**
 * 목록 테이블 초기화
 */
function ls_table_init(url, idx, async){
	var listUrl = url;
	// 페이징 처리일 경우
	if(PAGE_CONFIG["PAGING_YN"] == 'Y'){
		listUrl = url + ls_pageing_parm(url, idx);
	}
	
	$("#data tbody").empty();
	
	$.ajax({
		url: listUrl
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : async
	})
	.done(function(json) {
		// 목록 처리
		if(json.data.length != 0){
			$.each(json.data, function(key, value){
				var idx;
				var html = "<tr>";
				
				data.forEach(function(item) {
					html = html + "	<td id=\"TD_" + item["NM"] + value[PAGE_CONFIG["IDX"]] + "\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\">";
					
					if(item["FUNC"] != ""){
						html = html + "<a href=\"#\" onclick=\"" + item["FUNC"] + "\">" + bc_nullToBlank(value[item["VAL"]]) + "</a></td>";
					} else {
						// 숫자일 경우
						if(item["TYPE"] == "I"){
							html = html + bc_num_format(bc_nullToBlank(value[item["VAL"]])) + "</td>";
						
						// 숫자가 아닐 경우
						} else {
							html = html + bc_nullToBlank(value[item["VAL"]]) + "</td>";
							
						}
					}
					idx = value[PAGE_CONFIG["IDX"]];
				});
				
				// 수정 버튼 여부가 "Y" 일 때만 생성
				if(PAGE_CONFIG["MOD_BTN_YN"] == "Y"){
					// MOD_BTN 가 ""이 아닐 때 다른 값으로 변경
					if(PAGE_CONFIG["MOD_BTN"] != ""){
						html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:" + PAGE_CONFIG["MOD_BTN"] + "('" + idx + "');\"><i class=\"fa fa-pencil\"></i> 수정</a></td>";
					}else{
						html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_mod('" + idx + "');\"><i class=\"fa fa-pencil\"></i> 수정</a></td>";
					}
				} else if(PAGE_CONFIG["MOD_BTN_YN"] == "N") {
					html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"></td>";
				}
				
				html = html + "</tr>";
				
				$("#data tbody").append(html);
			});
		}
		
		// 페이징 처리일 경우
		if(PAGE_CONFIG["PAGING_YN"] == 'Y'){
			if(json.count != 0){
				PAGE_CONFIG["TOTAL_COUNT"] = json.count;
				PAGE_CONFIG["THIS_PAGE_IDX"] = idx;
				PAGE_CONFIG["MAX_PAGE_IDX"] = Math.floor(PAGE_CONFIG["TOTAL_COUNT"] / PAGE_CONFIG["PAGE_SIZE"]) + 1;
				
				ls_pageinate(url);
			}
		}
		
		PAGE_CONFIG["LIST_DATA"] = json;
	});
}

/**
 * 등록 버튼
 */
function ls_table_add(){
	var html = "<tr>";
	
	data.forEach(function(item) {
		// 텍스트박스
		if(item["TYPE"] == "T" || item["TYPE"] == "I"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"><input type=\"text\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + bc_cd_to_val(item["OPT"]) + " /></td>";
		
		// 버튼있는 택스트박스
		} else if(item["TYPE"] == "TB"){
			html = html + "<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"><div class=\"input-group\" style=\"margin: 0px;\">";
			html = html + "<input type=\"text\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + bc_cd_to_val(item["OPT"]) + " />";
			html = html + "<span><a href=\"#\" class=\"btn\" style=\"border: 1px solid #ddd; background-color: #fafafa; margin: 0px;\"><i class=\"fa fa-search\"></i></a></span>";
			html = html + "</div></td>";
		
		// 셀렉트박스
		} else if(item["TYPE"] == "S"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"><select id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option value=\"Y\">Y</option><option value=\"N\">N</option></select></td>";
		
		// 없음
		} else if(item["TYPE"] == "N"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"></td>";
		
		// 날자 선택
		} else if(item["TYPE"] == "D"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"><input type=\"date\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + bc_cd_to_val(item["OPT"]) + " /></td>";
			
		// 날자-시간 선택
		} else if(item["TYPE"] == "DT"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\" style=\"" + bc_cd_to_val(item["TDST"]) + " vertical-align: middle;\"><input type=\"datetime-local\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + bc_cd_to_val(item["OPT"]) + " /></td>";
			
		}
	});
	
	// 저장 함수가 있을 경우
	if(PAGE_CONFIG["INS_FUNC"] != ""){
		html = html + "	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:" + PAGE_CONFIG["INS_FUNC"] + "('NEW', 'POST');\"><i class=\"fa fa-floppy-o\"></i> 저장</a></td>";
		
	// 저장 함수가 없을 경우
	} else {
		html = html + "	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_save('NEW', 'POST');\"><i class=\"fa fa-floppy-o\"></i> 저장</a></td>";
	}
	
	html = html + "</tr>";
	
	$("#data tbody").append(html);
	$("#add-btn").attr("style", "display : none;");
}

/**
 * 목록 테이블 수정 버튼
 */
function ls_table_mod(id){
	data.forEach(function(item) {
		// 데이터 추출
		item["DATA"] = $("#TD_" + item["NM"] + id).text();
		
		// 타입이 없지 않을 경우
		if(item["TYPE"] != "N"){
			// 데이터 삭제
			$("#TD_" + item["NM"] + id).empty();
		}
		
		// 인풋박스로 변경
		// 텍스트박스
		if(item["TYPE"] == "T"){
			$("#TD_" + item["NM"] + id).append("<input type=\"text\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"] + "\"" + bc_cd_to_val(item["OPT"]) + " />");
			
		// 숫자박스
		} else if(item["TYPE"] == "I"){
			$("#TD_" + item["NM"] + id).append("<input type=\"text\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"].replace(/,/gi, "") + "\"" + bc_cd_to_val(item["OPT"]) + " />");
			
		// 버튼있는 택스트박스
		} else if(item["TYPE"] == "TB"){
			var html = "<div class=\"input-group\" style=\"margin: 0px;\">";
			html = html + "<input type=\"text\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"] + "\"" + bc_cd_to_val(item["OPT"]) + " />";
			html = html + "<span><a href=\"#\" class=\"btn\" style=\"border: 1px solid #ddd; background-color: #fafafa; margin: 0px;\"><i class=\"fa fa-search\"></i></a></span>";
			html = html + "</div>";
			
			$("#TD_" + item["NM"] + id).append(html);
			
		// 셀렉트박스
		} else if(item["TYPE"] == "S"){
			if(item["DATA"] == "Y"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option selected=\"selected\" value=\"Y\">Y</option><option value=\"N\">N</option></select>");
				
			} else if(item["DATA"] == "N"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option value=\"Y\">Y</option><option selected=\"selected\" value=\"N\">N</option></select>");
			}
			
		// 날자 선택
		} else if(item["TYPE"] == "D"){
			$("#TD_" + item["NM"] + id).append("<input type=\"date\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"] + "\"" + bc_cd_to_val(item["OPT"]) + " />");
			
		// 날자-시간 선택
		} else if(item["TYPE"] == "DT"){
			$("#TD_" + item["NM"] + id).append("<input type=\"datetime-local\" id=\"" + item["NM"] + id + "\" class=\"form-control\" value=\"" + item["DATA"].replace(" ", "T") + ":00\"" + bc_cd_to_val(item["OPT"]) + " />");
		}
	});
	
	$("#TD_BTN_" + id).empty();
	
	// 저장 함수가 있을 경우
	if(PAGE_CONFIG["INS_FUNC"] != ""){
		$("#TD_BTN_" + id).append("<a href=\"javascript:" + PAGE_CONFIG["INS_FUNC"] + "('" + id + "', 'PUT');\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
		
	// 저장 함수가 없을 경우
	} else {
		$("#TD_BTN_" + id).append("<a href=\"javascript:ls_table_save('" + id + "', 'PUT');\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
	}
}

/**
 * 페이징 파라미터 생성
 */
function ls_pageing_parm(url, idx){
	var parm;
	
	if(url.indexOf('?') != -1){
		parm = "&limit=" + PAGE_CONFIG["PAGE_SIZE"] + "&offset=" + PAGE_CONFIG["PAGE_SIZE"] * (idx - 1);
	} else {
		parm = "?limit=" + PAGE_CONFIG["PAGE_SIZE"] + "&offset=" + PAGE_CONFIG["PAGE_SIZE"] * (idx - 1);
	}
	
	return parm;
}

/**
 * 페이징 버튼 생성
 */
function ls_pageinate(url){
	var page = "";
	
	// 전체 건수가 페이지 사이즈보다 클 경우에만 페이징을 만든다.
	if(PAGE_CONFIG["TOTAL_COUNT"] > PAGE_CONFIG["PAGE_SIZE"]){
		page = "<ul class=\"pagination\">";
		
		start_page_idx = (Math.floor((PAGE_CONFIG["THIS_PAGE_IDX"] - 1) / 10) * 10) + 1;
		end_page_idx = start_page_idx + 10;
		
		if(end_page_idx > PAGE_CONFIG["MAX_PAGE_IDX"]){
			end_page_idx = PAGE_CONFIG["MAX_PAGE_IDX"] + 1;
		}
		
				
		if(start_page_idx >= 10){
			page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '1');\">◀</a></li>";
			page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '" + (start_page_idx - 10) + "');\">◁</a></li>";
		}
		
		for(var i = start_page_idx; i < end_page_idx; i++){
			if(i == PAGE_CONFIG["THIS_PAGE_IDX"]){
				page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '" + i + "');\" style=\"color : #FF0000;\">" + i + "</a></li>";
			} else {
				page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '" + i + "');\">" + i + "</a></li>";
			}
		}
		
		if((end_page_idx - 1) != PAGE_CONFIG["MAX_PAGE_IDX"]){
			page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '" + end_page_idx + "');\">▷</a></li>";
			page = page + "<li class=\"paginate_button\"><a href=\"#\" onclick=\"ls_table_init('" + url + "', '" + PAGE_CONFIG["MAX_PAGE_IDX"] + "');\">▶</a></li>";
		}
		
		page = page + "</ul>";
	}
	
	$("#paginate").empty();
	$("#paginate").append(page);
}

/**
 * 목록 테이블 저장 버튼
 */
function ls_table_save(id, method){
	if(confirm("저장하시겠습니까?")){
		var param = ct_save_data(id);
		
		$.ajax({
			url: PAGE_CONFIG["SAVE_URL"]
			, data : JSON.stringify(param)
			, method: method
			, dataType: "json"
			, contentType: 'application/json'
			, async : false
		})
		.done(function(json) {
			location.reload();
		});
	}
}

/**
 * 저장 할 데이터 생성
 */
function ct_save_data(id){
	var param = new Object();
	
	data.forEach(function(item) {
		if(item["VAL2"] != ""){
			param[item["VAL2"]] = $("#" + item["NM"] + id).val();
		} else {
			param[item["VAL"]] = $("#" + item["NM"] + id).val();
		}
	});
	
	return param;
}

/**
 * 특정 URL의 값을 가져와 SELECT 박스를 만들어서 리턴
 */
function ct_cd_select(url, val, nm){
	var result = "<select id=\"#ID#\" class=\"form-control\"><option value=''>선택</option>";
	
	$.ajax({
		url: url
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : false
	})
	.done(function(json) {
		if(json.data.length != 0){
			$.each(json.data, function(key, value){
				result = result + "<option value=\"" + value[val] + "\">" + value[nm] + "</option>";
			});
		}
	});
	
	result = result + "</select>";
	
	return result;
}

/*
 * 코드를 실제 값으로 변경
 */
function bc_cd_to_val(cd){
	if(cd == "O_D"){
		return " disabled=\"disabled\"";
	} else if(cd == "O_R"){
		return " readonly=\"readonly\"";
	} else if(cd == "T_C"){
		return "text-align: center;";
	} else if(cd == "T_R"){
		return "text-align: right;";
	} else {
		return "";
	}
}

/**
 * null을 ""으로 변환한다.
 */
function bc_nullToBlank(str){
	if(str == null){
		return "";
	} else {
		return str;
	}
}

/**
 * null을 "0"으로 변환한다.
 */
function bc_nullToZero(str){
	if(str == null){
		return "0";
	} else {
		return str;
	}
}

/**
 * 숫자에 콤마 추가
 */
function bc_num_format(num){
	return bc_nullToZero(num).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

/**
 * 팝업창을 닫는다.
 */
function pu_close(){
	$("#popup_layer").css('display', 'none');
	$("#popup_title").empty();
	$("#popup_body").empty();
}