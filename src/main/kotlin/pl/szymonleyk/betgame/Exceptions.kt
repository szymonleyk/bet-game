package pl.szymonleyk.betgame

open class BetGameException(message: String) : RuntimeException(message)

class UsernameAlreadyUsedException : BetGameException("Username already used")
class AccountNotFoundException : BetGameException("Account not found")
class NegativeBalanceException : BetGameException("Not enough funds in the account")
