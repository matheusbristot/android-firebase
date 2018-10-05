package matheusbristot.firebaseandroid.data.entity

import java.io.Serializable

data class Event(
        val id: Int? = null,
        val title: String? = null,
        val description: String? = null
) : Serializable