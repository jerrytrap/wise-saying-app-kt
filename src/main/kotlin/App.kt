package org.example

class App {
    private val fileUtil = FileUtil("db/wiseSaying/")
    private val wiseSayingRepository = WiseSayingRepository(fileUtil)
    private val wiseSayingService = WiseSayingService(wiseSayingRepository)
    private val wiseSayingController = WiseSayingController(wiseSayingService)

    fun run() {
        while (wiseSayingController.handleCommand()) {
            continue
        }
    }
}