package com.jutt.catfactsfeeddemo.view.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.core.BaseActivity
import com.jutt.catfactsfeeddemo.databinding.ActivitySplashBinding
import com.jutt.catfactsfeeddemo.extensions.doFullScreen
import com.jutt.catfactsfeeddemo.viewmodels.HomeViewModel
import com.jutt.catfactsfeeddemo.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel by viewModels<SplashViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun onReady() {
        doFullScreen(
            binding.root,
            makeStatusBarTransparent = true,
            hideBottomNavButtons = true
        )

        setupViews()
    }

    private fun setupViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.appName.transitionName = getString(R.string.transition_appLogo)
        }
        val text = getString(R.string.app_name)
        val textLength = text.length

        lifecycleScope.launch {
            delay(3000)
            (0 until textLength).forEach {
                delay(100)
                binding.appName.text = binding.appName.text.toString().plus(text[it])
            }
            openHome()
        }

    }

    private fun openHome() {
        val intent: Intent = HomeActivity.newIntent(context = this)
        val options: ActivityOptions = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            options.update(
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    binding.appName,
                    binding.appName.transitionName
                )
            )
        }

        startActivity(intent)
        supportFinishAfterTransition()
    }
}