/**
 * 목록 테이블 초기화
 */
function ls_table_init(url){
	$.ajax({
		url: url
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : true
	})
	.done(function(json) {
		if(json.data.length != 0){
			$.each(json.data, function(key, value){
				var idx;
				var html = "<tr>";
				
				data.forEach(function(item) {
					html = html + "	<td id=\"TD_" + item["NM"] + value[item["IDX"]] + "\"" + bc_cd_to_val(item["TDST"]) + ">";
					
					if(item["FUNC"] != ""){
						html = html + "<a href=\"#\" onclick=\"" + item["FUNC"] + "\">" + bc_nullToBlank(value[item["VAL"]]) + "</a></td>";
					} else {
						html = html + bc_nullToBlank(value[item["VAL"]]) + "</td>";
					}
					idx = value[item["IDX"]];
				});
				
				// 수정 버튼 여부가 "Y" 일 때만 생성
				if(MOD_BTN_YN == "Y"){
					// MOD_BTN 가 ""이 아닐 때 다른 값으로 변경
					if(MOD_BTN != ""){
						html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:" + MOD_BTN + "('" + idx + "');\"><i class=\"fa fa-pencil\"></i> 수정</a></td>";
					}else{
						html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_mod('" + idx + "');\"><i class=\"fa fa-pencil\"></i> 수정</a></td>";
					}
				} else if(MOD_BTN_YN == "N") {
					html = html + "	<td id=\"TD_BTN_" + idx + "\" style=\"text-align: center; vertical-align: middle;\"></td>";
				}
				
				html = html + "</tr>";
				
				$("#data tbody").append(html);
			});
		}
	});
}

/**
 * 등록 버튼
 */
function ls_table_add(){
	var html = "<tr>";
	
	data.forEach(function(item) {
		// 텍스트박스
		if(item["TYPE"] == "T"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\"" + bc_cd_to_val(item["TDST"]) + "><input type=\"text\" id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\" value=\"\"" + bc_cd_to_val(item["OPT"]) + " /></td>";
		
		// 셀렉트박스
		} else if(item["TYPE"] == "S"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\"" + bc_cd_to_val(item["TDST"]) + "><select id=\"" + item["NM"] + "NEW\" name=\"" + item["NM"] + "NEW\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option value=\"Y\">Y</option><option value=\"N\">N</option></select></td>"
		
		// 없음
		} else if(item["TYPE"] == "N"){
			html = html + "	<td id=\"TD_" + item["NM"] + "NEW\"" + bc_cd_to_val(item["TDST"]) + "></td>"
		}
	});
	
	html = html + "	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:ls_table_save('NEW', 'POST');\"><i class=\"fa fa-floppy-o\"></i> 저장</a></td>";
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
			
		// 셀렉트박스
		} else if(item["TYPE"] == "S"){
			if(item["DATA"] == "Y"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option selected=\"selected\" value=\"Y\">Y</option><option value=\"N\">N</option></select>");
				
			} else if(item["data"] == "N"){
				$("#TD_" + item["NM"] + id).append("<select id=\"" + item["NM"] + id + "\" class=\"form-control\"" + bc_cd_to_val(item["OPT"]) + "><option value=''>선택</option><option value=\"Y\">Y</option><option selected=\"selected\" value=\"N\">N</option></select>");
			}
		}
	});
	$("#TD_BTN_" + id).empty();
	$("#TD_BTN_" + id).append("<a href=\"javascript:ls_table_save('" + id + "', 'PUT');\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
}

/**
 * 목록 테이블 저장 버튼
 */
function ls_table_save(id, method){
	if(confirm("저장하시겠습니까?")){
		var param = new Object();
		
		data.forEach(function(item) {
			if(item["VAL2"] != ""){
				param[item["VAL2"]] = $("#" + item["NM"] + id).val();
			} else {
				param[item["VAL"]] = $("#" + item["NM"] + id).val();
			}
		});
		
		$.ajax({
			url: SAVE_URL
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
 * 코드 컬럼에 해당하는 코드를 select 박스로 만들어서 리턴
 */
function ct_cd_select(cdCol){
	var result = "<select id=\"#ID#\" class=\"form-control\"><option value=''>선택</option>";
	
	$.ajax({
		url: "/api/codes/" + cdCol
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : false
	})
	.done(function(json) {
		if(json.data.length != 0){
			$.each(json.data, function(key, value){
				result = result + "<option value=\"" + value["cd"] + "\">" + value["cdNm"] + "</option>";
			});
		}
	});
	
	result = result + "</select>";
	
	return result;
}

function bc_cd_to_val(cd){
	if(cd == "O_D"){
		return " disabled=\"disabled\"";
	} else if(cd == "O_R"){
		return " readonly=\"readonly\"";
	} else if(cd == "T_C"){
		return " style=\"text-align: center;\"";
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