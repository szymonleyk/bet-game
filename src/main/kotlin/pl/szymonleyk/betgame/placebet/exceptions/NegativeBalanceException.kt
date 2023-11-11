package pl.szymonleyk.betgame.placebet.exceptions

import pl.szymonleyk.betgame.BetGameException

class NegativeBalanceException : BetGameException("Not enough funds in the account")