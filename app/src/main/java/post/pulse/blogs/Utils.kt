package post.pulse.blogs

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.navigation.NavController
import androidx.navigation.NavOptions

object Utils {
    fun NavController.navigateTo(
        @IdRes resId: Int = 0,
        @Nullable args: Bundle? = null,
        navOptions: NavOptions? = getDefaultNavOptions()
    ) {
        try {
            this.navigate(resId, args, navOptions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDefaultNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.activity_slide_in_right_trans)
            .setExitAnim(R.anim.activity_slide_out_left_trans)
            .setPopEnterAnim(R.anim.activity_slide_in_left_trans)
            .setPopExitAnim(R.anim.activity_slide_out_right_trans)
            .build()
    }
}