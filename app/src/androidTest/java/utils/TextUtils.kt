package utils

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import java.util.regex.Matcher

fun hasTextInputLayoutErrorText(expectedErrorText: String) = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) { }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is TextInputLayout) return false
        val error = item.error ?: return false
        val errorMatched = error.toString()
        return expectedErrorText == errorMatched
    }
}