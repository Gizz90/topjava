####getAllMeals
curl http://localhost:8080/topjava/rest/meals

####getBetween
curl http://localhost:8080/topjava/rest/meals/filter??startDate=2015-05-30&startTime=20:00&endDate=2015-05-31&endTime=14:00

####getMeal
curl http://localhost:8080/topjava/rest/meals/100005

####deleteMeal
curl -X DELETE http://localhost:8080/topjava/rest/meals/100006

####createNewMeal
curl -X POST -H "Content-Type: application/json" -d '{"dateTime":"2015-05-30T12:00:00","description":"NewMeal","calories":1500}' http://localhost:8080/topjava/rest/meals

####updateMeal
curl -X PUT -H "Content-Type: application/json" -d '{"dateTime":"2015-05-31T16:00:00","description":"Полдник","calories":1500}' http://localhost:8080/topjava/rest/meals/100005

####adminGetAll
curl http://localhost:8080/topjava/rest/admin/users

####adminGetByEmail
curl http://localhost:8080/topjava/rest/admin/users/by?email=new2@yandex.ru

####adminCreate
curl -X POST -H "Content-Type: application/json" -d '{"name": "New2","email": "new2@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' http://localhost:8080/topjava/rest/admin/users

####adminGet
curl http://localhost:8080/topjava/rest/admin/users/100000

####adminUpdate
curl -X PUT -H "Content-Type: application/json" -d '{"name": "UserUpdated","email": "user@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' http://localhost:8080/topjava/rest/admin/users/100000
