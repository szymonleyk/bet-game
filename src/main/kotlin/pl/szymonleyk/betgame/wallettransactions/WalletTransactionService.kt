package pl.szymonleyk.betgame.wallettransactions

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WalletTransactionService(val walletTransactionRepository: WalletTransactionRepository) {
    fun add(walletTransaction: WalletTransaction) = walletTransactionRepository.save(walletTransaction)

    fun addEntryBalance(accountId: Int) {
        add(WalletTransaction(null, LocalDate.now(), ENTRY_BALANCE, accountId))
    }

    companion object {
        const val ENTRY_BALANCE = 1000
    }
}