package pl.szymonleyk.betgame.register.account

import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository) {
    fun add(account: Account) = accountRepository.save(account).subscribe()
}