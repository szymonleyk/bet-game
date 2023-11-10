package pl.szymonleyk.betgame.register.account

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock;
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.szymonleyk.betgame.register.AccountRequest
import pl.szymonleyk.betgame.register.AccountIdData
import pl.szymonleyk.betgame.wallettransactions.WalletTransactionService

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension::class)
class AccountServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var walletTransactionService: WalletTransactionService

    @InjectMocks
    private lateinit var accountService: AccountService

    @Test
    fun `Test create account`() {
        val accountRequest = AccountRequest("testUsername", "John", "Doe")

        Mockito.`when`(accountRepository.findByUsername(accountRequest.username))
            .thenReturn(Mono.empty())

        Mockito.`when`(accountRepository.save(Mockito.any(Account::class.java)))
            .thenReturn(Mono.just(Account(1, accountRequest.username, accountRequest.name, accountRequest.surname, 1000L)))

        Mockito.`when`(walletTransactionService.addEntryBalance(1))
            .thenReturn(Mono.empty())

        StepVerifier
            .create(accountService.create(accountRequest))
            .expectNext(AccountIdData(1))
            .verifyComplete()
    }
}
