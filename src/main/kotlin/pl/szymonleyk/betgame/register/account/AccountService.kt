package pl.szymonleyk.betgame.register.account

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szymonleyk.betgame.register.AccountIdData
import pl.szymonleyk.betgame.register.AccountRequest
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun create(accountRequest: AccountRequest): Mono<AccountIdData> =
        Mono.just(accountRequest)
            .map(::mapToAccount)
            .flatMap(accountRepository::save)
            .map(::mapToAccountIdData)


    private fun mapToAccount(accountRequest: AccountRequest): Account =
        Account(
            username = accountRequest.username,
            name = accountRequest.name,
            surname = accountRequest.surname,
            balance = ENTRY_BALANCE
        )

    private fun mapToAccountIdData(account: Account): AccountIdData = AccountIdData(account.id!!)

    companion object {
        const val ENTRY_BALANCE = 1000L
    }

}