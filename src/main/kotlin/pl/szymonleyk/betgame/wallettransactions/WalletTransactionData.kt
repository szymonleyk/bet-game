package pl.szymonleyk.betgame.wallettransactions

import java.time.LocalDate

data class WalletTransactionData(val transactionDate: LocalDate, val amount: Int)
