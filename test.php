<?php
// require 'vendor/autoload.php';

// $result = new WhichBrowser\Parser(getallheaders());
// print_r($result->toString());

// $uaParser = new UAParser\UAParser();
// $result =  $uaParser->parse('Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20130406 Firefox/23.0.1');


// $db = new SQLite3('test.db', SQLITE3_OPEN_CREATE | SQLITE3_OPEN_READWRITE);

// if ($db == false){
// 	die ('Unable to open database');
// } else {
// 	echo 'Database created.';
// }

// echo "<br>";

// $cu_time = date("Y-m-d H:i:s");
// echo $cu_time;

// $testdata = 'test value';
// $testid = '2';

// $statement = $db->prepare('INSERT INTO "tmp_table" ("data") VALUES (:td)');
// $statement->bindValue(':td', $testdata);
// $statement->execute(); 

// echo "Insert perfect<br>";

// $statement2 = $db->prepare('SELECT * FROM "tmp_table" WHERE "data" = ?');
// $statement2->bindValue(1, $testid);
// $result = $statement2->execute();
// // echo $db->LastErrorMsg();

// print_r($result->fetchArray(SQLITE3_ASSOC));

// $statement2 = $db->prepare('UPDATE "tmp_table" SET "data" = ? WHERE "no" = ?');
// $statement2->bindValue(1, $testdata);
// $statement2->bindValue(2, $testid);
// $result = $statement2->execute();
// echo $db->LastErrorMsg();

// print_r($result->fetchArray(SQLITE3_ASSOC));


// $db->close();
// unset($db);
?>