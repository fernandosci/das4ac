@echo off

REM src\uk\ac\gla\dcs\das4\i2120521\cw\remote

set srcpath=src\uk\ac\gla\dcs\das4\i2120521\cw\remotec
set pathclass=tmp\class
set pathbuild=tmp\class

mkdir pathclasslib

dir /s /B *.java > sources.txt
javac -d %pathclasslib% @sources.txt

jar cvf libAuction.jar compute\*.class


REM find -name "*.java" > sources.txt
REM $ javac @sources.txt


REM del -f tmp

REM SET mypath=%~dp0
REM echo %mypath:~0,-1%