package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> =
        when {
            question.validate(answer)!=null -> {
                val validationError = question.validate(answer)
                "$validationError\n${question.question}" to status.color
            }
            question.answers.contains(answer.toLowerCase()) -> {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
            else -> {
                status = status.nextStatus()
                val gameOverText =
                    if (status == Status.NORMAL) {
                        question = question.restart()
                        ". Давай все по новой"
                    } else ""
                "Это неправильный ответ$gameOverText\n${question.question}" to status.color
            }
        }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status =
            if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question

        fun restart(): Question = NAME

        fun validate(answer: String): String? = when (this) {
            NAME ->
                "Имя должно начинаться с заглавной буквы"
                    .takeUnless { answer[0] in ('A'..'Z') }
            PROFESSION ->
                "Профессия должна начинаться со строчной буквы"
                    .takeUnless { answer[0] in ('a'..'z') }
            MATERIAL ->
                "Материал не должен содержать цифр"
                    .takeUnless { answer.findAnyOf(('0'..'9').map { it.toString() }) == null }
            BDAY ->
                "Год моего рождения должен содержать только цифры"
                    .takeUnless { answer.matches("[0-9]".toRegex()) }
            SERIAL ->
                "Серийный номер содержит только цифры, и их 7"
                    .takeUnless { answer.matches("[0-9]{7}".toRegex()) }
            IDLE ->
                null
        }


    }
}