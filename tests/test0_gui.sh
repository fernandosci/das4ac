export CLIENTSH=client.sh
export SERVERSH=server.sh

export TEST=0

export SPARAMS="1 60 5 1000"

cd ..

sleep 6s && xterm -e sh ./client.sh "127.0.0.1 userlogin ${TEST} 1" &

for ((i=0; i<=0; i++)); do
sleep 5s && xterm -e sh ./client.sh "127.0.0.1 userlogin_$i ${TEST} 1" &
done

xterm -e sh ./server.sh $SPARAMS

