package bogdandonduk.androidlibs.userinterfacelanguagesandroid

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import java.util.*

object UILanguagesService {
    private const val delimiter = "_"

    private const val LIBRARY_PREFIX = "prefs" + delimiter + "bogdandonduk.androidlibs.userinterfacelanguagesandroid" + delimiter
    private const val APP_LANGUAGE_CODE = "appLanguage$delimiter$LIBRARY_PREFIX"

    private fun getPreferences(context: Context) : SharedPreferences =
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)

    fun getAppLanguageCode(context: Context) : String =
        getPreferences(context)
            .getString(APP_LANGUAGE_CODE, Locale.getDefault().language)!!


    fun setAppLanguageCode(context: Context, languageCode: String, host: UILanguagesHost?) {
        getPreferences(context).edit().putString(APP_LANGUAGE_CODE, languageCode).apply()

        host?.initializeLanguage()
    }

    private fun getConfiguredResources(context: Context, languageCode: String) : Resources =
        context.createConfigurationContext(Configuration(context.resources.configuration).apply { setLocale(Locale(languageCode)) }).resources

    fun initializeTextLanguage(context: Context, text: TextView, @StringRes stringResId: Int) {
        text.text = getConfiguredResources(context, getAppLanguageCode(context)).getString(stringResId)
    }

    fun initializeActionBarTitleLanguage(context: Context, toolbar: Toolbar?, @StringRes stringResId: Int) {
        toolbar?.title = getConfiguredResources(context, getAppLanguageCode(context)).getString(stringResId)
    }

    fun initializeOptionsItemTooltipText(context: Context, menuItem: MenuItem?, @StringRes stringResId: Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            menuItem?.tooltipText = getConfiguredResources(context, getAppLanguageCode(context)).getString(stringResId)
    }
}