var myApp = angular.module('myApp', [
	'ngRoute'])
	.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider){
		
		$routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'homeController'});
		$routeProvider.when('/mget2', {templateUrl: 'partials/mget2.html', controller: 'mget2Controller'});
		$routeProvider.when('/about', {templateUrl: 'partials/about.html', controller: 'aboutController'});
		$routeProvider.otherwise({redirectTo:'/home'});

		$locationProvider.html5Mode({enabled: true, requireBase: false});
	}])