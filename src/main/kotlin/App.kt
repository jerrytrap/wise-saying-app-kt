package org.example

class App {
    private val fileManager = FileManager("db/wiseSaying/")
    private val wiseSayingRepository = WiseSayingRepository(fileManager)
    private val wiseSayingService = WiseSayingService(wiseSayingRepository)
    private val wiseSayingController = WiseSayingController(wiseSayingService)

    fun run() {
        while (wiseSayingController.handleCommand()) {
            continue
        }
    }
}