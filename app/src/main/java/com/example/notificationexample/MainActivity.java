package com.example.notificationexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   private Button btnShowNotification;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      createNotificationChannel();

      btnShowNotification = findViewById(R.id.btn_show_notification);

      // Create an Intent for the activity you want to start
      Intent resultIntent = new Intent(this, AlertDetailsActivity.class);
      // Create the TaskStackBuilder and add the intent, which inflates the back stack
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
      stackBuilder.addNextIntentWithParentStack(resultIntent);
      // Get the PendingIntent containing the entire back stack
      PendingIntent resultPendingIntent =
              stackBuilder.getPendingIntent(0,
                      PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

//      Intent notifyIntent = new Intent(this, AlertDetailsActivity.class);
//// Set the Activity to start in a new, empty task
//      notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//              | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//// Create the PendingIntent
//      PendingIntent notifyPendingIntent = PendingIntent.getActivity(
//              this, 0, notifyIntent,
//              PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//      );

//      Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
//      snoozeIntent.setAction(ACTION_SNOOZE);
//      snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//      PendingIntent snoozePendingIntent =
//              PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

//      Intent fullScreenIntent = new Intent(this, ImportantActivity.class);
//      PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
//              fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Lemubit")
              .setSmallIcon(R.drawable.ic_round_add_alert_24)
              .setContentTitle("My notification")
              .setContentText("Much longer text that cannot fit one line...")
              .setStyle(new NotificationCompat.BigTextStyle()
                      .bigText("Much longer text that cannot fit one line..."))
              .setPriority(NotificationCompat.PRIORITY_DEFAULT)
              .setCategory(NotificationCompat.CATEGORY_MESSAGE)
              // Set the intent that will fire when the user taps the notification
              .setContentIntent(resultPendingIntent)
              // Set number of notification displayed on long-press
              // .setNumber(4)
              // Set full-screen intent to show urgent message
              // .setFullScreenIntent(fullScreenPendingIntent, true)
              // Set lock screen visibility
              // .setVisibility(VISIBILITY_PRIVATE)
              .setAutoCancel(true);
      // Send broadcast to receiver when the user click button
      // You can do a variety of other things with this addAction() method
      // .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
      // snoozePendingIntent)

      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

      btnShowNotification.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(100, builder.build());
         }
      });
   }

   // Create channel and set importance
   private void createNotificationChannel() {
      // Create the NotificationChannel, but only on API 26+ because
      // the NotificationChannel class is new and not in the support library
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         CharSequence name = getString(R.string.channel_name);
         String description = getString(R.string.channel_description);
         int importance = NotificationManager.IMPORTANCE_DEFAULT;
         NotificationChannel channel = new NotificationChannel("Lemubit", name, importance);
         channel.setDescription(description);
         // channel.setShowBadge();
         // Register the channel with the system; you can't change the importance
         // or other notification behaviors after this
         NotificationManager notificationManager = getSystemService(NotificationManager.class);
         notificationManager.createNotificationChannel(channel);
      }
   }
}