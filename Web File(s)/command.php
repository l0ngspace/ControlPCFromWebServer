<?php
$type = $_GET['action'];
 
if($type == "SHUTDOWN")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"SHUTDOWN");
fclose($file); 
}
else if($type == "RESTART")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"RESTART");
fclose($file); 
}
else if ($type == "STANDBY")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"STANDBY");
fclose($file); 
}
else if ($type == "HIBERNATE")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"HIBERNATE");
fclose($file); 
}
else if ($type == "NULL")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"NULL");
fclose($file); 
}else if ($type == "DELETEMYSELF")
{
$file = fopen('control_my_pc.txt', 'w');
fwrite($file,"DELETEMYSELF");
fclose($file); 
}
?>