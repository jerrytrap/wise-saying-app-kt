import org.example.App
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.assertj.core.api.Assertions.assertThat
import java.io.*

class WiseSayingTest {
    @Test
    fun `종료 명령어 입력 시 프로그램 종료`() {
        val input = "종료"
        val expectedOutput = "프로그램을 종료합니다."

        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        App().handleCommand()

        val actualOutput = outputStream.toString()
        assertEquals(actualOutput, expectedOutput)
    }

    @Test
    fun `명언 등록`() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
        """.trimIndent()

        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        App().handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("명언 : ")
            .contains("작가 : ")
    }
}