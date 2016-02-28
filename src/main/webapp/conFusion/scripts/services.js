'use strict';

angular.module('confusionApp').constant("baseURL", "http://localhost:3000/")
		.service(
				'menuFactory',
				[
						'$http',
						'baseURL',
						'$resource',
						function($http, baseURL, $resource) {

							this.getDishes = function() {
								// return $http.get(baseURL + "dishes");
								return $resource(baseURL + "dishes/:id", null,
										{ 'update' : { method : 'PUT' } });
							};

							this.getDish = function(index) {
								return $http.get(baseURL + "dishes/" + index);
							};

							this.getPromotion = function(index) {
								return $http.get(baseURL + "promotions/"
										+ index);
							};
							
							this.getPromotions = function() {
								return $resource(baseURL + "promotions/:id", null,
										{ 'update' : { method : 'PUT' } });
							};
						} ])

		.factory('corporateFactory',
				[ '$http', 'baseURL','$resource', function($http, baseURL, $resource) {

					var corpfac = {};

					corpfac.getLeaders = function() {
//						return $http.get(baseURL + "leadership/");
						return $resource(baseURL + "leadership/:id", null,
								{ 'update' : { method : 'PUT' } });
					};

					corpfac.getLeader = function(index) {
						return $http.get(baseURL + "leadership/" + index);
					};
					return corpfac;
				} ]);
