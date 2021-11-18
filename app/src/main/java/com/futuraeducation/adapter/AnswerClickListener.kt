package com.futuraeducation.adapter

interface AnswerClickListener {
    fun onAnswerClicked(isClicked: Boolean, option: Char, position: Int)
}