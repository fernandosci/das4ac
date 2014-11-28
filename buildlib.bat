@echo off

del tmp -f

REM src\uk\ac\gla\dcs\das4\i2120521\cw\remote
SET mypath=%~dp0
SET mypath1=%mypath:~0,-1%

SET server=AudictionSystemServer
SET client=AudictionSystemClient
SET lib=AudictionSystemLib

echo %mypath%
echo %mypath1%

SET srcpath=src\uk\ac\gla\dcs\das4\i2120521\cw\remote
SET pathclass=tmp\class
SET pathbuild=tmp\build

mkdir %pathclass%\src
mkdir %pathbuild%

echo COMPILING .JAVA
dir /s /B *.java > sources.txt

javac -d %pathclass%\src @sources.txt

echo BUILDING libAuction.jar
jar cvf %pathbuild%\libAuction.jar tmp\class\%srcpath%\commom\*.class

echo BUILDING ServerRunner.jar
javac -cp "%mypath1%\%pathbuild%\libAuction.jar" "tmp\class\%srcpath%\server\ServerRunner.class"

REM echo BUILDING libAuction.jar
REM javac -cp %pathbuild%\libAuction.jar tmp\class\%srcpath%\client\*.class


REM find -name "*.java" > sources.txt
REM $ javac @sources.txt


REM del -f tmp

REM 
REM echo %mypath:~0,-1%

del sources.txt