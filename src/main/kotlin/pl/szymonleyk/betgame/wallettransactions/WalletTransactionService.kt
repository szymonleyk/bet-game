package pl.szymonleyk.betgame.wallettransactions

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WalletTransactionService(val walletTransactionRepository: WalletTransactionRepository) {
    fun addTransaction(walletTransaction: WalletTransaction) = walletTransactionRepository.save(walletTransaction)

    fun addEntryBalance(accountId: Int) = addTransaction(
        WalletTransaction(
            transactionDate = LocalDate.now(), amount = ENTRY_BALANCE, accountId = accountId
        )
    )

    companion object {
        const val ENTRY_BALANCE = 1000
    }
}