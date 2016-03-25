<?php

$host="127.0.0.1" ;
$port=18000;


if(!($sock = socket_create(AF_INET, SOCK_STREAM, 0)))
{
    $errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
     
    die("Couldn't create socket: [$errorcode] $errormsg \n");
}
 
echo "Socket created \n";
 
//Connect socket to remote server
if(!socket_connect($sock , $host , $port))
{
    $errorcode = socket_last_error();
    $errormsg = socket_strerror($errorcode);
     
    die("Could not connect: [$errorcode] $errormsg \n");
}
 
echo "Connection established \n";







function send($message){
	global $sock;
	
	if( ! socket_send ( $sock , $message , strlen($message) , 0))
	{
	    $errorcode = socket_last_error();
	    $errormsg = socket_strerror($errorcode);
	     
	    echo "Could not send data: [$errorcode] $errormsg \n";
	}
}

function recieve(){
	global $sock;
	
	if(socket_recv ( $sock , $buf , 2045 , MSG_WAITALL ) === FALSE)
	{
	    $errorcode = socket_last_error();
	    $errormsg = socket_strerror($errorcode);
	     
	    echo ("Could not receive data: [$errorcode] $errormsg \n");
	}
	
	echo $buff;
	 
	return $buf;
	
}



if (!is_resource($sock)) {
    exit("connection fail: ".$errnum." ".$errstr) ;
} else {
    
    send("joueur\n");
		
		
	
	send("add\n");
	
	sleep(2);
	
	send("remove\n");
    
}

?>