angular.module('st-common-module', []).directive('stFormElem', function() {
	return {
		restrict : 'A',
		replace : true,
		transclude : true,
		scope : true,
		templateUrl : 'app/partials/form-elem.html',
		compile : function(cElement, cAttributes, transclude) {
			return {
				pre : function(scope, elem, attrs, controllers) {
					scope.stLabel = attrs['stLabel'];
					scope.stName = attrs['stName'];
				}
			}
		}
	}
}).provider('EventPublisher', function() {
	var self = this;

	this.events = {};
	this.events.postsave = {};
	this.events.presave = {};
	this.events.saveController = {};
	this.events.listController = {};
	this.$get = function() {
		return {
			pushEvent : function(type, entity, fn) {
				if (self.events[type][entity] === undefined)
					self.events[type][entity] = [];
				self.events[type][entity].push(fn);
			},
			callEvent : function(type, entity, scope, routeParams) {
				if (self.events[type][entity] !== undefined)
					$.each(self.events[type][entity], function(i, elem) {
						elem(scope, routeParams);
					});
			},
		};
	};
}).directive("dateFormat", ['$filter',function($filter) {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, ngModelController) {
			ngModelController.$parsers.push(function(data) {
				// View -> Model
				return data;
			});
			ngModelController.$formatters.push(function(data) {
				// Model -> View
				var date= $filter('date')(data, "yyyy-MM-dd");
				return date;
			});
		}
	}
}]);