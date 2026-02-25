@echo off
chcp 65001 > nul
title Лаба по Spring - Таблица товаров
echo Сборка проекта...
call gradle clean build > build_log.txt 2>&1

echo Запуск приложения...
java -Dfile.encoding=UTF-8 -cp "app\build\classes\java\main;app\build\resources\main" ru.bsuedu.cad.lab.App

echo.
pause