package pl.szymonleyk.betgame.config

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import pl.szymonleyk.betgame.register.exceptions.NonUniqueUsernameException

@RestControllerAdvice
class ValidationHandler {
    @ExceptionHandler(WebExchangeBindException::class) // Handle generic exceptions
    fun handleGenericException(e: WebExchangeBindException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()
        e.bindingResult.allErrors.filterIsInstance<FieldError>().forEach { fieldError ->
            errors[fieldError.field] = fieldError.defaultMessage ?: "Validation error"
        }

        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(NonUniqueUsernameException::class)
    fun handleGenericException2(e: NonUniqueUsernameException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()

        val errorMessage = e.message ?: "Non-unique username error"
        errors["username"] = errorMessage

        return ResponseEntity.badRequest().body(errors)
    }

}
