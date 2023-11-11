package pl.szymonleyk.betgame.wallettransactions

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import pl.szymonleyk.betgame.AccountNotFoundException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class WalletTransactionService(val walletTransactionRepository: WalletTransactionRepository) {
    @Transactional(propagation = Propagation.REQUIRED)
    fun addTransaction(walletTransaction: WalletTransaction) = walletTransactionRepository.save(walletTransaction)

    @Transactional(propagation = Propagation.REQUIRED)
    fun addEntryBalance(accountId: Int) = addTransaction(
        WalletTransaction(
            transactionDate = LocalDate.now(), amount = ENTRY_BALANCE, accountId = accountId
        )
    )

    fun retrieveWalletTransactions(accountId: Int): Flux<WalletTransactionData> =
        walletTransactionRepository.findAllByAccountId(accountId)
            .switchIfEmpty(Mono.error(::AccountNotFoundException))
            .map { walletTransaction ->
                WalletTransactionData(walletTransaction.transactionDate, walletTransaction.amount)
            }

    companion object {
        const val ENTRY_BALANCE = 1000.0
    }
}