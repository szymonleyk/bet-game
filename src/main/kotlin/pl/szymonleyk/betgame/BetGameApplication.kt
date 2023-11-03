package pl.szymonleyk.betgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BetGameApplication

fun main(args: Array<String>) {
    runApplication<BetGameApplication>(*args)
}
