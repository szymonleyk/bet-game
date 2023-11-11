package pl.szymonleyk.betgame.wallettransactions

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szymonleyk.betgame.AccountNotFoundException
import reactor.core.publisher.Flux

@Service
class WalletTransactionService(val walletTransactionRepository: WalletTransactionRepository) {
    @Transactional
    fun addTransaction(walletTransaction: WalletTransaction) = walletTransactionRepository.save(walletTransaction)

    fun retrieveWalletTransactions(accountId: Int): Flux<WalletTransactionData> =
        walletTransactionRepository.findAllByAccountId(accountId)
            .switchIfEmpty(Flux.error(::AccountNotFoundException))
            .map { WalletTransactionData(it.transactionDate, it.amount) }

    companion object {
        const val ENTRY_BALANCE = 1000.0
    }
}