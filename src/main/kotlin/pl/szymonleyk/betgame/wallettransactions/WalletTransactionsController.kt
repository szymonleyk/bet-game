package pl.szymonleyk.betgame.wallettransactions

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class WalletTransactionsController(private val walletTransactionService: WalletTransactionService) {

    @GetMapping("/wallet-transactions/{accountId}")
    fun retrieveWalletTransactions(@PathVariable accountId: Int) : Flux<WalletTransactionData> = walletTransactionService.retrieveWalletTransactions(accountId)
}