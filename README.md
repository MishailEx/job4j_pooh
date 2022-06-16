[![Build Status](https://app.travis-ci.com/MishailEx/job4j_pooh.svg?branch=main)](https://app.travis-ci.com/MishailEx/job4j_pooh)
[![codecov](https://codecov.io/gh/MishailEx/job4j_pooh/branch/master/graph/badge.svg?token=NJQPS146QK)](https://codecov.io/gh/MishailEx/job4j_pooh)

### Описание
Приложение запускает Socket и ждет клиентов.
Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber).
Отправитель посылает запрос на добавление данных с указанием очереди и значением параметра. 
Сообщение помещается в конец очереди. Если очереди нет в сервисе, то создаеться новая и сообщение помещаеться в нее.
Получатель посылает запрос на получение данных с указанием очереди. Сообщение забирается из начала очереди и удаляется.
Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
Каждое сообщение в очереди может быть получено только одним получателем.

### Пример

#### Отправление данных в очередь text
````
curl -X POST -d "text=13" http://localhost:9000/queue/text 
````
#### Получение из очереди text
````
curl -X GET http://localhost:9000/queue/text
````