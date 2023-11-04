package pl.szymonleyk.betgame.register.account

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Account(@Id var id:Long, var username: String, var name: String, var surname: String)