package org.example

class Command private constructor(
    val type: CommandType,
    val id: Int = DEFAULT_ID
) {
    companion object {
        private const val DEFAULT_ID = -1
        private val editRegex = "^수정\\?id=(\\d+)$".toRegex()
        private val deleteRegex = "^삭제\\?id=(\\d+)$".toRegex()

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
                    val id = matchResult?.groups?.get(1)?.value?.toIntOrNull()

                    if (id != null) {
                        Command(CommandType.DELETE, id)
                    } else {
                        Command(CommandType.UNKNOWN)
                    }
                }

                else -> {
                    Command(CommandType.UNKNOWN)
                }
            }
        }
    }
}