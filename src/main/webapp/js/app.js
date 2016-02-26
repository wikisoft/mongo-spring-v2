var app = angular.module('flapperNews', [ 'ui.router', 'ui-notification' ]);

// app.factory('posts', [
// '$http',
// function($http) {
//	    
// var o = {
// posts : []
// };
//
// o.getAll = function() {
// return $http.get('/posts').success(function(data) {
// // console.log('data '+data);
// angular.copy(data, o.posts);
// });
// };
//
// o.create = function(post) {
// return $http.post('/posts', post).success(function(data) {
// o.posts.push(data);
// });
// };
//
// o.upvote = function(post) {
// return $http.put('/posts/' + post._id + '/upvote').success(
// function(data) {
// post.upvotes += 1;
// });
// };
//
// o.get = function(id) {
// return $http.get('/posts/' + id).then(function(res) {
// return res.data;
// });
// };
//
// o.addComment = function(id, comment) {
// return $http.post('/posts/' + id + '/comments', comment);
// };
//
// o.upvoteComment = function(post, comment) {
// return $http.put(
// '/posts/' + post._id + '/comments/' + comment._id
// + '/upvote').success(function(data) {
// comment.upvotes += 1;
// });
// };
//
// return o;
// } ]);

app.config([
	'$stateProvider',
	'$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {

	    $stateProvider.state('home', {
		url : '/home',
		templateUrl : '/views/home.html',
		controller : 'MainCtrl'
	    // resolve : {
	    // postPromise : [ 'posts', function(posts) {
	    // return posts.getAll();
	    // } ]
	    // }
	    }).state(
		    'posts',
		    {
			url : '/posts/{id}',
			templateUrl : '/views/posts.html',
			controller : 'PostsCtrl',
			resolve : {
			    post : [ '$stateParams', 'posts',
				    function($stateParams, posts) {
					return posts.get($stateParams.id);
				    } ]
			}
		    });

	    $urlRouterProvider.otherwise('home');

	} ]);

app.controller('MainCtrl', function($scope, $http, Notification) {

    $scope.post = {};
    $scope.isEdit = false;
    
    $scope.selectedAuthor = {};

    $scope.authors = [];
    $http.get('/authors').success(function(data) {
	$scope.authors = data;
    });

    // $scope.author = $scope.author[1];

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
	$http.post('/upvotePost/'+up, post).success(
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
	console.log(post);
	$scope.isEdit = true;
	$scope.post.id = post.id;
	$scope.post.title = post.title;
	$scope.post.body = post.body;
	$scope.post.author = post.author.name;
	//$scope.post.author = post.author;
    };

    $scope.clearPost = function() {
	$scope.post.id = null;
	$scope.post.title = null;
	$scope.post.body = null;
	$scope.post.author = null;
	$scope.isEdit = false;
    };

    // $http.get('/taskList').success(function(data) {
    // $scope.tasks = data;
    // });
    //
    // $scope.posts = posts.posts;
    //
    // $scope.addPost = function() {
    // console.log("add post title " + $scope.title);
    // console.log("add post link " + $scope.link);
    //
    // if (!$scope.title || $scope.title === '') {
    // return;
    // }
    // posts.create({
    // title : $scope.title,
    // link : $scope.link,
    // });
    // $scope.title = '';
    // $scope.link = '';
    // };

});

app.controller('PostsCtrl', [ '$scope', 'post', 'posts', '$stateParams',
	function($scope, post, posts, $stateParams) {

	    $scope.post = post;

	    $scope.addComment = function() {
		console.log("add commment body " + $scope.body);
		if ($scope.body === '') {
		    return;
		}
		posts.addComment(post._id, {
		    body : $scope.body,
		    author : 'user',
		}).success(function(comment) {
		    $scope.post.comments.push(comment);
		});
		$scope.body = '';
	    };

	    $scope.incrementUpvotes = function(comment) {
		posts.upvoteComment(post, comment);
	    };

	} ]);