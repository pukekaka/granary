myApp.controller('mget2Controller', ['$scope', function($scope){
	$scope.search = function(samples) {
  		//console.log(samples);
  		var scode = /[A-z]{2}[0-9]{6}[A-z]{5}[-][0-9]{6}/;
  		var md5 = /[a-fA-F\d]{32}/;
  		var samplelist = samples.split('\n');
  		
  		console.log(samplelist.length);

  		for (var i=0; i<samplelist.length; i++){

  			if(md5.test(samplelist[i])){

  			}

  			if(scode.test(samplelist[i])){

  			}



  		}

  }

  $scope.open = function(){
  	
  	


	console.log('Download');
}


}]);