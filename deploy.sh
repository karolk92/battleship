#!/bin/sh

gradle clean build

java -jar build/libs/Battleship-0.0.1-SNAPSHOT.jar
