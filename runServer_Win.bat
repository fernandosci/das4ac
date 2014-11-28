@echo off

del /f tmp

REM src\uk\ac\gla\dcs\das4\i2120521\cw\remote
SET MYPATH=%~dp0
SET MYPATH1=%mypath:~0,-1%

SET SERVER=AudictionSystemServer
SET CLIENT=AudictionSystemClient
SET LIB=AudictionSystemLib
SET JAR=AuctionSystem.jar

SET SRCPATH=uk\ac\gla\dcs\das4\i2120521\cw\remote
SET PATHCLASS=tmp\class
SET PATHBUILD=tmp\build

mkdir %PATHCLASS%s
mkdir %PATHBUILD%

echo COMPILING .JAVA
dir /s /B *.java > sources.txt
javac %PATHCLASS% -d @sources.txt


cd %PATHCLASS%

echo BUILDING %JAR%
jar cvf ..\..\%PATHBUILD%\%JAR% %SRCPATH%\commom\*.class %SRCPATH%\server\*.class %SRCPATH%\client\*.class

cd ..\..

echo RUNNING SERVER
java -cp %MYPATH1%\%PATHBUILD%\%JAR% -Djava.rmi.server.codebase=file:/%MYPATH1%\%PATHBUILD%\%JAR% -Djava.security.policy=%MYPATH1%\security.policy uk.ac.gla.dcs.das4.i2120521.cw.remote.server.ServerRunner

REM echo BUILDING libAuction.jar
REM javac -cp %pathbuild%\libAuction.jar tmp\class\%srcpath%\client\*.class


REM find -name "*.java" > sources.txt
REM $ javac @sources.txt


REM del -f tmp

REM 
REM echo %mypath:~0,-1%

del sources.txt