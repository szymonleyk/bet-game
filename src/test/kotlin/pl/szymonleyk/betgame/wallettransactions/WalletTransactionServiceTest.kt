package pl.szymonleyk.betgame.wallettransactions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
@ExtendWith(SpringExtension::class)
class WalletTransactionServiceTest {
    @Mock
    private lateinit var walletTransactionRepository: WalletTransactionRepository

    @InjectMocks
    private lateinit var walletTransactionService:WalletTransactionService

    @Test
    fun `Test addEntryBalance`() {
        val accountId = 1
        val expectedTransaction = WalletTransaction(1, LocalDate.now(), WalletTransactionService.ENTRY_BALANCE, accountId)

        given(walletTransactionRepository.save(Mockito.any(WalletTransaction::class.java)))
            .willReturn(Mono.just(expectedTransaction))

        StepVerifier.create(walletTransactionService.addEntryBalance(accountId))
            .expectNext(expectedTransaction)
            .verifyComplete()
    }
}