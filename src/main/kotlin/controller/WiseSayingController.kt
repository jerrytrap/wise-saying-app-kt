package org.example.controller

import org.example.service.WiseSayingService
import org.example.entity.Command
import org.example.entity.CommandType
import org.example.entity.Page
import org.example.entity.WiseSaying

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
                val result = wiseSayingService.getWiseSayings(command.keywordType, command.keyword, command.page)
                printWiseSayings(result)
            }

            CommandType.DELETE -> {
                val wiseSaying = wiseSayingService.findWiseSaying(command.targetId)

                if (wiseSaying == null) {
                    println("${command.targetId}번 명언은 존재하지 않습니다.")
                } else {
                    wiseSayingService.deleteWiseSaying(command.targetId)
                    println("${command.targetId}번 명언이 삭제되었습니다.")
                }
            }

            CommandType.MODIFY -> {
                val wiseSaying = wiseSayingService.findWiseSaying(command.targetId)

                if (wiseSaying == null) {
                    println("${command.targetId}번 명언은 존재하지 않습니다.")
                } else {
                    println("명언(기존) : ${wiseSaying.content}")
                    print("명언 : ")
                    val content = readln()
                    println("작가(기존) : ${wiseSaying.author}")
                    print("작가 : ")
                    val author = readln()

                    wiseSayingService.modifyWiseSaying(command.targetId, content, author)
                }
            }

            CommandType.UNKNOWN -> {
                println("명령어를 다시 입력해주세요.")
            }
        }
        return true
    }

    private fun printWiseSayings(wiseSayings: Page<WiseSaying>) {
        printColumns()

        wiseSayings.data.forEach { wiseSaying ->
            println("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }

        printPages(wiseSayings.page, wiseSayings.totalSize)
    }

    private fun printColumns() {
        println("번호 / 작가 / 명언")
    }

    private fun printPages(page: Int, totalPageCount: Int) {
        println("---------------")
        print("페이지 :")

        IntRange(1, totalPageCount).forEach { p ->
            if (p == page) {
                print(" [${p}]")
            } else {
                print(" $p")
            }
        }

        println()
    }
}