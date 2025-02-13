import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class TestUtil {
    fun setInputStream(input: String) {
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)
    }

    fun setOutputStream(): OutputStream {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        return outputStream
    }
}