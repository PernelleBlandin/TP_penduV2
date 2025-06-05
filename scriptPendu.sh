#!bin/bash

export PATH_FX=/usr/share/openjfx/lib/

javac --module-path $PATH_FX --add-modules javafx.controls -d bin src/*.java
java -cp bin:img --module-path $PATH_FX --add-modules javafx.controls Pendu

#javadoc -d doc --module-path $PATH_FX --add-modules javafx.controls src/*.java

#javac --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls -d bin src/*.java
#java -cp bin:img --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls AppliPlusieursFenetres