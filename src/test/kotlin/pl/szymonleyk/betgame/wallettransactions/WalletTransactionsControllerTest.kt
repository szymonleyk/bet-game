package pl.szymonleyk.betgame.wallettransactions

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import pl.szymonleyk.betgame.AccountNotFoundException
import reactor.core.publisher.Flux
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebFluxTest(WalletTransactionsController::class)
class WalletTransactionsControllerTest {

    @MockBean
    private lateinit var walletTransactionService: WalletTransactionService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `when account exists retrieve wallet transactions successfully`() {
        val accountId = 1
        val expectedTransactions = listOf(
            WalletTransactionData(LocalDate.now(), 100.0),
            WalletTransactionData(LocalDate.now().minusDays(1), -50.0)
        )

        given(walletTransactionService.retrieveWalletTransactions(accountId))
            .willReturn(Flux.fromIterable(expectedTransactions))

        val result = webClient.get().uri("/wallet-transactions/$accountId")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(WalletTransactionData::class.java)
            .returnResult()

        Assertions.assertThatList(result.responseBody).isEqualTo(expectedTransactions)
    }

    @Test
    fun `when account not exsists return 404 code and appropiate message`() {
        val invalidAccountId = -1

        given(walletTransactionService.retrieveWalletTransactions(invalidAccountId))
            .willReturn(Flux.error(::AccountNotFoundException))

        webClient.get().uri("/wallet-transactions/$invalidAccountId")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(String::class.java)
            .isEqualTo("[\"Account not found\"]")
    }

}
