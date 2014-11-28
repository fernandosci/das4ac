#!/bin/sh

rm -r -f tmp

export MYPATH1=`pwd`

export SERVER=AudictionSystemServer
export CLIENT=AudictionSystemClient
export LIB=AudictionSystemLib
export JAR=AuctionSystem.jar

export SRCPATH=uk/ac/gla/dcs/das4/i2120521/cw/remote
export PATHCLASS=tmp/class
export PATHBUILD=tmp/build

mkdir $PATHCLASS
mkdir $PATHBUILD

echo COMPILING .JAVA
find -name "*.java" > sources.txt
javac $PATHCLASS -d @sources.txt


cd $PATHCLASS

echo BUILDING $JAR
jar cvf ../../$PATHBUILD/$JAR $SRCPATH/commom/*.class $SRCPATH/server/*.class $SRCPATH/client/*.class

cd ../..

echo RUNNING SERVER
java -cp $MYPATH1/$PATHBUILD/$JAR -Djava.rmi.server.codebase=file:/$MYPATH1/$PATHBUILD/$JAR -Djava.security.policy=$MYPATH1/security.policy uk.ac.gla.dcs.das4.i2120521.cw.remote.server.ServerRunner

rm sources.txt