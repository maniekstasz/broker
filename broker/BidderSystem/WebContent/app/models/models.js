var LinksHolder = function(resource) {
	var self = this;
	this.links = {};
	init();
	function init() {
		if (resource !== undefined)
			parseLinks();
	}
	function parseLinks() {
		$.each(resource.links, function(i, elem) {
			self.links[elem.rel] = elem.href;
		});
	}
};
var Model = function(resource) {
	LinksHolder.call(this, resource);
	var self = this;
	this.content = {};
	init();
	function init() {
		if (resource !== undefined)
			parseData();
	}
	function parseData() {
		$.each(resource, function(i, elem) {
			if (i != "links")
				self.content[i] = elem;
		});
	}
};
Model.prototype = LinksHolder;
Model.prototype.constructor = Model;

var List = function(resource) {
	LinksHolder.call(this, resource);
	var self = this;
	this.content = [];
	init();
	function init() {
		if (resource !== undefined) {
			self.page = resource.page;
			parseData();
		}
	}
	function parseData() {
		$.each(resource.content, function(i, elem) {
			self.content.push(new Model(elem));
		});
	}

};
List.prototype = LinksHolder;
List.prototype.constructor = List;
