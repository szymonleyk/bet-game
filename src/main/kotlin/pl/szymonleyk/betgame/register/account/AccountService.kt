package pl.szymonleyk.betgame.register.account

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.register.AccountData
import pl.szymonleyk.betgame.register.AccountId
import pl.szymonleyk.betgame.wallettransactions.WalletTransactionService
import reactor.core.publisher.Mono

@Service
class AccountService(val accountRepository: AccountRepository, val walletTransactionService: WalletTransactionService) {
    fun create(accountData: AccountData): Mono<AccountId> = accountRepository.save(
        Account(
            username = accountData.username, name = accountData.name, surname = accountData.surname
        )
    ).flatMap { savedAccount ->
        walletTransactionService.addEntryBalance(savedAccount.id!!)
        Mono.just(AccountId(savedAccount.id!!))
    }
}