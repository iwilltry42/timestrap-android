package dev.iwilltry42.timestrap

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned

fun fromHtml(html: String?): Spanned {
    // account for deprecated method and minimum SDK version
    // as per https://stackoverflow.com/a/37905107/6450189
    return if (html == null) {
        SpannableString("")
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}