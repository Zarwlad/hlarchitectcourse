
# Бэкенд для социальной сети

## Пререквезиты
Требуется наличие базы данных MySql с созданной базой данных social_network

## Список переменных окружения

| Переменная  | Описание                              | Пример                                     |
|-------------|---------------------------------------|--------------------------------------------|
| DB_URL      | Хост подключения, включая базу данных | jdbc:mysql://localhost:3306/social_network |
| DB_USERNAME | Пользователь подключения              | root                                       |
| DB_PASSWORD | Пароль пользователя                   | 123456                                     |
| BASE_URL    | Путь к апи, по умолчанию /api         | /api                                       |


Пример запуска с maven:
* `mvn clean`
* `mvn build`

При запуске передать переменные.


Пример запуска докера с переменными:
```
docker build <путь к Dockerfile>
docker run -p 8887:8888 --env BASE_URL=/api --env DB_PASSWORD=some_pass --env DB_URL=jdbc:mysql://mysql:3306/social_network --env DB_USERNAME=root hlarchitectcourse:latest
```


## API

Swagger-спецификация доступна по пути /swagger-ui/index.html (Пример: http://localhost:8080/swagger-ui/index.html)

Postman-коллекция доступна по пути /api/docs (Пример: http://localhost:8080/api/docs)



### Основные эндпоинты
*  **persons/register** - зарегистрировать нового пользователя. Метод доступен без аутентификации.
*  **persons/all** - Получить список анкет пользователей. На вход принимает offset - число записей, которое следует пропустить, и limit - размер страницы
*  **friends/person/{personId}/friend-requests/{friendId}/make** - отправить запрос на дружбу. PersonId - id автора запроса,  friendId - получатель запроса
*  **friends/person/{personId}/confirm-friendship/{friendId}** - подтвердить запрос. PersonId - id друга, пользователя, которому был направлен запрос, friendId - отправитель запроса





# Задача 2 - добавление индекса
Сгенерирован 1 млн анкет через тест-план jmeter, находящийся в папке src/main/resources/course/issue2/testplangenerate1kkpersons.
Запрос показывает число уникальных записей в таблице:
```
SELECT count(DISTINCT name) AS unique_names,
       count(DISTINCT surname) AS unique_surnames,
       (SELECT count(DISTINCT name, surname) FROM persons) AS unique_person_names_surnames,
       count(*) AS total_rows
FROM persons;
```
69к уникальных сочетаний имен и фамилий, 142 уникальных имени, 1013 уникальных фамилии

| unique\_names | unique\_surnames | unique\_person\_names\_surnames | total\_rows |
|:--------------|:-----------------|:--------------------------------|:------------|
| 142           | 1013             | 69422                           | 1000685     |


Реализован эндоинт:
```
GET /api/person/by-name-and-surname
```
На вход в параметрах запроса принимает likeName, likeSurname и возвращает пагинированную страницу (задается offset, limit).
Запрос нечувствителен к регистру (можно передать "Ан", "ан", "АН" и т.п.).

Метод вызывает запрос:
```
SELECT *
FROM persons
         JOIN cities ON persons.city_id = cities.id
WHERE persons.name LIKE '?%'
  AND persons.surname LIKE '?%'
ORDER BY persons.id
LIMIT ? OFFSET ?;
```

Пример запроса:
```
curl -X 'GET' \
'http://localhost:8080/api/person/by-name-and-surname?likeName=%D0%B0%D0%BD&likeSurname=%D0%B7%D0%B0&limit=5&offset=0' \
-H 'accept: */*'
```
(также необходимо передать токен авторизации)

Пример ответа:

```
[
   {
      "id":619,
      "email":"email@email.com",
      "login":"9791498",
      "name":"Анна",
      "surname":"Захарова",
      "age":15,
      "gender":"FEMALE",
      "interestsDtoList":[
         {
            "id":94,
            "tag":"Оригами"
         }
      ],
      "cityDto":{
         "id":590,
         "name":"Мирный",
         "region":"Якутия"
      }
   },
   {
      "id":624,
      "email":"email@email.com",
      "login":"438299588",
      "name":"Анна",
      "surname":"Зайцева",
      "age":32,
      "gender":"FEMALE",
      "interestsDtoList":[
         {
            "id":99,
            "tag":"Открытки"
         }
      ],
      "cityDto":{
         "id":589,
         "name":"Миньяр",
         "region":"Челябинская область"
      }
   },
   {
      "id":866,
      "email":"email@email.com",
      "login":"445765244",
      "name":"Анна",
      "surname":"Завьялова",
      "age":12,
      "gender":"FEMALE",
      "interestsDtoList":[
         {
            "id":123,
            "tag":"Серфинг"
         }
      ],
      "cityDto":{
         "id":332,
         "name":"Ивантеевка",
         "region":"Московская область"
      }
   },
   {
      "id":2064,
      "email":"email@email.com",
      "login":"340500109",
      "name":"Анастасия",
      "surname":"Зайцева",
      "age":40,
      "gender":"FEMALE",
      "interestsDtoList":[
         {
            "id":112,
            "tag":"Предпринимательство"
         }
      ],
      "cityDto":{
         "id":197,
         "name":"Вязьма",
         "region":"Смоленская область"
      }
   },
   {
      "id":2068,
      "email":"email@email.com",
      "login":"820702421",
      "name":"Анастасия",
      "surname":"Захарова",
      "age":30,
      "gender":"FEMALE",
      "interestsDtoList":[
         {
            "id":111,
            "tag":"Плетение"
         }
      ],
      "cityDto":{
         "id":198,
         "name":"Вятские Поляны",
         "region":"Кировская область"
      }
   }
]
```



## Статистика вызовов БЕЗ индекса
В папке src/main/resources/course/issue2/testplanselectLikeName находится тест план jmeter для проверки эндпоинта.
Одновременных вызовов 1/10/100/1000.
Число итераций
* 1 вызов - 100 итераций.
* 10 параллельных вызовов - 100 итераций.
* 100 параллельных вызовов - 20 итераций.
* 1000 параллельных вызовов - 6 итераций.

| Label                                            | # Samples | Average | Min   | Max    | Std. Dev. | Error % | Throughput | Received KB/sec | Sent KB/sec | Avg. Bytes |
|--------------------------------------------------|-----------|---------|-------|--------|-----------|---------|------------|-----------------|-------------|------------|
| 1 User:Find by likeName and likeSurname          | 100       | 1165    | 1112  | 1281   | 26.48     | 0.000%  | .85710     | 145.53          | 0.23        | 173867.0   |
| 10 users:10 Find by likeName and likeSurname     | 1000      | 2567    | 1678  | 3235   | 240.95    | 0.000%  | 3.87126    | 657.31          | 1.02        | 173867.0   |
| 100 users:100 Find by likeName and likeSurname   | 2000      | 25170   | 7275  | 30955  | 2034.19   | 0.000%  | 3.93094    | 667.44          | 1.03        | 173867.0   |
| 1000 users:1000 Find by likeName and likeSurname | 6468      | 244184  | 26744 | 297627 | 51015.74  | 0.433%  | 3.77695    | 638.53          | 0.99        | 173116.3   |
| TOTAL                                            | 9568      | 170611  | 1112  | 297627 | 114422.40 | 0.293%  | 3.68527    | 623.90          | 0.97        | 173359.5   |


Максимальное время при 1 тысяче параллельных запросов 15 раз было превышено время ожидания коннекта в 300 секунд.

## Индекс
План запроса БЕЗ индекса
```
-> Limit: 1000 row(s)  (cost=2655.69 rows=111) (actual time=15.378..2826.166 rows=205 loops=1)
    -> Nested loop inner join  (cost=2655.69 rows=111) (actual time=15.371..2825.663 rows=205 loops=1)
        -> Filter: ((persons.`name` like 'да%') and (persons.surname like 'ти%'))  (cost=708.50 rows=111) (actual time=15.335..2822.480 rows=205 loops=1)
            -> Index scan on persons using PRIMARY  (cost=708.50 rows=20308) (actual time=0.069..1646.360 rows=1005687 loops=1)
        -> Single-row index lookup on cities using PRIMARY (id=persons.city_id)  (cost=0.36 rows=1) (actual time=0.008..0.009 rows=1 loops=205)
```
Выборка длится 2,8 секунды.

Стандартный текстовый индекс MySQL поддерживает выборки слева, либо необходимо использовать полнотекстовый поиск. 

Для текущего like('starts%') запроса подходит стандартный индекс, но полям name и surname одновременно.

Индексы по текстовому полю регистронезависимые, поэтому приведение к нижнему регистру не требуется.

Поля name и surname имеют длину 500, поэтому для создания индекса необходимо обрезать длину.

Максимальная длина name, surname сгенерированных записей - 10 по имени (например, Александра), 13 по surname (Константинова). 
Поэтому возьмем ограничение по длине строки 15 знаков.

Итоговый индекс описан в src/main/resources/db/changelog/db.changelog-1.3.sql:
```
CREATE INDEX persons_name_surname_i
    ON persons (name(15), surname(15));
```

План запроса для индексированного поиска:
```
-> Limit: 1000 row(s)  (cost=33692.04 rows=1000) (actual time=164.315..165.051 rows=205 loops=1)
    -> Sort: persons.id, limit input to 1000 row(s) per chunk  (cost=33692.04 rows=47232) (actual time=164.310..164.566 rows=205 loops=1)
        -> Filter: ((persons.`name` like 'да%') and (persons.surname like 'ти%'))  (cost=33692.04 rows=47232) (actual time=28.060..163.901 rows=205 loops=1)
            -> Index range scan on persons using persons_name_surname_i over ('да' <= name <= 'да􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿' AND 'ти' <= surname <= 'ти􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿􏿿')  (cost=33692.04 rows=47232) (actual time=0.051..129.816 rows=24755 loops=1)
```
| id | select\_type | table | partitions | type | possible\_keys | key | key\_len | ref | rows | filtered | Extra |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | SIMPLE | persons | null | range | persons\_name\_surname\_i | persons\_name\_surname\_i | 124 | null | 47232 | 11.11 | Using where; Using filesort |

Выборка произвелась за 210ms, скорость выросла в 24 раза.


### Результат нагрузочного теста С индексом

| Label                                            | # Samples | Average | Min   | Max    | Std. Dev. | Error % | Throughput | Received KB/sec | Sent KB/sec | Avg. Bytes |
|--------------------------------------------------|-----------|---------|-------|--------|-----------|---------|------------|-----------------|-------------|------------|
| 1 User:Find by likeName and likeSurname          | 100       | 671     | 502   | 771    | 26.48     | 0.000%  | 1.29702    | 220.22          | 0.34        | 173867.0   |
| 10 users:10 Find by likeName and likeSurname     | 1000      | 1094    | 994   | 1196   | 64.79     | 0.000%  | 4.95786    | 841.80          | 1.30        | 173867.0   |
| 100 users:100 Find by likeName and likeSurname   | 2000      | 13798   | 10394 | 15173  | 1108.58   | 0.000%  | 6.50872    | 1105.13         | 1.71        | 173867.0   |
| 1000 users:1000 Find by likeName and likeSurname | 6000      | 101204  | 19891 | 174064 | 49680.30  | 0.000%  | 5.58784    | 948.77          | 1.47        | 173867.0   |
| TOTAL                                            | 9100      | 92345   | 771   | 174064 | 54130.46  | 0.000%  | 5.63593    | 956.94          | 1.48        | 173867.0   |

В целом показатели улучшились более чем в 2 раза. Кроме того, ошибок чтения больше не возникает.

