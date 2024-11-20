import android.text.method.PasswordTransformationMethod
import android.view.View



class passwordTransformation : PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return toStar(source)
    }

    private class toStar(private val source: CharSequence) : CharSequence {
        override val length: Int
            get() = source.length

        override fun get(index: Int): Char {

            return '*'


        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {


            return toStar(source.subSequence(startIndex, endIndex))

        }
    }
}