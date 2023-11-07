package pl.szymonleyk.betgame.register.account

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock;
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.szymonleyk.betgame.register.AccountData
import pl.szymonleyk.betgame.register.AccountId
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
        val accountData = AccountData("testUsername", "John", "Doe")

        Mockito.`when`(accountRepository.findByUsername(accountData.username))
            .thenReturn(Mono.empty())

        Mockito.`when`(accountRepository.save(Mockito.any(Account::class.java)))
            .thenReturn(Mono.just(Account(1, accountData.username, accountData.name, accountData.surname)))

        Mockito.`when`(walletTransactionService.addEntryBalance(1))
            .thenReturn(Mono.empty())

        StepVerifier
            .create(accountService.create(accountData))
            .expectNext(AccountId(1))
            .verifyComplete()
    }
}
