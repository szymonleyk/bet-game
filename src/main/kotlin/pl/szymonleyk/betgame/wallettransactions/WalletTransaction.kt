package pl.szymonleyk.betgame.wallettransactions

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class WalletTransaction(@Id val id: Long, val transactionDate: LocalDate, val amount: Integer, val accountId: Long)
