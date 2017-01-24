var express = require('express');
var path = require('path');
var app = express();
var port = 5000;
var http = require('http');
var https = require('https');
var fs = require('fs');

app.set('view engine', 'ejs');
app.set('views', path.resolve(__dirname, 'client', 'views'));

app.use(express.static(path.resolve(__dirname, 'client')));

app.get('/', function(req, res){
    res.render('index.ejs');
});

app.get('/download', function(req, res){
    var file = fs.createWriteStream("zip_files/testfile");
    //var request = http.get("https://download.jboss.org/drools/release/5.1.0.33002.M2/drools-5.1.0.M2-guvnor.zip", function(response) {
    //	response.pipe(file);
    //});
    var request = https.get("https://download.jboss.org/drools/release/5.1.0.33002.M2/drools-5.1.0.M2-guvnor.zip", function(response) {
        response.pipe(file);
    });

})

app.listen(port, function(){
    console.log('SERVER RUNNING.... PORT: '+port);
})