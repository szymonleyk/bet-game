package pl.szymonleyk.betgame.topplayers

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.placebet.bet.BetRepository
import pl.szymonleyk.betgame.register.account.AccountRepository
import reactor.core.publisher.Flux

@Service
class TopPlayerService(val betRepository: BetRepository, val accountRepository: AccountRepository) {
    fun fetchTopPlayers(): Flux<TopPlayer> =
        betRepository.findAll()
            .groupBy { it.accountId }
            .flatMap { groupedFlux ->
                groupedFlux.filter { it.win }
                    .collectList()
                    .flatMap { winningBets ->
                        accountRepository.findById(groupedFlux.key()!!)
                            .map { account ->
                                TopPlayer(account.username, winningBets.size.toLong())
                            }
                    }
            }
            .sort{topPlayer1, topPlayer2 -> topPlayer2.totalWins.compareTo(topPlayer1.totalWins) }

}