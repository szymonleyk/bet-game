package pl.szymonleyk.betgame.wallettransactions

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletTransactionRepository : ReactiveCrudRepository<WalletTransaction, Long>