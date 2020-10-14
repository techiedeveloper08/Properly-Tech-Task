package com.sample.demo.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object Validation {

   fun isValidEmail(string: String): Boolean {
        val emailRegex = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+).([a-zA-Z]{2,5})$"
        val pattern: Pattern = Pattern.compile(emailRegex)
        val matcher: Matcher = pattern.matcher(string)
        return matcher.matches()
    }
}