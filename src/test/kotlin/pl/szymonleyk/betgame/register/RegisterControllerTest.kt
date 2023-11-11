package pl.szymonleyk.betgame.register

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import pl.szymonleyk.betgame.UsernameAlreadyUsedException
import pl.szymonleyk.betgame.register.account.AccountService
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@WebFluxTest(RegisterController::class)
class RegisterControllerTest {

    @MockBean
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `when valid accountRequest then register and return ID`() {
        val accountRequest = AccountRequest("sleyk1001", "Szymon", "Leyk")
        val expectedId = 1

        given(accountService.create(accountRequest)).willReturn(Mono.just(AccountIdData(expectedId)))

        webClient.post().uri("/register")
            .bodyValue(accountRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(AccountIdData::class.java)
            .isEqualTo(AccountIdData(expectedId))
    }

    @Test
    fun `when username duplicated then return BadRequest(400) with appropiate message`() {
        val accountRequest = AccountRequest("sleyk1001", "Szymon", "Leyk")

        given(accountService.create(accountRequest)).willThrow(UsernameAlreadyUsedException())

        webClient.post().uri("/register")
            .bodyValue(accountRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .isEqualTo("[\"Username already used\"]")
    }

    @Test
    fun `when username length is greather than 25 then return BadRequest(400)`() {
        val accountRequest = AccountRequest("sleysdjflalkllkjdlkfjalskd", "Szymon", "Leyk")
        thenBedRequestExpected(accountRequest)
    }

    @Test
    fun `when username length is less than 5 then return BadRequest(400)`() {
        val accountRequest = AccountRequest("1233", "Szymon", "Leyk")
        thenBedRequestExpected(accountRequest)
    }

    @ParameterizedTest(name = "when: username({0}), name({1}), surname({2})")
    @CsvSource(
        "'    ', 'Szymon', 'Leyk'",
        "'sleyk123', '  ', 'Leyk'",
        "'sleyk123', 'Szymon', ' '",
    )
    fun `when any data is blank then return BadRequest`(username: String, name: String, surname: String) {
        val accountRequest = AccountRequest(username, name, surname)
        thenBedRequestExpected(accountRequest)
    }

    private fun thenBedRequestExpected(accountRequest: AccountRequest) {
        webClient.post().uri("/register")
            .bodyValue(accountRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
    }

}