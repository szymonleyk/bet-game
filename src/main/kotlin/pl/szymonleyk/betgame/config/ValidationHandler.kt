package pl.szymonleyk.betgame.config

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class ValidationHandler {
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleException(e: WebExchangeBindException): ResponseEntity<Map<String, String>> {
        val errors = e.bindingResult.allErrors
            .filterIsInstance<FieldError>()
            .associateBy(
                { it.field },
                { it.defaultMessage ?: "Validation error" }
            )
        return ResponseEntity.badRequest().body(errors)
    }
}
