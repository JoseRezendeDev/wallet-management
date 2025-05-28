for i in {1..10}; do
  curl --silent --location 'http://localhost:8080/transaction' \
    --header 'Content-Type: application/json' \
    --data '{"userId": 1, "amount": 10.0}' &
done
wait

for i in {1..2}; do
  curl --silent --location 'http://localhost:8080/transaction' \
    --header 'Content-Type: application/json' \
    --data '{"userId": 2, "amount": 10.0}' &
done
wait

for i in {1..5}; do
  curl --silent --location 'http://localhost:8080/transaction' \
    --header 'Content-Type: application/json' \
    --data '{"userId": 2, "amount": -10.0}' &
done
wait