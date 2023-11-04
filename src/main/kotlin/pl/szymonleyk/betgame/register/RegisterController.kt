package pl.szymonleyk.betgame.register

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.szymonleyk.betgame.register.account.Account
import pl.szymonleyk.betgame.register.account.AccountService
import pl.szymonleyk.betgame.wallettransactions.WalletTransactionService
import reactor.core.publisher.Mono

@RestController
class RegisterController(val accountService: AccountService, val walletTransactionService: WalletTransactionService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody account: Account): Mono<Account> {
        return accountService.add(account)
            .flatMap { savedAccount ->
                walletTransactionService.addEntryBalance(savedAccount.id)
                Mono.just(savedAccount)
            }
    }

}