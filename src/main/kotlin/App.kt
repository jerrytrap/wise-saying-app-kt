package org.example

class App {
    private val wiseSayings = ArrayList<WiseSaying>()

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
        } else if (command.startsWith("삭제")) {
            val id = command.split("?id=")[1].toInt()
            deleteWiseSaying(id)
        }
        return true;
    }

    fun addWiseSaying(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
    }

    fun getWiseSayingCount(): Int {
        return wiseSayings.size;
    }

    private fun printWiseSaying() {
        println("번호 / 작가 / 명언")

        wiseSayings.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
    }

    private fun deleteWiseSaying(id: Int) {
        if (wiseSayings.find { it.id == id - 1 } == null) {
            println("${id}번 명언은 존재하지 않습니다.")
        } else {
            wiseSayings.removeAt(id - 1)
            println("${id}번 명언이 삭제되었습니다.")
        }
    }
}