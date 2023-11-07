package pl.szymonleyk.betgame.register.account

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AccountRepository : ReactiveCrudRepository<Account, Int> {
    fun findByUsername(username: String): Mono<Account>
}