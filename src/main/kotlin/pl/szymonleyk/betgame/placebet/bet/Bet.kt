package pl.szymonleyk.betgame.placebet.bet

import jakarta.validation.constraints.Min
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Bet(
    @Id val id: Int? = null,
    val betDate: LocalDate,
    @Min(1)
    val betValue: Int,
    val betNumber: Int,
    val win: Boolean,
    val accountId: Int
)
