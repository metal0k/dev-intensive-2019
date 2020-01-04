package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    companion object Factory {
        var mLastId: Int = -1

        fun makeUser(fullName: String): User {
            mLastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(
                mLastId.toString(),
                firstName,
                lastName,
                null
            )
        }
    }

    class Builder() {
        private var mId: String = ""
        private val user: User = User("", null, null, null)
        fun id(value: String) = this.apply { mId = value }
        fun firstName(value: String) = this.apply { user.firstName = value }
        fun lastName(value: String) = this.apply { user.lastName = value }
        fun avatar(value: String) = this.apply { user.avatar = value }
        fun rating(value: Int) = this.apply { user.rating = value }
        fun respect(value: Int) = this.apply { user.respect = value }
        fun lastVisit(value: Date) = this.apply { user.lastVisit = value }
        fun isOnline(value: Boolean) = this.apply { user.isOnline = value }

        fun build(): User = user.copy(id = mId);

    }
}