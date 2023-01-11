package com.sopt.smeme.business.policy

object DiaryPolicy {
    fun textMoreThan10(text: String) = text.filterNot { it.isWhitespace() }.length >= 10

    fun onlyAlphabetMoreThan10(text: String) = text.replace("[^a-zA-Z]".toRegex(), "").length >= 10
}