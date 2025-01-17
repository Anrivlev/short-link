Как пользоваться сервисом.

Сервис представляет из себя стандартное Java Spring приложение с Rest-API.
Для работы необходима SQL база данных.
Настроить подключение к БД и другие параметры приложения можно в конфигурационном файле application.properties.

Настройки
short-link.hours - максимальное число часов действия ссылки
short-link.usages - максимальное число использований ссылки

Какие команды поддерживаются
1. Получение идентификатора пользователя
   curl --location 'localhost:8080/uuid'

2. Получение сокращенной ссылки
   curl --location 'localhost:8080/get/?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68&url=https%3A%2F%2Fwww.google.ru%2F&usages=10&hours=24'    

3. Использование сокращенной ссылки
   curl --location 'localhost:8080/use/C?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68'    

4. Удаление сокращенной ссылки
   curl --location --request DELETE 'localhost:8080/delete/C?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68'

Как протестировать код
Для тестирования кода можно воспользоваться следующими командами:

1. Получить uuid (идентификатор пользователя)
   curl --location 'localhost:8080/uuid'

2. Получить сокращенную ссылку
   curl --location 'localhost:8080/get/?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68&url=https%3A%2F%2Fwww.google.ru%2F&usages=10&hours=24'

3. Использовать сокращенную ссылку
   curl --location 'localhost:8080/use/C?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68'

4. Удалить сокращенную ссылку
   curl --location --request DELETE 'localhost:8080/delete/C?uuid=bf17977f-bc3d-4335-9294-a6370fae3d68'