#!/bin/bash
maxIterations=10
url="google.de"
secondsToWait=1

iteration=0;
while [ $iteration -lt $maxIterations ]
do
    ((++iteration))
    httpStatusCode=`curl -sL -w "%{http_code}\\n" "$url" -o /dev/null`
    echo "$httpStatusCode"
    if [ "x$httpStatusCode" = "x200" ]
    then
        break
    fi
    echo "endpoint $url did not respond with status 200. Retrying in 1 second... ($iteration of $maxIterations)"
    sleep $secondsToWait
done

if [ $iteration -eq $maxIterations ]
then
    timeout=$(expr $maxIterations \* $secondsToWait)
    echo "$url did not respond with Status 200 within the configured timeout of $timeout seconds."
    exit -1
fi
exit 0
