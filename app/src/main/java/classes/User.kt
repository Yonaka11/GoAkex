package classes

import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.ImageButton
import androidx.compose.runtime.Composable


data class User(
    var name: String,
    var gsonHolder: String
) {
    fun recallUsers(Users: MutableList<User>):MutableList<String> {
         val uList = mutableListOf<String>()

        Users.forEach { user ->
            var thisUser = user.name
            uList.add(thisUser)
         }
    return uList
    }

}

