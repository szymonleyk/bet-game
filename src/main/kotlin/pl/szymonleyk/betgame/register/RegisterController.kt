package pl.szymonleyk.betgame.register

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
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
    fun register(@Valid @RequestBody accountRequest: AccountRequest): Mono<AccountIdData> =
        accountService.create(accountRequest)

}

data class AccountRequest(
    @field:Length(min = 5, max = 25)
    @field:NotBlank
    val username: String,

    @field:Length(max = 50)
    @field:NotBlank
    val name: String,

    @field:Length(max = 50)
    @field:NotBlank
    val surname: String
)

data class AccountIdData(
    @JsonProperty("id") val id: Int
)
