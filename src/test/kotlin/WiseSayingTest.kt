import org.example.App
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.example.WiseSaying
import org.junit.jupiter.api.AfterEach

class WiseSayingTest {
    private val app = App()
    private val testUtil = TestUtil()

    @AfterEach
    fun tearDown() {
        WiseSaying.resetIndex()
    }

    @Test
    fun `종료 명령어 입력 시 프로그램 종료`() {
        val input = "종료"
        val expectedOutput = "프로그램을 종료합니다."

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        app.handleCommand()

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

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        app.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("명언 : ")
            .contains("작가 : ")
    }

    @Test
    fun `명언 등록 시 명언번호 표시`() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
        """.trimIndent()

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        app.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 등록 시 명언번호 증가`() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
        """.trimIndent()

        testUtil.setInputStream(input)
        val outputStream1 = testUtil.setOutputStream()

        app.handleCommand()

        testUtil.setInputStream(input)
        val outputStream2 = testUtil.setOutputStream()

        app.handleCommand()

        val actualOutput1 = outputStream1.toString()
        val actualOutput2 = outputStream2.toString()
        assertThat(actualOutput1).contains("1번 명언이 등록되었습니다.")
        assertThat(actualOutput2).contains("2번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 목록 출력`() {
        val input = "목록"
        val wiseSaying1 = WiseSaying("현재를 사랑하라.", "작자미상")
        val wiseSaying2 = WiseSaying("현재를 사랑하라.", "작자미상")
        app.addWiseSaying(wiseSaying1)
        app.addWiseSaying(wiseSaying2)

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        app.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("번호 / 작가 / 명언")
            .contains("1 / 작자미상 / 현재를 사랑하라.")
            .contains("2 / 작자미상 / 현재를 사랑하라.")
    }

    @Test
    fun `명언 삭제`() {
        val input = "삭제?id=1"
        val wiseSaying = WiseSaying("현재를 사랑하라.", "작자미상")
        app.addWiseSaying(wiseSaying)

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        app.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput).contains("1번 명언이 삭제되었습니다.")
        assertEquals(0, app.getWiseSayingCount())
    }
}