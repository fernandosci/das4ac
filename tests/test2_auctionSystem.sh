export CLIENTSH=client.sh
export SERVERSH=server.sh

export TEST=1

export SPARAMS="1 4 5 1000"

cd ..

#CREATOR
export TEST=2
sleep 5s && xterm -e sh ./client.sh "127.0.0.1 CREATOR ${TEST} 1" &

#BIDDER
export TEST=3
sleep 5s && xterm -e sh ./client.sh "127.0.0.1 BIDDER ${TEST} 2" &

#LISTENER
export TEST=4
sleep 5s && xterm -e sh ./client.sh "127.0.0.1 LISTENER ${TEST} 1" &

#HISTORIAN
export TEST=5
sleep 5s && xterm -e sh ./client.sh "127.0.0.1 HISTORIAN ${TEST} 1" &


sh ./server.sh $SPARAMS

