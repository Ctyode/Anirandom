# Anirandom
Anime nazism

Памятка себе: как развернуть проект

1) ставим 8 жаву

2) ставим грэдл 2.3.3

3) ставим монгодб, нпм

4) в нпм ставим свиталерт, жквери, бэконжс

5) пишем npm i gradle <названиебиблиотек> 
(по очереди все три перечисленных в пункте 4)

6) включаем монго 
(включение монго: mongod)
(если не работает - sudo mkdir -p /data/db, потом sudo mongod))

7) импортим туда файл data.json из папки с ресурсами
(импорт mongoimport --host=127.0.0.1 --db anirandom --collection animes --file data.json --jsonArray)

8) пишем в грэдле команды staticResources, build, run
