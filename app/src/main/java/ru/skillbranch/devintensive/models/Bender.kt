package ru.skillbranch.devintensive.models

import android.os.Bundle
import ru.skillbranch.devintensive.extensions.log_d

const val STATUS_ID = "status"
const val QUESTION_ID = "question"

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME  ) {

    fun askQuestion(): String = question.question;

    fun listenAnswer(answer: String): Pair<String,Triple<Int,Int,Int>> {
        if ((question.validateAnswer(answer))) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        }
        else {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            else {
                status = status.nextStatus()
                return "Это неправильный ответ!\n${question.question}" to status.color
            }
        }
    }

    fun saveState( bundle: Bundle?) =
        bundle?.apply {
            putString(STATUS_ID, status.name)
            putString(QUESTION_ID, question.name)
        }

    fun restoreState(bundle: Bundle?) = bundle?.apply {
        val lstatus = getString(STATUS_ID, Status.NORMAL.name)
        val lquestion = getString(QUESTION_ID, Question.NAME.name)
        log_d("restoreState $lstatus $lquestion")
        status = Status.valueOf(lstatus)
        question = Question.valueOf(lquestion)
    }



    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;
        fun nextStatus(): Status = if (this.ordinal == (values().lastIndex)) values()[0] else Status.values()[this.ordinal+1]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun validateAnswer(answer: String): Boolean {
                return super.validateAnswer(answer)
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validateAnswer(answer: String): Boolean {
                return super.validateAnswer(answer)
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validateAnswer(answer: String): Boolean {
                return super.validateAnswer(answer)
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validateAnswer(answer: String): Boolean {
                return super.validateAnswer(answer)
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validateAnswer(answer: String): Boolean {
                return super.validateAnswer(answer)
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validateAnswer(answer: String): Boolean {
                return true

            }
        };

        fun nextQuestion(): Question = if (this.ordinal == values().lastIndex) this else values()[this.ordinal+1]

        open fun validateAnswer(answer: String): Boolean = this.answers.contains(answer.toLowerCase())
    }
}