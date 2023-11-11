package pl.szymonleyk.betgame.placebet

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.szymonleyk.betgame.placebet.bet.BetService
import reactor.core.publisher.Flux

@RestController
class PlaceBetController(val betService: BetService) {

    @PostMapping("/place-bet")
    @ResponseStatus(HttpStatus.CREATED)
    fun placeBet(@Valid @RequestBody placeBetRequest: PlaceBetRequest) =
        betService.placeBet(placeBetRequest)

    @GetMapping("/bets/{accountId}")
    fun retrieveBets(@PathVariable accountId:Int) : Flux<PlaceBetResponse> =
        betService.retriveBetsByAccount(accountId)

}