package woowacourse.movie.list.view

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        setInitialSwitch()
        storeNotificationGrantedOrNot()
        return binding.root
    }

    private fun setInitialSwitch() {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS
        ) != -1
        if (!isPermissionGranted) {
            binding.permissionSwitch.isClickable = false
        }
        binding.permissionSwitch.isChecked = isPermissionGranted
    }

    private fun storeNotificationGrantedOrNot() {
        val sharedPreference = requireActivity().getSharedPreferences(
            SHARED_PREFERENCE_SETTING,
            AppCompatActivity.MODE_PRIVATE,
        )
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        binding.permissionSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean(NOTIFICATION_KEY, true).apply()
            } else {
                editor.putBoolean(NOTIFICATION_KEY, false).apply()
            }
        }
    }

    companion object {
        const val SHARED_PREFERENCE_SETTING = "settings"
        const val NOTIFICATION_KEY = "notification"
    }
}
