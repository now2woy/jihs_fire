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
						+ "	<td><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.cdCol + "</a></td>"
						+"	<td><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.cdColNm + "</a></td>"
						+"	<td style=\"text-align: center;\"><a href=\"javascript:goCd('" + value.cdCol + "');\">" + value.useYn + "</a></td>"
						+"	<td style=\"text-align: center;\"><a href=\"javascript:modify();\"><i class=\"fa fa-gears\"></i> 수정</a></td>"
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
function modify(){
	
}

function add(){
	$("#data tbody").append(
			"<tr>"
			+ "	<td></td>"
			+"	<td></td>"
			+"	<td></td>"
			+"	<td style=\"text-align: center;\"><a href=\"javascript:modify();\"><i class=\"fa fa-gears\"></i> 저장</a></td>"
			+"</tr>");
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
								<div style="float: right;">
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