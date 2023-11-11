package pl.szymonleyk.betgame.topplayers

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@WebFluxTest(TopPlayersController::class)
class TopPlayersControllerTest {

    @MockBean
    private lateinit var topPlayerService: TopPlayerService

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `when call then return list of top players`() {
        val expectedTopPlayers = listOf(
            TopPlayer("player1", 10),
            TopPlayer("player2", 8),
            TopPlayer("player3", 7)
        )

        given(topPlayerService.fetchTopPlayers())
            .willReturn(Flux.fromIterable(expectedTopPlayers))

        val result = webClient.get().uri("/top-players")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(TopPlayer::class.java)
            .returnResult()

        Assertions.assertThatList(result.responseBody).isEqualTo(expectedTopPlayers)
    }
}