<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/inc.jsp" %>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url: "/api/code/columns"
		, data : null
		, method: "GET"
		, dataType: "json"
		, async : true
	})
	.done(function(json) {
		
		if(json.length != 0){
			$.each(json, function(key, value){
				$("#data tbody").append(
						"<tr>"
						+ "	<td id=\"TD_CD_COL_" + value.cdCol + "\"><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.cdCol + "</a></td>"
						+"	<td id=\"TD_CD_COL_NM_" + value.cdCol + "\"><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.cdColNm + "</a></td>"
						+"	<td id=\"TD_USE_YN_" + value.cdCol + "\" style=\"text-align: center;\"><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.useYn + "</a></td>"
						+"	<td id=\"TD_BTN_" + value.cdCol + "\" style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:modify('" + value.cdCol + "');\"><i class=\"fa fa-gears\"></i> 수정</a></td>"
						+"</tr>");
			});
		}
	});
});

/**
 * 코드 목록
 */
function goCd(cdCol){
	location.href="/bsc/codes.do?cdCol=" + cdCol;
}

/**
 * 수정
 */
function modify(id){
	var cdCol = $("#TD_CD_COL_" + id + " a").text();
	var cdColNm = $("#TD_CD_COL_NM_" + id + " a").text();
	var useYn = $("#TD_USE_YN_" + id + " a").text();
	
	var select = "<select id=\"USE_YN_" + id + "\" class=\"form-control\">";
	
	if(useYn == "Y"){
		select = select + "<option selected=\"selected\" value=\"Y\">Y</option><option value=\"N\">N</option></select>";
	} else {
		select = select + "<option value=\"Y\">Y</option><option selected=\"selected\" value=\"N\">N</option></select>";
	}
	
	$("#TD_CD_COL_" + id).empty();
	$("#TD_CD_COL_NM_" + id).empty();
	$("#TD_USE_YN_" + id).empty();
	$("#TD_BTN_" + id).empty();
	
	$("#TD_CD_COL_" + id).append("<input type=\"text\" id=\"CD_COL_" + id + "\" class=\"form-control\" value=\"" + cdCol + "\" readonly=\"readonly\" />");
	$("#TD_CD_COL_NM_" + id).append("<input type=\"text\" id=\"CD_COL_NM_" + id + "\" class=\"form-control\" value=\"" + cdColNm + "\" />");
	$("#TD_USE_YN_" + id).append(select);
	$("#TD_BTN_" + id).append("<a href=\"javascript:save('" + id + "', 'PUT');\"><i class=\"fa fa-gears\"></i> 저장</a>");
}

/**
 * 등록 버튼
 */
function add(){
	$("#data tbody").append(
			"<tr>"
			+ "	<td><input type=\"text\" id=\"CD_COL_NEW\" name=\"CD_COL_NEW\" class=\"form-control\" value=\"\" /></td>"
			+"	<td><input type=\"text\" id=\"CD_COL_NM_NEW\" name=\"CD_COL_NM_NEW\" class=\"form-control\" value=\"\" /></td>"
			+"	<td style=\"text-align: center;\">"
			+"		<select id=\"USE_YN_NEW\" name=\"USE_YN_NEW\" class=\"form-control\">"
			+"			<option value=\"Y\">Y</option>"
			+"			<option value=\"N\">N</option>"
			+"		</select>"
			+"	</td>"
			+"	<td style=\"text-align: center; vertical-align: middle;\"><a href=\"javascript:save('NEW', 'POST');\"><i class=\"fa fa-gears\"></i> 저장</a></td>"
			+"</tr>");
	
	$("#btn_div").attr("style", "float: right; display : none;")
}

/**
 * 저장버튼
 */
function save(id, method){
	if(confirm("저장하시겠습니까?")){
		var param = new Object();
		
		param.cdCol = $("#CD_COL_" + id).val();
		param.cdColNm = $("#CD_COL_NM_" + id).val();
		param.useYn = $("#USE_YN_" + id).val();
		
		$.ajax({
			url: "/api/code/columns"
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
</script>
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
		
		<!-- 왼쪽 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/left.jsp" %>
		<!-- 왼쪽 메뉴 종료 -->
		
		<!-- 상단 메뉴 시작 -->
			<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
		<!-- 상단 메뉴 종료 -->
			
		<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="clearfix"></div>
					
					<div class="row" style="display: block; min-height: 880px;">
						<div class="col-md-12 col-sm-12  ">
							<div class="x_panel">
								<div class="x_title">
									<h2>코드 컬럼 목록</h2>
									<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a>
										</li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="table-responsive">
										<table id="data" class="table jambo_table bulk_action">
											<colgroup>
												<col style="width: 35%;" />
												<col style="width: 35%;" />
												<col style="width: 15%;" />
												<col style="width: 15%;" />
											</colgroup>
											<thead>
												<tr class="headings">
													<th class="column-title" style="text-align: center;">컬럼</th>
													<th class="column-title" style="text-align: center;">컬럼명</th>
													<th class="column-title" style="text-align: center;">사용여부</th>
													<th class="column-title" style="text-align: center;">-</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
								
							<!-- 버튼 영역 시작 -->
								<div id="btn_div" style="float: right;">
									<button type="button" class="btn btn-success" onclick="add();">등록</button>
								</div>
							<!-- 버튼 영역 종료 -->
								
							</div>
						</div>
					</div>
				</div>
			</div>
		<!-- /page content -->
			
		</div>
	</div>
</body>
</html>