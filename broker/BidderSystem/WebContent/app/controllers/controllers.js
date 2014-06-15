app.controller('RegCtrl', [
		'$scope',
		'RestService',
		'resourceData',
		'EventPublisher',
		'$routeParams',
		function($scope, RestService, resourceData, EventPublisher,
				$routeParams) {
			$scope.submit = function() {
				EventPublisher.callEvent('presave', resourceData.name, $scope,
						$routeParams);
				RestService.save(resourceData.collectionUrl,
						$scope[resourceData.name], function(returnedObject) {
							$scope[resourceData.name] = returnedObject;
							EventPublisher.callEvent('postsave',
									resourceData.name, $scope, $routeParams);
						});
			};
			init();
			function init() {
				$scope.resource = new Model();
				EventPublisher.callEvent('saveController', resourceData.name,
						$scope, $routeParams);
			}
		} ]);

app.controller('ListCtrl', [
		'$scope',
		'RestService',
		'resourceData',
		'list',
		'$location',
		'EventPublisher',"$routeParams",
		function($scope, RestService, resourceData, list, $location,
				EventPublisher, $routeParams) {
			init();
			$scope.selectPage = function(page) {
				var link = '';
				if ($scope.list.page.number + 1 < page)
					link = $scope.list.links.next;
				else
					link = $scope.list.links.prev;
				RestService.findAll(link, null, function(data) {
					$scope.list = data;
				});
			};
			function init() {
				$scope.list = list;
				$scope.routeParams = $routeParams;
			}
		} ]);
app.controller('ResourceCtrl', [
                    		'$scope',
                    		'RestService',
                    		'resourceData',
                    		'resource',
                    		'$location',
                    		'EventPublisher',
                    		'$routeParams',
                    		function($scope, RestService, resourceData, resource, $location,
                    				EventPublisher,$routeParams) {
                    			init();
                    			$scope.submit = function() {
                    				EventPublisher.callEvent('presave', resourceData.name, $scope,
                    						$routeParams);
                    				RestService.save(resourceData.collectionUrl,
                    						$scope[resourceData.name], function(returnedObject) {
                    							$scope[resourceData.name] = returnedObject;
                    							EventPublisher.callEvent('postsave',
                    									resourceData.name, $scope, $routeParams);
                    						});
                    			};
                    			function init() {
                    				$scope.resource = resource;
                    			}
                    		} ]);

app.controller('ElemCtrl', ['$scope', 'RestService', '$location', function($scope, RestService, $location){
	$scope.delete = function(link){
		RestService.delete(link, function(){
			if($scope.list !== undefined){
				var index = $scope.list.content.indexOf($scope.resource);
				$scope.list.content.splice(index, 1);
			}else{
				$scope.changeLocation(link.replace(new RegExp("/[0-9]*" + '$'), ""));
			}
		});
	};
}]);

app.controller('LoginCtrl', [
		'$rootScope',
		'$scope',
		'$http',
		'Base64',
		'$http',
		'storage',
		function($rootScope, $scope, $http, Base64, $http,storage) {
			$scope.loggedUser = $rootScope.loggedUser;
			$scope.submit = function() {
				var credentials = $scope.loggedUser.login + ":"
						+ $scope.loggedUser.password;
				var encodedCredentials = Base64.encode(credentials);
				$http.get(domain + "/login", {
					headers : {
						'Authorization' : "Basic " + encodedCredentials,
					}
				}).success(function(loggedUser) {
					$rootScope.loggedUser.id = loggedUser.id;
					$rootScope.loggedUser.password = $scope.loggedUser.password;
					$rootScope.loggedUser.loggedIn = true;
					if($rootScope.loggedUser.login == "admin")
						$rootScope.loggedUser.admin = true;
					storage.set('loggedUser', $rootScope.loggedUser);
				});
			}
		} ]);
app.controller('LogoutCtrl', [
                     		'$rootScope',
                     		'storage',
                     		'$scope',
                     		'$location',
                     		function($rootScope, storage, $scope, $location) {
                     			$scope.logout = function() {
                     				$rootScope.loggedUser = {};
                     				$rootScope.loggedUser.loggedIn = false;
                     				storage.set("loggedUser", $rootScope.loggedUser);
                     				$location.path("/");
                     			}
                     		} ]);



app.controller("UserRegCtrl",['$scope',
                        		'RestService',
                        		'$location',
                        		function($scope, RestService, $location) {
   								$scope.resource = new Model();
                        		$scope.submit = function() {
                        		    	RestService.save(resourcesData["user"].collectionUrl, $scope.resource, function(offer){
                        		    		$location.path("/");
                        		    	});
                        			};
                        			
                        		} ]);
