How to see the exception
------------------------

- mvn spring-boot:run
- curl -i -X POST -H "Content-Type:application/json" -d '{}' http://localhost:8080/units
- curl -i -X PATCH -H "Content-Type:application/json" -d '{"connectionStatus": {"status": "Connected"}}' http://localhost:8080/units/1
- curl -i -X PATCH -H "Content-Type:application/json" -d '{"connectionStatus": {"status": "Disconnected"}}' http://localhost:8080/units/1
