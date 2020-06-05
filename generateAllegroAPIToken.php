<?php

function getAccessToken(): String
{
    $authUrl = "https://allegro.pl/auth/oauth/token?grant_type=client_credentials";
    $clientId = "f64f5d2974974232be774faeeaaa0c05";
    $clientSecret = "4Qknch66KcQS2k77vOOoDleh4NsSHMT7w6xjTkXi1b8mQ8XrKEwzLxvENtDe2M4U";

    $redirect_uri = "https://apitester.com/";

     	$ch = curl_init();
 	curl_setopt($ch, CURLOPT_URL, $authUrl);
	curl_setopt($ch, CURLOPT_POST, TRUE);

    	curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
	curl_setopt($ch, CURLOPT_USERNAME, $clientId);
	curl_setopt($ch, CURLOPT_PASSWORD, $clientSecret);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch, CURLOPT_POSTFIELDS, array(
		'client_id' => $clientId,
		'client_secret' => $clientSecret,
		'redirect_uri' => $redirect_uri,
		'grant_type' => 'client_credentials'
	));

    $tokenResult = curl_exec($ch);
    $resultCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

#    echo $tokenResult;
#    echo $resultCode;

    if ($tokenResult === false || $resultCode !== 200) {
        exit ("Something went wrong");
    }

    $tokenObject = json_decode($tokenResult);

    return $tokenResult;
#    return $tokenObject->access_token;
}

function main()
{
    echo "access_token = ", getAccessToken();
}

main();
