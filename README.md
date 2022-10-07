# yourCodeReview

Ссылка с описанием задания:
https://github.com/avito-tech/msg-backend-trainee-assignment

Стэк: Spring Boot, java 11, PostgresSQL, Junit, Mockito

Инструкция по запуску: 

Необходимо в файле application.properties, указать данные для локальной Postgres СУБД.
В командной строке указываем путь до папки с проектом "cd путь до папки проекта".
Далее если Maven установлен на системе, то вводим команду "mvn package", если нет то "mvnw package".
Ждем создания Jar файла.
Далее переходим в папку target "cd target/" и запускаем приложение командой "java -jar chat-0.0.1-SNAPSHOT.jar".

Примеры запросов: 

Users: 

http://localhost:9000/users/add
    
    {
        "username" : "username"
    }

Chats:

http://localhost:9000/chats/add
    
    {
        "name" : "name",
        "users": [1, 2]
    }

http://localhost:9000/chats/add

    {
        "userId": 1
    }

Messages:
http://localhost:9000/messages/add

    {
        "chatId": 1,
        "author": 1, 
        "text": "text"
    }

http://localhost:9000/messages/get

    {
        "id": 1
    }






