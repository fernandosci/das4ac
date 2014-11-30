rm -r -f tmp

export MYPATH1=`pwd`
export SERVER="AuctionSystemServer"
export CLIENT="AuctionSystemClient"
export LIB="AuctionSystemLib"


export SRCPATH="uk/ac/gla/dcs/das4/i2120521/cw/remote"
export PATHCLASS="tmp/class"
export PATHBUILD="tmp/build"


mkdir -p $PATHCLASS/$SERVER
mkdir -p $PATHCLASS/$CLIENT
mkdir -p $PATHCLASS/$LIB
mkdir -p $PATHBUILD

echo COMPILING LIB
cd $LIB
find $MYPATH1/$LIB -name *.java > ../$LIB"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$LIB @$LIB"sources.txt"
rm $LIB"sources.txt"
cd $PATHCLASS/$LIB
jar cf $MYPATH1/$PATHBUILD/$LIB".jar" uk
cd $MYPATH1

echo COMPILING SERVER
cd $SERVER
find $MYPATH1/$SERVER -name *.java > ../$SERVER"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$SERVER -cp $MYPATH1/$PATHBUILD/$LIB".jar" @$SERVER"sources.txt"
rm $SERVER"sources.txt"
cd $PATHCLASS/$SERVER
jar cf $MYPATH1/$PATHBUILD/$SERVER".jar" uk
cd $MYPATH1



echo COMPILING CLIENT
cd $CLIENT
find $MYPATH1/$CLIENT -name *.java > ../$CLIENT"sources.txt"
cd ..
javac -d $MYPATH1/$PATHCLASS/$CLIENT -cp $MYPATH1/$PATHBUILD/$LIB".jar" @$CLIENT"sources.txt"
rm $CLIENT"sources.txt"
cd $PATHCLASS/$CLIENT
jar cf $MYPATH1/$PATHBUILD/$CLIENT".jar" uk
cd $MYPATH1

exit
