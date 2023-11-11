package pl.szymonleyk.betgame.register.account

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : ReactiveCrudRepository<Account, Int>