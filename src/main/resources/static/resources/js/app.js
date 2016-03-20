var app = angular
	.module('News', [ 'ui.router', 'ui-notification', 'ngResource' ])

	.config(
		function($stateProvider, $urlRouterProvider, $httpProvider) {

		    $stateProvider.state('nav', {
			url : '/front/login',
			templateUrl : '/views/login.html',
			controller : 'Navigation'
		    })

		    // posts
		    .state('home', {
			url : '/front/home',
			templateUrl : '/views/home.html',
			controller : 'MainCtrl'
		    })

		    // post details
		    .state('posts', {
			url : '/front/post/{id}',
			templateUrl : '/views/post.html',
			controller : 'PostCtrl'
		    })

		    // post details
		    .state('authors', {
			url : '/front/authors',
			templateUrl : '/views/author.html',
			controller : 'AuthorCtrl'
		    })

		    // error
		    .state('error', {
			url : '/front/error',
			templateUrl : '/views/error.html',
			controller : 'Error'
		    });

		    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

		    $urlRouterProvider.otherwise('/front/home');

		})

	.controller(
		'MainCtrl',
		function($scope, $http, Notification) {

		    $scope.post = {};
		    $scope.isEdit = false;

		    $scope.authors = [];
		    $http.get('/rest/authors').success(function(data) {
			$scope.authors = data;
		    });

		    $http.get('/rest/posts').success(function(data) {
			$scope.posts = data;
		    });

		    $scope.savePost = function(post) {
			console.log(post);
			if (post.title == null || post.body == null
				|| post.author == null) {
			    Notification.error('Data Error');

			} else {
			    $http.post('/rest/savePost', post).success(
				    function(data, status, headers, config) {
					$scope.posts = data;
					$scope.clearPost();
					$scope.isEdit = false;
				    });
			    Notification.success('Done!');
			}
		    };

		    $scope.deletePost = function(post) {
			if (confirm("Delete!")) {
			    $http.post('/rest/deletePost', post).success(
				    function(data, status, headers, config) {
					$scope.posts = data;
					$scope.clearPost();
					Notification.info('Deleted!');
					$scope.isEdit = false;
				    }).error(function() {
				Notification.error('Must be Admin !');
			    })
			}
			;
		    };

		    $scope.editPost = function(post) {
			$scope.post = post;
			$scope.isEdit = true;
		    };

		    $scope.clearPost = function() {
			$scope.post = null;
			$scope.isEdit = false;
		    };

		})

	.controller(
		'PostCtrl',
		function($scope, $stateParams, $http, $resource, Notification) {

		    $scope.post = {};
		    $scope.comment = {};
		    $scope.comment.rating = 1;
		    $scope.postComment = {
			post : $scope.post,
			comment : $scope.comment
		    };

		    $scope.orderBy = [ 'rating', 'date', 'upvotes' ];
		    $scope.reverseSort = function() {
			$scope.order = ($scope.isReverse ? '' : '-')
				+ $scope.orderAttr;
		    };

		    $http.get('/rest/posts/' + $stateParams.id).success(
			    function(data) {
				$scope.post = data;
			    });

		    // $http.get('/comments/'+
		    // $stateParams.id).success(function(data) {
		    // $scope.post.comments = data;
		    // });

		    $scope.saveComment = function() {
			$http.post('/rest/saveComment/' + $scope.post.id,
				$scope.comment).success(
				function(data, status, headers, config) {
				    $scope.post = data;
				    $scope.clearComment();
				});
			Notification.success('Done!');
		    };

		    $scope.clearComment = function() {
			$scope.comment.rating = 1;
			$scope.comment.comment = null;
			$scope.commentForm.comment.$pristine = true;
		    }

		    $scope.upvote = function(isUp, comment) {
			$http
				.post(
					'/rest/upvoteComment/' + isUp + '/'
						+ $scope.post.id, comment)
				.success(
					function(data, status, headers, config) {
					    $scope.post = data;
					}).error(function(error) {
				    if (error.status == 403) {
					Notification.error('Must be Admin !');
				    }
				});
		    };

		})

	.controller(
		'Navigation',
		function($rootScope, $scope, $http, $location) {

		    $rootScope.userName = '';
		    $rootScope.isAdmin = false;
		    // $rootScope.hideAuthorMenu = false;

		    var authenticate = function(credentials, callback) {
			var headers = credentials ? {
			    authorization : "Basic "
				    + btoa(credentials.username + ":"
					    + credentials.password)
			} : {};
			
			//console.log(headers);

			$http.get('/user', {
			    headers : headers
			}).success(function(data) {
			    // console.log(data);
			    if (data == '') {
				$location.path("/front/login");
				$rootScope.authenticated = false;
			    } else {
				$rootScope.userName = data.username;
				$rootScope.authenticated = true;
				checkIfIsAdmin(data.authorities);
			    }
			    callback && callback($rootScope.authenticated);
			}).error(function() {
			    $rootScope.authenticated = false;
			    callback && callback(false);
			});

		    }

		    checkIfIsAdmin = function(data) {
			if (data == null || !$rootScope.authenticated) {
			    return false;
			}
			data.some(function(e, index, array) {
			    if (e.authority == 'ROLE_ADMIN') {
				$rootScope.isAdmin = true;
				return true;
			    }
			    return false;
			});
		    };

		    authenticate();

		    $scope.credentials = {};
		    $scope.login = function() {
			authenticate($scope.credentials,
				function(authenticated) {
				    if (authenticated) {
					console.log("Login succeeded")
					$location.path("/front/home");
					$scope.error = false;
					$rootScope.authenticated = true;
				    } else {
					console.log("Login failed")
					$location.path("/front/login");
					$scope.error = true;
					$rootScope.authenticated = false;
				    }
				})
		    };

		    $scope.logout = function() {
			$http.post('/logout', {}).finally(function() {
			    $rootScope.authenticated = false;
			    $location.path("/front/login");
			});
		    }

		})

	.controller(
		'AuthorCtrl',
		function($rootScope, $scope, $http, Notification) {

		    // //$rootScope.hideAuthorMenu = true;
		    // console.log(hideAuthorMenu);

		    $scope.author = {};

		    $http.get('/rest/authors').success(function(data) {
			$scope.authors = data;
		    });

		    $scope.saveAuthor = function() {
			if ($scope.author.name == null) {
			    Notification.error('Data Error');

			} else {
			    $http.post('/rest/saveAuthor', $scope.author).success(
				    function(data, status, headers, config) {
					$scope.authors = data;
					$scope.clear();
				    });
			    Notification.success('Done!');
			}
		    };

		    $scope.deleteAuthor = function(author) {
			if (confirm("Delete!")) {
			    $http.post('/rest/deleteAuthor', author).success(
				    function(data, status, headers, config) {
					$scope.authors = data;
					$scope.clear();
					Notification.info('Deleted!');
				    })
			}
			;
		    };

		    $scope.editAuthor = function(author) {
			$scope.author = author;
		    };

		    $scope.clear = function() {
			$scope.author = null;
		    };
		})

	.controller('Error', function($rootScope, $scope, $http, $location) {

	});
