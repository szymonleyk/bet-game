package pl.szymonleyk.betgame.placebet

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceBetController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun placeBet(@RequestBody placeBetRequest: PlaceBetRequest) {

    }
}