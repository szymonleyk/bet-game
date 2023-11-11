package pl.szymonleyk.betgame.register.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Account(
    @Id val id: Int? = null,
    val username: String,
    val name: String,
    val surname: String,
    val balance: Double
)