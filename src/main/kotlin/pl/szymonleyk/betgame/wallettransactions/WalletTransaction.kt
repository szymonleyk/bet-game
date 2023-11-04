package pl.szymonleyk.betgame.wallettransactions

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class WalletTransaction(@Id val id: Int?, val transactionDate: LocalDate, val amount: Int, val accountId: Int)