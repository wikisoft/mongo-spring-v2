var app = angular.module('flapperNews',
	[ 'ui.router', 'ui-notification', 'ngResource' ])

.config(
	[ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

		    $stateProvider.state('home', {
			url : '/home',
			templateUrl : '/views/home.html',
			controller : 'MainCtrl'
		    // resolve : {
		    // postPromise : [ 'posts', function(posts)
		    // {
		    // return posts.getAll();
		    // } ]
		    // }
		    }).state('posts', {
			url : '/{id}',
			templateUrl : '/views/post.html',
			controller : 'PostCtrl'
		    // resolve : {
		    // post : [
		    // '$stateParams',
		    // 'posts',
		    // function(
		    // $stateParams,
		    // posts) {
		    // return posts
		    // .get($stateParams.id);
		    // } ]
		    // }
		    });

		    $urlRouterProvider.otherwise('home');

		} ])
.controller(
	'MainCtrl',
	function($scope, $http, Notification) {

	    $scope.post = {};
	    $scope.isEdit = false;

	    $scope.authors = [];
	    $http.get('/authors').success(function(data) {
		$scope.authors = data;
	    });

	    $http.get('/posts').success(function(data) {
		$scope.posts = data;
	    });

	    $scope.savePost = function() {
		if ($scope.post.title == null || $scope.post.body == null) {
		    Notification.error('Data Error');

		} else {
		    $http.post('/savePost', $scope.post).success(
			    function(data, status, headers, config) {
				$scope.posts = data;
				$scope.clearPost();
				$scope.isEdit = false;
			    });
		    Notification.success('Done!');
		}
	    };

	    $scope.upvote = function(post, up) {
		$http.post('/upvotePost/' + up, post).success(
			function(data, status, headers, config) {
			    $scope.posts = data;
			});
	    };

	    $scope.deletePost = function(post) {
		if (confirm("Delete!")) {
		    $http.post('/deletePost', post).success(
			    function(data, status, headers, config) {
				$scope.posts = data;
				$scope.clearPost();
				Notification.info('Deleted!');
				$scope.isEdit = false;
			    })
		}
		;
	    };

	    $scope.editPost = function(post) {
		$scope.isEdit = true;
		$scope.post.id = post.id;
		$scope.post.title = post.title;
		$scope.post.body = post.body;
		$scope.post.author = post.author;
	    };

	    $scope.clearPost = function() {
		$scope.post.id = null;
		$scope.post.title = null;
		$scope.post.body = null;
		$scope.post.author = null;
		$scope.isEdit = false;
	    };

	})

.controller(
	'PostCtrl',
	function($scope, $stateParams, $http, $resource, Notification) {

	    $scope.post = {};
	    $scope.comment = {};
	    $scope.comment.rating = 1;
	    $http.get('/posts/' + $stateParams.id).success(function(data) {
		$scope.post = data;
	    });

	    $http.get('/comments/'+ $stateParams.id).success(function(data) {
		$scope.comments = data;
	    });

	    $scope.saveComment = function() {
		$scope.comment.post = $scope.post;
		$http.post('/saveComment', $scope.comment).success(
			function(data, status, headers, config) {
			    $scope.comments = data;
			    $scope.clearComment();
			});
		Notification.success('Done!');
	    };

	    $scope.clearComment = function() {
		$scope.comment.rating = 1;
		$scope.comment.comment = null;
		$scope.commentForm.comment.$pristine= true;
	    }

	});