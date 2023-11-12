package pl.szymonleyk.betgame.placebet

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import pl.szymonleyk.betgame.placebet.bet.BetService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@WebFluxTest(PlaceBetController::class)
class PlaceBetControllerTest {

    @MockBean
    private lateinit var betService: BetService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `when place bet then return created status`() {
        val placeBetRequest = PlaceBetRequest(5, 3, 1)
        val expectedResponse = PlaceBetResponse(5, 3, true)

        given(betService.placeBet(placeBetRequest)).willReturn(Mono.just(expectedResponse))

        val result = webClient.post().uri("/place-bet")
            .bodyValue(placeBetRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(PlaceBetResponse::class.java)
            .returnResult()

        Assertions.assertThat(result.responseBody).isEqualTo(expectedResponse)
    }

    @Test
    fun `when retrieve bets then return list of bets`() {
        val accountId = 1
        val expectedBets = listOf(
            PlaceBetResponse(10, 1, true),
            PlaceBetResponse(5, 2, false),
            PlaceBetResponse(20, 5, true)
        )

        given(betService.retriveBetsByAccount(accountId))
            .willReturn(Flux.fromIterable(expectedBets))

        val result = webClient.get().uri("/bets/$accountId")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(PlaceBetResponse::class.java)
            .returnResult()

        Assertions.assertThatList(result.responseBody).isEqualTo(expectedBets)
    }

    @Test
    fun `when invalid bet value then return BadRequest(400)`() {
        val placeBetRequest = PlaceBetRequest(0, 3, 1)
        thenBadRequestExpected(placeBetRequest)
    }

    @Test
    fun `when invalid bet number less than 1 then return BadRequest(400)`() {
        val placeBetRequest = PlaceBetRequest(5, 0, 1)
        thenBadRequestExpected(placeBetRequest)
    }

    @Test
    fun `when invalid bet number greater than 10 then return BadRequest(400)`() {
        val placeBetRequest = PlaceBetRequest(5, 11, 1)
        thenBadRequestExpected(placeBetRequest)
    }

    @Test
    fun `when invalid account ID less than 1 then return BadRequest(400)`() {
        val placeBetRequest = PlaceBetRequest(5, 3, 0)
        thenBadRequestExpected(placeBetRequest)
    }

    private fun thenBadRequestExpected(placeBetRequest: PlaceBetRequest) {
        webClient.post().uri("/place-bet")
            .bodyValue(placeBetRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
    }
}