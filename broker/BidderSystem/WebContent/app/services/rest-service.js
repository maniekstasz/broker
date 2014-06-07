app.service("RestService", ['$http',function($http) {
    this.save= function(resource, object, success, error) {
    	if(object.links !== undefined && object.links.self !== undefined){
    		$http({method: "PATCH", url: object.links.self, data: object.content}).success(function(data){
    			success(object);
    		});
    	}else{
	        $http.post(resource, object.content).success(function(data){
	        	success(new Model(data));
	        });
    	}
    };
    this.findOne = function(resource, success, error){
    	$http.get(resource).success(function(responsedData){
    		var model = new Model(responsedData);
    		success(model);
    	});
    };
    this.findAll = function(resource, params, success, error){
    	$http.get(resource, {params: params}).success(function(responsedData){
    		var list = new List(responsedData);
    		success(list);
    	});
    };
    
    this.delete = function(resource, success, error){
    	$http.delete(resource).success(success);
    };
    
}]);