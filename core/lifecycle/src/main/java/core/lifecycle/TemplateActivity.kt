package core.lifecycle

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.core.app.TaskStackBuilder

open class TemplateActivity : ComponentActivity() {
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        // Resend intent updates as pending intents to fix navigation
        val data: Uri? = intent.data
        if (data?.scheme == "apptemplate") {
            val pending: PendingIntent? = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT.or(PendingIntent.FLAG_IMMUTABLE)
                )
            }
            pending?.send()
        }
    }
}
