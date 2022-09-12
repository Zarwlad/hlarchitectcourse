
# Бэкенд для социальной сети

## Список переменных окружения

| Переменная  | Описание                              | Пример                                     |
|-------------|---------------------------------------|--------------------------------------------|
| DB_URL      | Хост подключения, включая базу данных | jdbc:mysql://localhost:3306/social_network |
| DB_USERNAME | Пользователь подключения              | root                                       |
| DB_PASSWORD | Пароль пользователя                   | 123456                                     |
| BASE_URL    | Путь к апи, по умолчанию /api         | /api                                       |


Пример запуска докера с переменными:
`sudo docker run -p 8887:8888 --env BASE_URL=/api --env DB_PASSWORD=some_pass --env DB_URL=jdbc:mysql://mysql:3306/social_network --env DB_USERNAME=root hlarchitectcourse:latest`



## API

Swagger-спецификация доступна по пути /swagger-ui/index.html (Пример: http://localhost:8080/swagger-ui/index.html)

Postman-коллекция доступна по пути /api/docs (Пример: http://localhost:8080/api/docs)



### Основные эндпоинты
*  **persons/register** - зарегистрировать нового пользователя. Метод доступен без аутентификации.
*  **persons/all** - Получить список анкет пользователей. На вход принимает offset - число записей, которое следует пропустить, и limit - размер страницы
*  **friends/person/{personId}/friend-requests/{friendId}/make** - отправить запрос на дружбу. PersonId - id автора запроса,  friendId - получатель запроса
*  **friends/person/{personId}/confirm-friendship/{friendId}** - подтвердить запрос. PersonId - id друга, пользователя, которому был направлен запрос, friendId - отправитель запроса

