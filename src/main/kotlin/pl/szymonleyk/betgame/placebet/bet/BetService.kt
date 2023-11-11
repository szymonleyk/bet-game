package pl.szymonleyk.betgame.placebet.bet

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.szymonleyk.betgame.AccountNotFoundException
import pl.szymonleyk.betgame.NegativeBalanceException
import pl.szymonleyk.betgame.placebet.PlaceBetRequest
import pl.szymonleyk.betgame.placebet.PlaceBetResponse
import pl.szymonleyk.betgame.register.account.Account
import pl.szymonleyk.betgame.register.account.AccountRepository
import pl.szymonleyk.betgame.wallettransactions.WalletTransaction
import pl.szymonleyk.betgame.wallettransactions.WalletTransactionRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import kotlin.math.abs
import kotlin.random.Random

@Service
class BetService(
    private val betRepository: BetRepository,
    private val accountRepository: AccountRepository,
    private val walletTransactionRepository: WalletTransactionRepository
) {

    @Transactional
    fun placeBet(placeBetRequest: PlaceBetRequest): Mono<String> =
        accountRepository.findById(placeBetRequest.accountId)
            .switchIfEmpty(Mono.error(::AccountNotFoundException))
            .filter { hasSufficientBalance(it, placeBetRequest.betValue) }
            .switchIfEmpty(Mono.error(::NegativeBalanceException))
            .flatMap { deductBetValue(it, placeBetRequest.betValue) }
            .flatMap { play(it, placeBetRequest) }
            .thenReturn("Bet placed.")

    private fun deductBetValue(account: Account, betValue: Int): Mono<Account> {
        addNewWalletTransaction((-betValue).toDouble(), account.id!!)
        return changeAccountBalance((-betValue).toDouble(), account)
    }

    private fun addWinAmount(account: Account, amount: Double) {
        changeAccountBalance(amount, account).subscribe()
        addNewWalletTransaction(amount, account.id!!)
    }

    private fun play(account: Account, placeBetRequest: PlaceBetRequest): Mono<out Any>? {
        val randomNumber = generateRandomNumber()
        val winAmount = calculateBetResult(placeBetRequest, randomNumber)

        val hasWinner = winAmount > 0
        if (hasWinner) {
            addWinAmount(account, winAmount)
        }
        addBet(
            Bet(
                betDate = LocalDate.now(),
                betValue = placeBetRequest.betValue,
                betNumber = placeBetRequest.betNumber,
                win = hasWinner,
                accountId = account.id!!
            )
        )

        return Mono.empty()
    }

    private fun addBet(bet: Bet) {
        betRepository.save(bet).subscribe()
    }

    private fun calculateBetResult(placeBetRequest: PlaceBetRequest, randomNumber: Int): Double =
        when {
            placeBetRequest.betNumber == randomNumber -> 10.0 * placeBetRequest.betValue
            abs(placeBetRequest.betNumber - randomNumber) == 1 -> 5.0 * placeBetRequest.betValue
            abs(placeBetRequest.betNumber - randomNumber) == 2 -> 0.5 * placeBetRequest.betValue
            else -> 0.0
        }

    private fun generateRandomNumber(): Int = Random.nextInt(MIN_BET_VALUE, MAX_BET_VALUE + 1)

    private fun hasSufficientBalance(account: Account, betValue: Int): Boolean =
        account.balance - betValue >= ZERO_BALANCE

    private fun changeAccountBalance(betValue: Double, account: Account): Mono<Account> {
        val updatedAccount = account.copy(balance = account.balance + betValue)
        return accountRepository.save(updatedAccount)
    }

    private fun addNewWalletTransaction(amount: Double, accountId: Int) {
        walletTransactionRepository.save(
            WalletTransaction(
                transactionDate = LocalDate.now(),
                amount = amount,
                accountId = accountId
            )
        ).subscribe()
    }

    fun retriveBetsByAccount(accountId: Int): Flux<PlaceBetResponse> =
        betRepository.findAllByAccountId(accountId)
            .map { PlaceBetResponse(it.betValue, it.betNumber, it.win) }

    companion object {
        const val ZERO_BALANCE = 0L
        const val MIN_BET_VALUE = 1
        const val MAX_BET_VALUE = 10
    }
}
