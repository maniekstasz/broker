var app = angular.module('app', ['st-auth-module', 'ui.bootstrap', 'st-common-module']);

app.config(['$routeProvider','$httpProvider',

function($routeProvider,$httpProvider) {
   
	$httpProvider.defaults.headers.patch = {
	        'Content-Type': 'application/json;charset=utf-8'
	    };
	$httpProvider.interceptors.push('stAuthInterceptor');
	
	resourcesData = {
			user: {
				collectionUrl: "https://localhost:8443/BidderSystem/api/users",
				name: "user"
			} 
	};
	
    function resolveFetcher($q, _function, resource, params) {
        var deferred = $q.defer();
        var success = function(data) {
            deferred.resolve(data);
        };
        if (params !== undefined)
            _function(resource, params, success);
        else
            _function(resource, success);
        return deferred.promise;
    };
    
    function getListResolver(resourceData, params){
    	return {
    		resourceData: function(){
        		return resourceData;
        	},
        	list: ['$q','RestService',function($q, RestService) {
                return resolveFetcher($q, RestService.findAll, resourceData.collectionUrl, params);
            }]
    	};
    }
    
	
    $routeProvider.when("/users/register", {
        controller : 'RegCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/user/userReg.html",
        resolve:{
        	resourceData: function(){
        		return resourcesData['user'];
        		}
        }
    }).when("/users", {
        controller : 'ListCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/user/user-list.html",
        resolve: getListResolver(resourcesData['user'],{size:10})
        }
    ).otherwise({
        redirectTo : '/'
    });

   

}]);
