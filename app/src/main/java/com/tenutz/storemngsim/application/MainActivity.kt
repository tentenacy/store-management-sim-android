package com.tenutz.storemngsim.application

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.core.result.ActivityResultFactory
import com.tenutz.storemngsim.data.datasource.sharedpref.Events
import com.tenutz.storemngsim.data.datasource.sharedpref.EventsSharedPref
import com.tenutz.storemngsim.databinding.ActivityMainBinding
import com.tenutz.storemngsim.databinding.DrawermenuBinding
import com.tenutz.storemngsim.ui.common.LogoutDialog
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBottomSheetDialog
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    lateinit var binding: ActivityMainBinding
    lateinit var drawermenuBinding: DrawermenuBinding

    private val menuCloseOnClickListener: (v: View) -> Unit = {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    }

    val vm: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawermenuBinding = DataBindingUtil.inflate(layoutInflater, R.layout.drawermenu, binding.navMain, true)

        binding.drawerMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        setOnClickListeners()

        Events.existsNewNotification.value = EventsSharedPref.existsNewNotification

        Events.existsNewNotification.observe(this) {
            drawermenuBinding.viewDrawermenu2Menu9.visibility = if(it) View.VISIBLE else View.GONE
        }

        vm.storeMain.observe(this) {
            drawermenuBinding.username = it.storeManagerName
            drawermenuBinding.storeName = it.storeName
        }

        vm.viewEvent.observe(this) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    GlobalViewModel.EVENT_GLOBAL_NAVIGATE_TO_LOGIN -> {
/*
                        if (currentDestinationId() != R.id.loginFragment) {
                            val navController = findNavController(R.id.container)
                            val navHostFragment: NavHostFragment =
                                supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
                            val inflater = navHostFragment.navController.navInflater
                            val graph = inflater.inflate(R.navigation.navigation_main)
                            graph.startDestination = R.id.loginFragment

                            navController.graph = graph
                        }
*/
                        navigateToLoginFragment()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        drawermenuBinding.imageDrawer1Logout.setOnClickListener {
            LogoutDialog {
                vm.logout()
                binding.drawerMain.closeDrawer(GravityCompat.END)
            }.show(supportFragmentManager, "logoutDialog")
        }
        drawermenuBinding.btnDrawermenuClose.setOnClickListener(menuCloseOnClickListener)
        drawermenuBinding.textDrawermenu2Menu1.setOnClickListener {
            navigateToMainCategoriesFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu2.setOnClickListener {
            navigateToMainMenusFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu3.setOnClickListener {
            navigateToOptionMenusFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu4.setOnClickListener {
            navigateToOptionGroupsFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu5.setOnClickListener {
            navigateToStatisticsFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu6.setOnClickListener {
            navigateToSalesFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu7.setOnClickListener {
            navigateToReviewsFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu8.setOnClickListener {
            navigateToHelpsFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.constraintDrawermenu2Menu9Container.setOnClickListener {
            navigateToNotificationFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
        drawermenuBinding.textDrawermenu2Menu10.setOnClickListener {
            navigateToSettingsFragment()
            binding.drawerMain.closeDrawer(GravityCompat.END)
        }
//        drawermenuBinding.constraintDrawermenu2Menu9Container.setOnClickListener { navigateToMainCategoriesFragment() }
//        drawermenuBinding.textDrawermenu2Menu10.setOnClickListener { navigateToMainCategoriesFragment() }
    }


    fun phoneNumber(): String? = (getSystemService(TELEPHONY_SERVICE) as TelephonyManager).line1Number?.replace("+82", "0")

    fun showPermissionDialog(permissionName: String) {
        AlertDialog.Builder(this).apply {
            setTitle("권한 설정")
                .setMessage("${getString(R.string.app_name)}의 모든 기능을 사용하기 위해 ${permissionName} 권한이 필요합니다. 이동을 눌러 설정을 완료해주세요.")
                .setPositiveButton("이동") { dialog, i ->
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null)
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(this)
                    }
                }
            setNegativeButton("닫기") { dialog, i ->

            }
        }.create().apply {
            setOnShowListener {
                (it as AlertDialog).getButton(DialogInterface.BUTTON_NEGATIVE).layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    ).apply {
                        setMargins(0, 0, 20, 0)
                    }
            }
        }.show()
    }
}