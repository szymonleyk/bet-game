# Bet Game
The game allow players to bet on a randomly generated number and win or lose based on the odds of their bet.

Tech stack:
* Kotlin 1.8.22
* Spring Boot 3.1.5
* M2 database
* Open JDK 17

### How to run
Fetch repo to your local machine
```shell
git clone git@github.com:szymonleyk/bet-game.git
```

Run tests
```shell
mvn clean test
```

Build project
```shell
mvn clean package
```

Create docker image
```shell
docker build -t bet-game-image .
```

Create docker container
```shell
docker run -p 8080:8080 bet-game-image
```

### Documentation
Open swagger-ui and use as you wish
```shell
http://localhost:8080/swagger-ui
```

### Use cases
Register account
```shell
curl -X 'POST' \
  'http://localhost:8080/register' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "snowak123",
  "name": "Stefan",
  "surname": "Nowak"
}'
```
Place bet
```shell
curl -X 'POST' \
  'http://localhost:8080/place-bet' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "betValue": 1,
  "betNumber": 10,
  "accountId": 1
}'
```
Retrieve bets for account
```shell
curl -X 'GET' \
  'http://localhost:8080/bets/1' \
  -H 'accept: */*'
```
Get all transactions for account
```shell
curl -X 'GET' \
  'http://localhost:8080/wallet-transactions/1' \
  -H 'accept: */*'
```
Fetch top players sorted by wins
```shell
curl -X 'GET' \
  'http://localhost:8080/top-players' \
  -H 'accept: */*'
```