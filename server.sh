export MYPATH1=`pwd`
export SERVER="AuctionSystemServer"
export CLIENT="AuctionSystemClient"
export LIB="AuctionSystemLib"


export SRCPATH="uk/ac/gla/dcs/das4/i2120521/cw/remote"
export PATHBUILD="tmp/build"

export SERVERJAR=$PATHBUILD/$SERVER".jar"
export LIBJAR=$PATHBUILD/$LIB".jar"
export JAR="$SERVERJAR:$LIBJAR"



export CODEBASE="-Djava.rmi.server.codebase=file:/$MYPATH1/$SERVERJAR"
export SECURITYCERT="-Djava.security.policy=$MYPATH1/security.policy"
export TORUN=uk.ac.gla.dcs.das4.i2120521.cw.remote.server.ServerRunner
export PARAMS=""


echo RUNNING SERVER
java -cp $JAR $CODEBASE  $SECURITYCERT $TORUN $PARAMS