package bogdandonduk.androidlibs.userinterfacelanguagesandroid

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object UILanguagesService {
    private const val delimiter = "_"

    private const val LIBRARY_PREFIX = "prefs" + delimiter + "bogdandonduk.androidlibs.userinterfacelanguagesandroid" + delimiter
    private const val CURRENT_APP_LANGUAGE_CODE = "currentAppLanguage$delimiter$LIBRARY_PREFIX"

    private fun getPreferences(context: Context) : SharedPreferences =
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)

    private fun getCurrentAppLanguageCode(context: Context) : String =
        getPreferences(context)
            .getString(CURRENT_APP_LANGUAGE_CODE, Locale.getDefault().language)!!


    fun setLanguageCode(context: Context, languageCode: String, host: UILanguagesHost?) {
        getPreferences(context).edit().putString(CURRENT_APP_LANGUAGE_CODE, languageCode).apply()

        host?.initializeLanguage()
    }

    private fun getConfiguredResources(context: Context, languageCode: String) : Resources =
        context.createConfigurationContext(Configuration(context.resources.configuration).apply { setLocale(Locale(languageCode)) }).resources
}