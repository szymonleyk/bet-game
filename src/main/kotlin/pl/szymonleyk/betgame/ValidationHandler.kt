package pl.szymonleyk.betgame

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

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

    @ExceptionHandler(BetGameException::class)
    fun handleGenericException2(e: BetGameException): ResponseEntity<List<String>> {
        val errors = mutableListOf<String>()
        errors.add(e.message!!)
        val statusCode = e.statusCode
        return ResponseEntity.status(statusCode).body(errors)
    }

}
