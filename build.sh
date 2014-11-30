rm -r -f tmp

export MYPATH1=`pwd`
export SERVER="AudictionSystemServer"
export CLIENT="AudictionSystemClient"
export LIB="AudictionSystemLib"


export SRCPATH="uk/ac/gla/dcs/das4/i2120521/cw/remote"
export PATHCLASS="./tmp/class"
export PATHBUILD="./tmp/build"


mkdir -p $PATHCLASS/$SERVER
mkdir -p $PATHCLASS/$CLIENT
mkdir -p $PATHCLASS/$LIB
mkdir -p $PATHBUILD

echo COMPILING LIB
cd $LIB
find $MYPATH1/$LIB -name *.java > ../$LIB"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$LIB @$LIB"sources.txt"
cd $LIB/src
jar cf $MYPATH1/$PATHBUILD/$LIB".jar" uk
cd ../..

echo COMPILING SERVER
cd $SERVER
find $MYPATH1/$SERVER -name *.java > ../$SERVER"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$SERVER -cp $MYPATH1/$PATHBUILD/$LIB".jar" @$SERVER"sources.txt"
cd $SERVER/src
jar cf $MYPATH1/$PATHBUILD/$SERVER".jar" uk
cd ../..



echo COMPILING CLIENT
cd $CLIENT
find $MYPATH1/$CLIENT -name *.java > ../$CLIENT"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$CLIENT -cp $MYPATH1/$PATHBUILD/$LIB".jar" @$CLIENT"sources.txt"
cd $CLIENT/src
jar cf $MYPATH1/$PATHBUILD/$CLIENT".jar" uk
cd ../..

exit


jar cvf ../../$PATHBUILD/$JAR $SRCPATH/client/*.class


echo COMPILING .JAVA
cd ../$SERVER
find $MYPATH1/$SERVER -name *.java > ../$SERVER"sources.txt"
cd ../$CLIENT
find $MYPATH1/$CLIENT -name *.java > ../$CLIENT"sources.txt"


javac -d $MYPATH1/$PATHCLASS/$SERVER @$SERVER"sources.txt"
javac -d $MYPATH1/$PATHCLASS/$CLIENT @$CLIENT"sources.txt"
javac -d $MYPATH1/$PATHCLASS/$LIB @$LIB"sources.txt"

exit


cd $PATHCLASS
echo BUILDING $JAR
jar cvf ../../$PATHBUILD/$JAR $SRCPATH/commom/*.class $SRCPATH/server/*.class $SRCPATH/client/*.class
cd ../..

#rm sources.txt

echo RUNNING SERVER
java -cp $MYPATH1/$PATHBUILD/$JAR -Djava.rmi.server.codebase=file:/$MYPATH1/$PATHBUILD/$JAR -Djava.security.policy=$MYPATH1/security.policy uk.ac.gla.dcs.das4.i2120521.cw.remote.server.ServerRunner