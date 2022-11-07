# Installation and execution
## Windows
./mvnw.cmd spring-boot:build-image

docker run -d -p 8080:8080 applebags
## Linux
./mvnw spring-boot:build-image

docker run -d -p 8080:8080 applebags

# Testing the app

send the requests either using your browser or postman etc.
## CreateAppleBag
#### description
Create an applebag and add it to the application's list
#### arguments
| name      | type      | mandatory | description |
| --------- | --------- | --------- | ----------- |
| apples    | integer   | yes       | the amount of apples in the bag
| supplier  | string    | yes       | name of the supplier, must be one of: "Royal Gala", "Pink Lady", "Kanzi Apple", "Elstar Apples"
| packedOn  | date      | yes       | date the bag was packed on. must be in the format "dd-MM-yyyy"
| price     | float     | yes       | price of the bag
#### example:
http://localhost:8080/CreateAppleBag?apples=30&supplier=Pink%20Lady&packedOn=12-05-1999&price=5.20

## GetAppleBags
#### description
Get a given amount of applebags as JSON. If no amount is given, up to three are retrieved instead.
#### arguments
| name      | type      | mandatory | description |
| --------- | --------- | --------- | ----------- |
| amount    | integer   | no        | the amount of applebags to get
#### example:
http://localhost:8080/GetAppleBags?amount=5
