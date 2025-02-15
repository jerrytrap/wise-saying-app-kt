package org.example

import org.example.controller.WiseSayingController
import org.example.repository.FileManager
import org.example.repository.WiseSayingRepository
import org.example.service.WiseSayingService

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