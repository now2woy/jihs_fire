<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>정지인, 최현성 은퇴 계획</title>

<!-- Bootstrap -->
<link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="/css/font-awesome/font-awesome.min.css" rel="stylesheet">
<!-- NProgress -->
<link href="/css/nprogress/nprogress.css" rel="stylesheet">
<!-- iCheck -->
<link href="/css/iCheck/green.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="/css/custom.min.css" rel="stylesheet">


<!-- jQuery -->
<script src="/js/jquery/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="/js/bootstrap/bootstrap.bundle.min.js"></script>
<!-- FastClick -->
<script src="/js/fastclick/fastclick.js"></script>
<!-- NProgress -->
<script src="/js/nprogress/nprogress.js"></script>
<!-- iCheck -->
<script src="/js/iCheck/icheck.min.js"></script>

<!-- Custom Theme Scripts -->
<script src="/js/custom.js"></script>
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
									<h2>제목</h2>
									<ul class="nav navbar-right panel_toolbox" style="min-width: 40px;">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a>
										</li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="table-responsive">
										<table class="table jambo_table bulk_action">
											<thead>
												<tr class="headings">
													<th class="column-title">컬럼</th>
													<th class="column-title">컬럼명</th>
													<th class="column-title">사용여부</th>
													<th class="column-title">-</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td class=" ">ITM_CL_CD</td>
													<td class=" ">주식구분코드</td>
													<td class=" ">Y</td>
													<td class=" ">-</td>
												</tr>
												<tr>
													<td class=" ">ITM_CL_CD</td>
													<td class=" ">주식구분코드</td>
													<td class=" ">Y</td>
													<td class=" ">-</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
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