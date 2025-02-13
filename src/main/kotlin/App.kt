package org.example

class App {
    private val wiseSayings = ArrayList<WiseSaying>()
    private var wiseSayingId = 1;

    fun run() {
        while (handleCommand()) {
            continue
        }
    }

    fun handleCommand(): Boolean {
        val command = readln()

        if (command == "종료") {
            print("프로그램을 종료합니다.")
            return false;
        } else if (command == "등록") {
            print("명언 : ")
            val content = readln()
            print("작가 : ")
            val author = readln()

            val wiseSaying = WiseSaying(content, author)
            addWiseSaying(wiseSaying)
            println("${wiseSaying.id}번 명언이 등록되었습니다.")
        } else if (command == "목록") {
            printWiseSaying()
        }
        return true;
    }

    fun addWiseSaying(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
    }

    private fun printWiseSaying() {
        println("번호 / 작가 / 명언")

        wiseSayings.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
    }
}