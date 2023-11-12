package pl.szymonleyk.betgame.wallettransactions

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.AccountNotFoundException
import reactor.core.publisher.Flux

@Service
class WalletTransactionService(val walletTransactionRepository: WalletTransactionRepository) {

    fun retrieveWalletTransactions(accountId: Int): Flux<WalletTransactionData> =
        walletTransactionRepository.findAllByAccountId(accountId)
            .switchIfEmpty(Flux.error(::AccountNotFoundException))
            .map { WalletTransactionData(it.transactionDate, it.amount) }

}