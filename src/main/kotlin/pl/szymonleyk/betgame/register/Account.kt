package pl.szymonleyk.betgame.register

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Account(@Id var username: String, var name: String, var surname: String)