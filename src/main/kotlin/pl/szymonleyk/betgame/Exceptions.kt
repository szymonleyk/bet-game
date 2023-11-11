package pl.szymonleyk.betgame

import org.springframework.http.HttpStatus

open class BetGameException(message: String, val statusCode: HttpStatus) : RuntimeException(message)


class UsernameAlreadyUsedException : BetGameException("Username already used", HttpStatus.BAD_REQUEST)
class AccountNotFoundException : BetGameException("Account not found", HttpStatus.NOT_FOUND)
class NegativeBalanceException : BetGameException("Not enough funds in the account", HttpStatus.BAD_REQUEST)
