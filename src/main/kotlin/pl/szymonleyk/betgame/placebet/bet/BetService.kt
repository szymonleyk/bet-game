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
    fun placeBet(placeBetRequest: PlaceBetRequest): Mono<PlaceBetResponse> =
        accountRepository.findById(placeBetRequest.accountId)
            .switchIfEmpty(Mono.error(::AccountNotFoundException))
            .filter { hasSufficientBalance(it, placeBetRequest.betValue) }
            .switchIfEmpty(Mono.error(::NegativeBalanceException))
            .flatMap { deductBetValue(it, placeBetRequest.betValue) }
            .flatMap { play(it, placeBetRequest) }
            .flatMap { addBet(it) }

    private fun addBet(bet: Bet): Mono<PlaceBetResponse> {
        return betRepository.save(bet).map { PlaceBetResponse(bet.betValue, bet.betNumber, bet.win) }
    }

    private fun deductBetValue(account: Account, betValue: Int): Mono<Account> =
        changeAccountBalance((-betValue).toDouble(), account)
            .doOnSuccess { addNewWalletTransaction((-betValue).toDouble(), account.id!!) }

    private fun addWinAmount(account: Account, amount: Double): Mono<Account> =
        changeAccountBalance(amount, account)
            .doOnSuccess { addNewWalletTransaction(amount, account.id!!) }


    private fun play(account: Account, placeBetRequest: PlaceBetRequest): Mono<Bet> {
        return Mono.defer {
            val randomNumber = generateRandomNumber()
            calculateBetResult(placeBetRequest, randomNumber)
                .flatMap { winAmount ->
                    createBet(account, placeBetRequest, winAmount)
                }
        }
    }

    private fun createBet(account: Account, placeBetRequest: PlaceBetRequest, winAmount: Double): Mono<Bet> {
        val bet = Bet(
            betDate = LocalDate.now(),
            betValue = placeBetRequest.betValue,
            betNumber = placeBetRequest.betNumber,
            win = true,
            accountId = placeBetRequest.accountId
        )
        return if (winAmount > 0) {
            addWinAmount(account, winAmount)
                .map {
                    bet.copy(win = true)
                }
        } else {
            Mono.just(
                bet.copy(win = false)
            )
        }
    }

    private fun calculateBetResult(placeBetRequest: PlaceBetRequest, randomNumber: Int): Mono<Double> {
        val difference = abs(placeBetRequest.betNumber - randomNumber)
        return Mono.just(
            when {
                difference == 0 -> 10.0 * placeBetRequest.betValue
                difference == 1 -> 5.0 * placeBetRequest.betValue
                difference == 2 -> 0.5 * placeBetRequest.betValue
                else -> 0.0
            }
        )
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
