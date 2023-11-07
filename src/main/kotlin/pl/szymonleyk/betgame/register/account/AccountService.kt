package pl.szymonleyk.betgame.register.account

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.register.AccountData
import pl.szymonleyk.betgame.register.AccountId
import pl.szymonleyk.betgame.wallettransactions.WalletTransactionService
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val walletTransactionService: WalletTransactionService
) {
    fun create(accountData: AccountData): Mono<AccountId> =
        validate(accountData)
            .then(accountRepository.save(dtoToAccount(accountData)))
            .flatMap { savedAccount ->
                walletTransactionService.addEntryBalance(savedAccount.id!!)
                Mono.just(AccountId(savedAccount.id!!))
            }

    private fun validate(accountData: AccountData): Mono<AccountId> =
        accountRepository.findByUsername(accountData.username)
            .flatMap { Mono.error(NonUniqueUsernameException()) }

    private fun dtoToAccount(accountData: AccountData): Account {
        return Account(
            username = accountData.username,
            name = accountData.name,
            surname = accountData.surname
        )
    }

}