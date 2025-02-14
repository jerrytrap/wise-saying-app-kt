package org.example

class App {
    private val wiseSayingController = WiseSayingController(WiseSayingService(WiseSayingRepository()))

    fun run() {
        while (wiseSayingController.handleCommand()) {
            continue
        }
    }
}