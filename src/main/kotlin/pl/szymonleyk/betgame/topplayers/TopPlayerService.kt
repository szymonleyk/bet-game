package pl.szymonleyk.betgame.topplayers

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.placebet.bet.BetRepository
import pl.szymonleyk.betgame.register.account.AccountRepository
import reactor.core.publisher.Flux

@Service
class TopPlayerService(val betRepository: BetRepository, val accountRepository: AccountRepository) {
    fun fetchTopPlayers(): Flux<TopPlayer> =
        betRepository.fetchTopPlayers()
}