package pl.szymonleyk.betgame.topplayers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class TopPlayersController(val topPlayerService: TopPlayerService) {
    @GetMapping("/top-players")
    fun topPlayers() : Flux<TopPlayer> = topPlayerService.fetchTopPlayers()
}

data class TopPlayer(val username: String, val totalWins: Long)