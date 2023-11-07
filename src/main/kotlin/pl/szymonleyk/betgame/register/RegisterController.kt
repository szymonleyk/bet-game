package pl.szymonleyk.betgame.register

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.szymonleyk.betgame.register.account.AccountService
import reactor.core.publisher.Mono

@RestController
class RegisterController(val accountService: AccountService) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody accountData: AccountData) : Mono<AccountId> = accountService.create(accountData)

}