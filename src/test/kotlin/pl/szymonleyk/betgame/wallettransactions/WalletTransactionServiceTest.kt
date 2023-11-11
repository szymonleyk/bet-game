package pl.szymonleyk.betgame.wallettransactions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class WalletTransactionServiceTest {
    @Mock
    private lateinit var walletTransactionRepository: WalletTransactionRepository

    @InjectMocks
    private lateinit var walletTransactionService:WalletTransactionService

}