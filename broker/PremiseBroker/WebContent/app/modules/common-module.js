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
});