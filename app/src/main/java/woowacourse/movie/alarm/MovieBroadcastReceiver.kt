package woowacourse.movie.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.database.NotificationSharedPreference
import woowacourse.movie.ticket.view.MovieTicketActivity

class MovieBroadcastReceiver : BroadcastReceiver() {
    private var movieTitle: String? = null
    private var movieId: Long = 0
    override fun onReceive(context: Context, intent: Intent?) {
        val isNotificationGranted = NotificationSharedPreference(context).load()
        movieTitle = intent?.getStringExtra(MovieTicketActivity.EXTRA_MOVIE_TITLE_KEY)
        movieId = intent!!.getLongExtra(MovieTicketActivity.EXTRA_MOVIE_ID_KEY, 0)
        if (!isNotificationGranted) return
        makeNotification(context)
    }

    private fun makeNotification(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(context, channelId)
        val pendingIntent = makePendingIntent(context)
        setBuilder(builder, pendingIntent)
        manager.notify(1, builder.build())
    }

    private fun makePendingIntent(context: Context): PendingIntent? {
        val ticketIntent = MovieTicketActivity.newTicketActivityInstance(context, movieId)
        return PendingIntent.getActivity(context, 10, ticketIntent, PendingIntent.FLAG_MUTABLE)
    }

    private fun setBuilder(
        builder: NotificationCompat.Builder,
        pendingIntent: PendingIntent?,
    ) {
        builder.setSmallIcon(android.R.drawable.ic_popup_reminder)
        builder.setContentTitle(MESSAGE_RESERVATION_NOTIFICATION)
        builder.setContentText(MESSAGE_SCREENING_INFORMATION.format(movieTitle))
        builder.setContentIntent(pendingIntent)
    }

    companion object {
        private const val MESSAGE_RESERVATION_NOTIFICATION = "예매 알림"
        private const val MESSAGE_SCREENING_INFORMATION = "%s 30분 후에 상영"
    }
}
