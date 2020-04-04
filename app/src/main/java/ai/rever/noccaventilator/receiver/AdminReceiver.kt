package ai.rever.noccaventilator.receiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast


class AdminReceiver : DeviceAdminReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action === ACTION_DEVICE_ADMIN_DISABLE_REQUESTED) {
            abortBroadcast()
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context, intent: Intent?) {
        context.toast("Nocca Ventilator: Admin enabled")
    }

    override fun onDisableRequested(context: Context?, intent: Intent?): CharSequence {
        return "Disable requested"
    }

    override fun onDisabled(context: Context, intent: Intent?) {
        context.toast("Nocca Ventilator: Admin disabled")
    }

    override fun onPasswordChanged(context: Context?, intent: Intent?) {
        // Do nothing
    }

    override fun onPasswordFailed(context: Context?, intent: Intent?) {
        // Do nothing
    }

    override fun onPasswordSucceeded(context: Context?, intent: Intent?) {
        // Do nothing
    }

    override fun onPasswordExpiring(context: Context?, intent: Intent?) {
        // Do nothing
    }

    companion object {
        private const val TAG = "DeviceAdminReceiver"
    }
}