app.controller("OfferRegCtrl",['$scope',
                        		'RestService',
                        		'$location',
                        		function($scope, RestService, $location) {
   								$scope.resource = new Model();
   								$scope.picPaths = {};
   								$scope.update = false;
   							
                        			$scope.submit = function() {
                        				$scope.resource.content.picPaths = [];
                        		    	$.each($scope.picPaths, function(i,elem){
                        		    		if(elem != "")
                        		    			$scope.resource.content.picPaths.push(elem);
                        		    	});
                        		    	RestService.save(resourcesData["offer"].collectionUrl, $scope.resource, function(offer){
                        		    		$location.path("/offers");
                        		    	});
                        			};
                        			
                        		} ]);
   app.controller("OfferUpdateCtrl",['$scope',
                           		'RestService',
                           		'$location',
                           		'resource',
                           		function($scope, RestService, $location, resource) {
   								$scope.resource = resource;
      								$scope.update = true;
                           			$scope.submit = function(){
                           				delete $scope.resource.content.picPaths;
                           		    	RestService.save(resourcesData["offer"].collectionUrl, $scope.resource, function(offer){
                           		    		$location.path("/offers");
                           		    	});
                           			};
                           			
                           		} ]);
   app.controller("PremiseUpdateCtrl",['$scope',
                               		'RestService',
                               		'$location',
                               		'resource',
                               		function($scope, RestService, $location, resource) {
       								$scope.resource = resource;
          								$scope.update = true;
                               			$scope.submit = function(){
                               				delete $scope.resource.content.picPaths;
                               		    	RestService.save(resourcesData["premise"].collectionUrl, $scope.resource, function(offer){
                               		    		$scope.changeLocation($scope.resource.links.self);
                               		    	});
                               			};
                               		} ]);

   app.controller("PremiseRegCtrl",['$scope',
                           		'RestService',
                           		'$location','$routeParams',
                           		function($scope, RestService, $location,$routeParams) {
      								$scope.resource = new Model();
      								$scope.picPaths = {};
      								$scope.update = false;
                           			$scope.submit = function() {
                           				$scope.resource.content.picPaths = [];
                           		    	$.each($scope.picPaths, function(i,elem){
                           		    		if(elem != "")
                           		    			$scope.resource.content.picPaths.push(elem);
                           		    	});
                           		    	$scope.resource.content.offer = resourcesData["offer"].collectionUrl + "/" +
                           		    	 $routeParams.offerId;
                           		    	RestService.save(resourcesData["premise"].collectionUrl, $scope.resource, function(offer){
                           		    		$location.path("/offers/"+$routeParams.offerId + "/premises");
                           		    	});
                           			};
                           			
                           		} ]);
   app.controller("PremiseReservationRegCtrl",['$scope',
                                        		'RestService',
                                        		'$location','$routeParams','$filter',
                                        		function($scope, RestService, $location,$routeParams,$filter) {
                   								$scope.resource = new Model();
                   								$scope.update = false;
                                        			$scope.submit = function() {
                                        				$scope.resource.content.reservedFrom = $filter('date')($scope.resource.content.reservedFrom, "yyyy-MM-dd'T'12:00:00.000'Z");
                                        				$scope.resource.content.reservedTo = $filter('date')($scope.resource.content.reservedTo, "yyyy-MM-dd'T'11:59:00.000'Z");
                                        				$scope.resource.content.premise = resourcesData["premise"].collectionUrl + "/"
                                        			 + $routeParams.premiseId;
                                        		    	RestService.save(resourcesData["premiseReservation"].collectionUrl, $scope.resource, function(resource){
                                        		    		$scope.changeLocation(resource.links.self);
                                        		    	});
                                        			};
                                        			
                                        		} ]);
               app.controller("PremiseReservationUpdateCtrl",['$scope',
                                           		'RestService',
                                           		'$location',
                                           		'resource',
                                           		function($scope, RestService, $location, resource) {
                   								$scope.resource = resource;
                   								$scope.update = true;
                                           			$scope.submit = function(){
                                            				$scope.resource.content.reservedFrom = $filter('date')($scope.resource.content.reservedFrom, "yyyy-MM-dd'T'12:00:00.000'Z");
                                            				$scope.resource.content.reservedTo = $filter('date')($scope.resource.content.reservedTo, "yyyy-MM-dd'T'11:59:00.000'Z");
                                           				if($scope.resource.canceled)
                                           					$scope.resource.content.status = "CANCELED";
                                           		    	RestService.save(resourcesData["premiseReservation"].collectionUrl, $scope.resource, function(offer){
                                           		    		$scope.changeLocation($scope.resource.links.self);
                                           		    	});
                                           			};
                                           		} ]);
               app.controller('PremiseReservationCtrl', ['$scope', 'RestService', '$location', function($scope, RestService, $location){
            		$scope.cancel = function(){
            			var res = new Model();
            			res.content.status = "CANCELED";
            			res.links = $scope.resource.links;
            			RestService.save($scope.resource.links.self, res, function(){
            				$scope.resource.content.status = "CANCELED";
            				$scope.changeLocation($scope.resource.links.self);
            			});
            		},	$scope.accept = function(){
            			var res = new Model();
            			res.content.status = "ACCEPTED";
            			res.links = $scope.resource.links;
            			RestService.save($scope.resource.links.self, res, function(){
            				$scope.resource.content.status = "ACCEPTED";
            				$scope.changeLocation($scope.resource.links.self);
            			});
            		}
            	}]);

            	app.controller('PagePremiseReservationCrtl',['$scope', 'RestService', '$location', 'resource','$controller', function($scope, RestService, $location,resource,$controller){
            		$scope.resource =resource;
            		$controller("PremiseReservationCtrl",{$scope:$scope, RestService:RestService, $location:$location});
            		
            	}]);
