import org.example.App
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class WiseSayingTest {
    @Test
    fun `종료 명령어 입력 시 프로그램 종료`() {
        val input = "종료"
        val expectedOutput = "프로그램을 종료합니다."

        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        App().run()

        val actualOutput = outputStream.toString()
        assertEquals(actualOutput, expectedOutput)
    }
}