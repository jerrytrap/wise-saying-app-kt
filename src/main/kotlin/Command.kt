package org.example

import java.security.Key

class Command private constructor(
    val type: CommandType,
    val targetId: Int = DEFAULT_ID,
    val keywordType: KeywordType = KeywordType.NONE,
    val keyword: String = ""
) {
    companion object {
        private const val DEFAULT_ID = -1
        private val editRegex = "^수정\\?id=(\\d+)$".toRegex()
        private val deleteRegex = "^삭제\\?id=(\\d+)$".toRegex()
        private val searchRegex = "^목록(?:\\?keywordType=(author|content)&keyword=([^&]+))?(?:[?&]page=(\\d+))?$".toRegex()


        fun getInstance(input: String): Command {
            return when {
                input == "종료" -> {
                    Command(CommandType.QUIT)
                }

                input == "등록" -> {
                    Command(CommandType.APPLY)
                }

                input == "목록" -> {
                    Command(CommandType.SHOW)
                }

                input.matches(editRegex) -> {
                    val matchResult = editRegex.find(input)
                    val id = matchResult?.groups?.get(1)?.value?.toIntOrNull()

                    if (id != null) {
                        Command(CommandType.MODIFY, id)
                    } else {
                        Command(CommandType.UNKNOWN)
                    }
                }

                input.matches(deleteRegex) -> {
                    val matchResult = deleteRegex.find(input)
                    val id = matchResult?.groups?.get(1)?.value?.toIntOrNull() ?: return Command(CommandType.UNKNOWN)

                    Command(CommandType.DELETE, id)
                }

                input.matches(searchRegex) -> {
                    val matchResult = searchRegex.find(input)
                    val keywordType = matchResult?.groups?.get(1)?.value ?: return Command(CommandType.UNKNOWN)
                    val keyword = matchResult.groups[2]?.value ?: return Command(CommandType.UNKNOWN)

                    Command(
                        type = CommandType.SHOW,
                        keywordType = KeywordType.fromType(keywordType),
                        keyword = keyword
                    )
                }

                else -> {
                    Command(CommandType.UNKNOWN)
                }
            }
        }
    }
}