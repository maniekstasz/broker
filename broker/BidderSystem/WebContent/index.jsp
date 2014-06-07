<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" data-ng-app="app" id="app">
<!--<![endif]-->
<head>
<!-- stylesheets -->

<link rel="stylesheet" href="resources/css/bootstrap.css" />
<!-- components -->
<script type="text/javascript"
	src="resources/components/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="resources/components/angular.js"></script>
<script type="text/javascript" src="resources/components/ngShowHide.js"></script>

<script type="text/javascript"
	src="resources/components/ui-bootstrap-custom-0.10.0.js"></script>
<!-- 	models -->
<script type="text/javascript" src="app/models/models.js"></script>
<!-- modules -->
<script type="text/javascript" src="app/modules/common-module.js"></script>
<script type="text/javascript" src="app/modules/st-auth-module.js"></script>

<!-- app sources -->
<script type="text/javascript" src="app/app.js"></script>

<!-- controllers -->
<script type="text/javascript" src="app/controllers/controllers.js"></script>

<!-- services -->
<script type="text/javascript" src="app/services/rest-service.js"></script>


</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
	<div class="container">
		<div class="header">
			<ul class="nav nav-pills pull-right">
				<li><a href="#/users/register">Register user</a></li>
				<li><a href="#/users">Users</a></li>
			</ul>
			<h3 class="text-muted">Bidder system</h3>
		</div>
		<div class="jumbotron">
			<div class="row">
				<div class="col-sm-9" data-ng-view=""></div>
				<div data-ng-controller="LoginCtrl" class="col-sm-3 form-horizontal">
					<div class="form-group">
						<label class="col-sm-4">Login</label> <input type="text"
							data-ng-model="loggedUser.login" class="form-control" />
					</div>
					<div class="form-group">
						<label class="col-sm-4">Password</label> <input type="password"
							data-ng-model="loggedUser.password" class="form-control" />
					</div>
				</div>

			</div>
		</div>
	</div>


</body>