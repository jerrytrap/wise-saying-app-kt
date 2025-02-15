import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.example.*
import org.junit.jupiter.api.AfterEach
import kotlin.test.fail

class WiseSayingTest {
    private val wiseSayingRepository = WiseSayingRepository(FileManager("db/test/"))
    private val wiseSayingService = WiseSayingService(wiseSayingRepository)
    private val wiseSayingController = WiseSayingController(wiseSayingService)

    private val testUtil = TestUtil()

    @AfterEach
    fun tearDown() {
        wiseSayingRepository.clearAll()
    }

    @Test
    fun `종료 명령어 입력 시 프로그램 종료`() {
        val input = "종료"
        val expectedOutput = "프로그램을 종료합니다."

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

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

        wiseSayingController.handleCommand()

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

        wiseSayingController.handleCommand()

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

        wiseSayingController.handleCommand()

        testUtil.setInputStream(input)
        val outputStream2 = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput1 = outputStream1.toString()
        val actualOutput2 = outputStream2.toString()
        assertThat(actualOutput1).contains("1번 명언이 등록되었습니다.")
        assertThat(actualOutput2).contains("2번 명언이 등록되었습니다.")
    }

    @Test
    fun `명언 목록 출력`() {
        val input = "목록"

        wiseSayingService.apply {
            addWiseSaying("현재를 사랑하라.", "작자미상")
            addWiseSaying("현재를 사랑하라.", "작자미상")
        }

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("번호 / 작가 / 명언")
            .contains("1 / 작자미상 / 현재를 사랑하라.")
            .contains("2 / 작자미상 / 현재를 사랑하라.")
    }

    @Test
    fun `명언 삭제`() {
        val input = "삭제?id=1"
        wiseSayingService.addWiseSaying("현재를 사랑하라.", "작자미상")

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput).contains("1번 명언이 삭제되었습니다.")
        assertEquals(0, wiseSayingService.getCount())
    }

    @Test
    fun `존재하지 않는 명언 삭제`() {
        val input = "삭제?id=1"

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput).contains("1번 명언은 존재하지 않습니다.")
        assertEquals(0, wiseSayingService.getCount())
    }

    @Test
    fun `명언 수정`() {
        val input = """
            수정?id=1
            현재와 자신을 사랑하라.
            홍길동
        """.trimIndent()

        wiseSayingService.addWiseSaying("현재를 사랑하라.", "작자미상")

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("명언(기존) : 현재를 사랑하라.")
            .contains("작가(기존) : 작자미상")

        val modifiedWiseSaying = wiseSayingService.findWiseSaying(1) ?: fail("1번 명언을 찾을 수 없습니다.")
        assertEquals("현재와 자신을 사랑하라.", modifiedWiseSaying.content)
        assertEquals("홍길동", modifiedWiseSaying.author)
    }

    @Test
    fun `명언 검색`() {
        val input = "목록?keywordType=content&keyword=현재"

        wiseSayingService.apply {
            addWiseSaying("현재를 사랑하라.", "작자미상")
            addWiseSaying("과거에 집착하지 마라.", "홍길동")
        }

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("번호 / 작가 / 명언")
            .contains("1 / 작자미상 / 현재를 사랑하라.")
            .doesNotContain("2 / 홍길동 / 과거에 집착하지 마라.")
    }

    @Test
    fun `명언 페이징`() {
        val input = "목록?page=2"

        wiseSayingService.apply {
            addWiseSaying("명언1", "작가1")
            addWiseSaying("명언2", "작가2")
            addWiseSaying("명언3", "작가3")
            addWiseSaying("명언4", "작가4")
            addWiseSaying("명언5", "작가5")
            addWiseSaying("명언6", "작가6")
        }

        testUtil.setInputStream(input)
        val outputStream = testUtil.setOutputStream()

        wiseSayingController.handleCommand()

        val actualOutput = outputStream.toString()
        assertThat(actualOutput)
            .contains("번호 / 작가 / 명언")
            .contains("1 / 작가1 / 명언1")
            .contains("1 [2]")
            .doesNotContain("2 / 작가2 / 명언2")
            .doesNotContain("3 / 작가3 / 명언3")
            .doesNotContain("4 / 작가4 / 명언4")
            .doesNotContain("5 / 작가5 / 명언5")
            .doesNotContain("6 / 작가6 / 명언6")
    }
}