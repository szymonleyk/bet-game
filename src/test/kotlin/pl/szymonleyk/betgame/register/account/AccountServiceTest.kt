package pl.szymonleyk.betgame.register.account

import org.h2.message.DbException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import pl.szymonleyk.betgame.UsernameAlreadyUsedException
import pl.szymonleyk.betgame.register.AccountIdData
import pl.szymonleyk.betgame.register.AccountRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class AccountServiceTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @InjectMocks
    private lateinit var accountService: AccountService

    @Test
    fun `create account successfully`() {
        val accountRequest = AccountRequest("username", "John", "Doe")
        val expectedAccount = Account(1, "username", "John", "Doe", 1000.0)
        val expectedAccountIdData = AccountIdData(1)

        given(accountRepository.save(ArgumentMatchers.any(Account::class.java))).willReturn(Mono.just(expectedAccount))

        val result = accountService.create(accountRequest)

        StepVerifier.create(result)
            .expectNext(expectedAccountIdData)
            .verifyComplete()
    }

    @Test
    fun `create account with existing username should throw UsernameAlreadyUsedException`() {
        // Arrange
        val accountRequest = AccountRequest("existingUsername", "John", "Doe")

        given(accountRepository.save(ArgumentMatchers.any())).willThrow(DbException::class.java)

        val result = accountService.create(accountRequest)

        // Assert
        StepVerifier.create(result)
            .expectError(UsernameAlreadyUsedException::class.java)
            .verify()
    }

    // Add more tests for other scenarios as needed
}
