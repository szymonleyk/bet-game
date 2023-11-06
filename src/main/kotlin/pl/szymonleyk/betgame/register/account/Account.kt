package pl.szymonleyk.betgame.register.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Account(@Id var id: Int? = null, var username: String, var name: String, var surname: String)