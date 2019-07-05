package ru.skillbranch.devintensive.extensions

fun String.truncate(newLength: Int = 16): String {
    val trimmedString = this.trimEnd()
    val trimmedLength = trimmedString.length
    return if (newLength < trimmedLength) "${this.substring(0, newLength).trimEnd()}..."
    else trimmedString
}

fun String.stripHtml(): String {

    var res = """<.+?>""".toRegex().replace(this, "")
    res = """&.+?;""".toRegex().replace(res, "")
    res = "\\s+".toRegex().replace(res, " ")

    return res
}