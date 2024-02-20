package post.pulse.blogs.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersData(
    val id: String,
    val name: String,
    val email: String,
    val mobile: String,
    val image : String,
):Parcelable
