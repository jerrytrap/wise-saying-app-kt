package org.example

class App {
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
            val wiseSaying = readln()
            print("작가 : ")
            val author = readln()
            println("${wiseSayingId}번 명언이 등록되었습니다.")
            wiseSayingId++
        }
        return true;
    }
}