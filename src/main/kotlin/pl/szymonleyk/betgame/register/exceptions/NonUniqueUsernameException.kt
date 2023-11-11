package pl.szymonleyk.betgame.register.exceptions

import pl.szymonleyk.betgame.config.BetGameException

class NonUniqueUsernameException : BetGameException("Username already used")