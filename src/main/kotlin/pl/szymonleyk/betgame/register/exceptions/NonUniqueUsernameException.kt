package pl.szymonleyk.betgame.register.exceptions

import pl.szymonleyk.betgame.BetGameException

class NonUniqueUsernameException : BetGameException("Username already used")