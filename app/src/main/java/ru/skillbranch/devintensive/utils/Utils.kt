package ru.skillbranch.devintensive.utils


object Utils {
    private val excludeRepoList = listOf("enterprise",
        "features",
        "topics",
        "collections",
        "trending",
        "events",
        "marketplace",
        "pricing",
        "nonprofit",
        "customer-stories",
        "security",
        "login",
        "join")

    private val dictionary = mutableMapOf(
        "А" to "A",
        "Б" to "B",
        "В" to "V",
        "Г" to "G",
        "Д" to "D",
        "Е" to "E",
        "Ё" to "E",
        "Ж" to "Zh",
        "З" to "Z",
        "И" to "I",
        "Й" to "I",
        "К" to "K",
        "Л" to "L",
        "М" to "M",
        "Н" to "N",
        "О" to "O",
        "П" to "P",
        "Р" to "R",
        "С" to "S",
        "Т" to "T",
        "У" to "U",
        "Ф" to "F",
        "Х" to "H",
        "Ц" to "C",
        "Ч" to "Ch",
        "Ш" to "Sh",
        "Щ" to "Sh'",
        "Ъ" to "",
        "Ы" to "I",
        "Ь" to "",
        "Э" to "E",
        "Ю" to "Yu",
        "Я" to "Ya",
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )


    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val strToParse = fullName.notNullOrEmptyOrSpace()

        val parts = strToParse?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstLetter = firstName.notNullOrEmptyOrSpace()?.capitalize()?.substring(0, 1)
        val secondLetter = lastName.notNullOrEmptyOrSpace()?.capitalize()?.substring(0, 1)

        return "${firstLetter.orEmpty()}${secondLetter.orEmpty()}".notNullOrEmptyOrSpace()
    }

    private fun String?.notNullOrEmptyOrSpace(): String? = this?.takeUnless { it in arrayOf("", " ") }

    fun transliteration(payload: String, divider: String = " "): String {
        dictionary[" "] = divider
        val tmpString = StringBuilder()

        payload.forEach { tmpString.append(replaceSymbolByDictionary(it.toString())) }
        return tmpString.toString()
    }

    private fun replaceSymbolByDictionary(input: String): String =
        dictionary[input] ?: input

    fun validateRepository(repo: String) : Boolean{
        val repoPattern = Regex("^(?:https://|https:\\\\\\\\|)(?:www\\.|)github\\.com/((?:(?:\\d|\\w)|(?:\\d|\\w)-)*(?:\\d|\\w)|(?:\\d|\\w))(?:/|\\\\|)\$", setOf(RegexOption.IGNORE_CASE))
        val githubPattern = Regex("^(?:https://|https:\\\\\\\\|)(?:www\\.|)github\\.com/",setOf(RegexOption.IGNORE_CASE))
        val restrictedWords = arrayOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join"
        )
        return if (repoPattern.matches(repo)){
            val userName = repo
                .replace(githubPattern, "")
                .replace("/","")
                .replace("\\","")
            restrictedWords.indexOf(userName) == -1
        }else{
            false
        }
    }

    fun isGithubAccValid(url: CharSequence?): Boolean {
        val githubAccount = "(https://)?(www.)?github.com/[^/]+".toRegex()

        return url != null && (url.isEmpty() ||
                (githubAccount.matches(url)) &&
                excludeRepoList.all { !url.contains("github.com/$it") }
                )
    }
}