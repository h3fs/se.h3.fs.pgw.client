# Tritium Payment GateWay Client

##API

Detect available modules. This is used on boot to see witch modules are there.

    curl -X "GET http://pgw.labs.h3.se:8080/info/modules"

Create a new payment request. This statement will return the state of the payment.
including the id variable. Use this variable in future references.

    curl -X POST "http://pgw.labs.h3.se:8080/test/create?amount=100.00&currency=SEK"

Get the QR code as a picture.

    curl -X GET "http://pgw.labs.h3.se:8080/test/0/qr.png"

This command will wait for 1000ms for the payment to be resolved. 
The state of the payment will be returned. 

    curl -X GET "http://pgw.labs.h3.se:8080/test/0/waitfor?timeout=1000"

This will retrieve the status of a request.
    
    curl -X GET "http://pgw.labs.h3.se:8080/test/0/status"

This will cancel a pending payment request.
    
    curl -X PUT "http://pgw.labs.h3.se:8080/test/0/cancel"