remember to add file: src/main/resources/application.properties

it is supposed to have database url.

Remember that on each app run because of some mongo db controller you will get error messages, app will work anyway :)

Example of what should be in application.properties:

mongo.url=database_link_with_authentication_data_ should_be_here

server=http://localhost:8080

debug=true //it will make display errors on start that a lot of beans do not work properly, ignore.

db.generate.enabled=false
