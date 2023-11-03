package pl.szymonleyk.betgame.register

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : ReactiveCrudRepository<Account, String> {
}