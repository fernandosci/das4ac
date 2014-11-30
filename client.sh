export MYPATH1=`pwd`
export SERVER="AuctionSystemServer"
export CLIENT="AuctionSystemClient"
export LIB="AuctionSystemLib"


export SRCPATH="uk/ac/gla/dcs/das4/i2120521/cw/remote"
export PATHBUILD="tmp/build"

export CLIENTJAR=$PATHBUILD/$CLIENT".jar"
export LIBJAR=$PATHBUILD/$LIB".jar"
export JAR="$CLIENTJAR:$LIBJAR"



export CODEBASE="-Djava.rmi.server.codebase=file:/$MYPATH1/$CLIENTJAR"
export SECURITYCERT="-Djava.security.policy=$MYPATH1/security.policy"
export TORUN=uk.ac.gla.dcs.das4.i2120521.cw.remote.client.ClientRunner

if [ $# > 1 ]; then
	export HOST=$1
	export USERNAME=$2
	export OPT=$3
	export N=$4
else
	export HOST="127.0.0.1"
	export USERNAME="user"
	export OPT="1"
	export N="1"
fi

export PARAMS="$HOST $USERNAME $OPT $N"


echo RUNNING CLIENT
java -cp $JAR $CODEBASE  $SECURITYCERT $TORUN $PARAMS