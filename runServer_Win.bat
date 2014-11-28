@echo off

del /f /q tmp

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

mkdir %PATHCLASS%
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


del sources.txt