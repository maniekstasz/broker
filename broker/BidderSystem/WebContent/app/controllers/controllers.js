app.controller('RegCtrl', ['$scope','RestService', 'resourceData',function($scope, RestService, resourceData) {
   $scope.submit = function(){
    	RestService.save(resourceData.collectionUrl,$scope[resourceData.name], function(returnedObject){
    		$scope[resourceData.name] = returnedObject;
    	});
    };
    init();
    function init(){
    	$scope[resourceData.name] = new Model();
    }
}]);

app.controller('ListCtrl', ['$scope','RestService', 'resourceData', 'list',function($scope, RestService, resourceData,list) {
	    init();
	    $scope.selectPage = function(page){
	    	var link = '';
	    	if($scope.list.page.number + 1 < page)
	    		link = $scope.list.links.next;
	    	else
	    		link = $scope.list.links.prev;
	    	RestService.findAll(link, null, function(data){
	    		$scope.list = data;
	    	});
	    };
	    function init(){
	    	$scope.list = list;
	    }
	}]);

app.controller('LoginCtrl', ['$rootScope', '$scope', function($rootScope,$scope){
	$scope.loggedUser = {};
	$rootScope.loggedUser = $scope.loggedUser;
}]);