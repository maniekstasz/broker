var app = angular.module('app', ['st-auth-module', 'ui.bootstrap', 'st-common-module','angularLocalStorage','ngCookies']);
var domain = "https://localhost:8443/BidderSystem/api";
var resourcesData = {
		user: {
			collectionUrl: "https://localhost:8443/BidderSystem/api/users",
			name: "user"
		} ,offer: {
			collectionUrl: "https://localhost:8443/BidderSystem/api/offers",
			name: "offer"
		} ,premise: {
			collectionUrl: "https://localhost:8443/BidderSystem/api/premises",
			name: "premise"
		},premiseReservation: {
			collectionUrl: "https://localhost:8443/BidderSystem/api/premiseReservations",
			name: "premiseReservation"
		}  
};
app.config(['$routeProvider','$httpProvider', 'EventPublisherProvider',
function($routeProvider,$httpProvider) {
   

	
	$httpProvider.defaults.headers.patch = {
	        'Content-Type': 'application/json;charset=utf-8'
	    };
	$httpProvider.interceptors.push('stAuthInterceptor');
	

	
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
        	list: ['$q','RestService', '$location' ,function($q, RestService, $location) {
        		var resource = domain + $location.path();
                return resolveFetcher($q, RestService.findAll, resource, params);
            }]
    	};
    }
    function getResourceResolver(resourceData, trim){
    	return {
    		resourceData: function(){
        		return resourceData;
        	},
        	resource: ['$q','RestService', '$location' ,function($q, RestService, $location) {
        		var resource = domain + $location.path().replace(trim,"");
                return resolveFetcher($q, RestService.findOne, resource);
            }]
    	};
    }
    
	
    $routeProvider.when("/offers", {
        controller : 'ListCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/offer/offer-list.html",
        resolve: getListResolver(resourcesData['offer'],{size:10})
        }
    ).when("/offers/register", {
        controller : 'OfferRegCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/offer/offer-reg.html",
    }).when("/offers/:offerId/update", {
        controller : 'OfferUpdateCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/offer/offer-reg.html",
        resolve: getResourceResolver(resourcesData["offer"], "/update")
    }).when("/offers/:offerId/premises/register", {
        controller : 'PremiseRegCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/premise/premise-reg.html",
    }).when("/offers/:offerId/premises", {
        controller : 'ListCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/premise/premise-list.html",
        resolve: getListResolver(resourcesData["premise"],{size:10})
    }).when("/premises/:premiseId/update", {
        controller : 'PremiseUpdateCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/premise/premise-reg.html",
        resolve: getResourceResolver(resourcesData["premise"], "/update")
    }).when("/premises/:premiseId", {
        controller : 'ResourceCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/premise/premise.html",
        resolve: getResourceResolver(resourcesData['premise'])
        }
    ).when("/premiseReservations/:premiseReservationId/premise", {
        controller : 'ResourceCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/premise/premise.html",
        resolve: getResourceResolver(resourcesData['premise'])
        }
    ).when("/premises/:premiseId/premiseReservations/register", {
        controller : 'PremiseReservationRegCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/reservation/reservation-reg.html",
    }).when("/premiseReservations/:premiseReservationId/update", {
        controller : 'PremiseReservationUpdateCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/reservation/reservation-reg.html",
        resolve: getResourceResolver(resourcesData["premiseReservation"], "/update")
    }).when("/premiseReservations", {
        controller : 'ListCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/reservation/premise-reservation-list.html",
        resolve: getListResolver(resourcesData['premiseReservation'],{size:10})
        }
    ).when("/premiseReservations/:premiseReservationId", {
        controller : 'PagePremiseReservationCrtl',
        templateUrl : "https://localhost:8443/PremiseBroker/app/partials/reservation/premise-reservation.html",
        resolve: getResourceResolver(resourcesData['premiseReservation'])
        }
    ).when("/", {
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/main.html",
    }).when("/users/register", {
        controller : 'UserRegCtrl',
        templateUrl : "https://localhost:8443/BidderSystem/app/partials/user/userReg.html",
    }).otherwise({
        redirectTo : '/'
    });

   

}]).run(['EventPublisher', "$location", "$rootScope", "storage", function(EventPublisher, $location, $rootScope,storage){
	var loggedUser = storage.get("loggedUser");
		$rootScope.changeLocation = function(link) {
			link = link.replace(domain, "");
			$location.path(link);
		};
	if(loggedUser == null){
		$rootScope.loggedUser = {};
		$rootScope.loggedUser.loggedIn = false;
	}else
		$rootScope.loggedUser = loggedUser;
// EventPublisher.pushEvent('presave', 'bidder', function(scope){
// scope.resource.content.webHooks = [];
// if(scope.webHooks.reservation.uri)
// scope.resource.content.webHooks.push({uri: scope.webHooks.reservation.uri,
// type: "RESERVATION_REQUEST"});
// if(scope.webHooks.invoice.uri)
// scope.resource.content.webHooks.push({uri: scope.webHooks.invoice.uri, type:
// "INVOICE_SEND"});
// });
// EventPublisher.pushEvent('postsave', 'bidder', function(scope){
// $location.path('/');
// });
// EventPublisher.pushEvent('saveController', 'bidder', function(scope){
// scope.webHooks = {};
// scope.webHooks.reservation ={};
// scope.webHooks.invoice = {};
// });
// EventPublisher.pushEvent('presave', 'offer', function(scope){
// scope.resource.content.picPaths = [];
// $.each(scope.picPaths, function(i,elem){
// if(elem != "")
// scope.resource.content.picPaths.push(elem);
// });
// });
// EventPublisher.pushEvent('saveController', 'offer', function(scope){
// scope.picPaths = {};
// });
// EventPublisher.pushEvent('presave', 'premise', function(scope, routeParams){
// scope.resource.content.picPaths = [];
// $.each(scope.picPaths, function(i,elem){
// if(elem != "")
// scope.resource.content.picPaths.push(elem);
// });
// scope.resource.content.offer = resourcesData["offer"].collectionUrl + "/" +
// routeParams.offerId;
// });
// EventPublisher.pushEvent('saveController', 'premise', function(scope){
// scope.picPaths = {};
// });
//	 
// EventPublisher.pushEvent('presave', 'premiseReservation', function(scope,
// routeParams){
// scope.resource.content.premise = resourcesData["premise"].collectionUrl + "/"
// + routeParams.premiseId;
// });
}]);
