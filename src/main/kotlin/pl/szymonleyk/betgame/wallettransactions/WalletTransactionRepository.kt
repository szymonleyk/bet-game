package pl.szymonleyk.betgame.wallettransactions

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface WalletTransactionRepository : ReactiveCrudRepository<WalletTransaction, Int> {
    fun findAllByAccountId(accountId: Int): Flux<WalletTransaction>
}