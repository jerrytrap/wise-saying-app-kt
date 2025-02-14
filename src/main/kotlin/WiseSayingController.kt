package org.example

class WiseSayingController(
    private val wiseSayingService: WiseSayingService
) {
    fun handleCommand(): Boolean {
        val input = readln()
        val command = Command.getInstance(input)

        when (command.type) {
            CommandType.QUIT -> {
                print("프로그램을 종료합니다.")
                return false;
            }

            CommandType.APPLY -> {
                print("명언 : ")
                val content = readln()
                print("작가 : ")
                val author = readln()

                val id = wiseSayingService.addWiseSaying(content, author)
                println("${id}번 명언이 등록되었습니다.")
            }

            CommandType.SHOW -> {
                println("번호 / 작가 / 명언")

                wiseSayingService.getWiseSayings().forEach { wiseSaying ->
                    println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
                }
            }

            CommandType.DELETE -> {
                if (wiseSayingService.deleteWiseSaying(command.id)) {
                    println("${command.id}번 명언이 삭제되었습니다.")
                } else {
                    println("${command.id}번 명언은 존재하지 않습니다.")
                }
            }

            CommandType.MODIFY -> {
                val wiseSaying = wiseSayingService.findWiseSaying(command.id)

                if (wiseSaying == null) {
                    println("${command.id}번 명언은 존재하지 않습니다.")
                } else {
                    println("명언(기존) : ${wiseSaying.content}")
                    print("명언 : ")
                    val content = readln()
                    println("작가(기존) : ${wiseSaying.author}")
                    print("작가 : ")
                    val author = readln()

                    wiseSayingService.modifyWiseSaying(command.id, content, author)
                }
            }

            CommandType.UNKNOWN -> {
                println("명령어를 다시 입력해주세요.")
            }
        }
        return true
    }
}