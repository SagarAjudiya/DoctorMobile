package com.example.doctormobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.doctormobile.R
import com.example.doctormobile.databinding.ActivityWelcomeBinding
import com.example.doctormobile.helpers.LoginVMFactory
import com.example.doctormobile.model.LoginResponse
import com.example.doctormobile.repository.LoginRepository
import com.example.doctormobile.viewmodel.LoginViewModel

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            toolbar.toolbarTitle.text = getString(R.string.welcome)
            toolbar.imgBack.visibility = View.GONE
        }
        binding.btnSignIn.setOnClickListener {
            val loginViewModel = ViewModelProvider(
                this,
                LoginVMFactory(LoginRepository())
            )[LoginViewModel::class.java]
            loginViewModel.postLoginData(
                binding.etxtEmail.text?.toString(),
                binding.etxtPassword.text?.toString()
            ) { loginResponse ->
                signIn(loginResponse)
            }
        }
        binding.txtvAlreadyAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.txtvOR.setOnClickListener {
            val intent = Intent(this, DoctorHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(loginResponse: LoginResponse?) {
        if (loginResponse == null) {
            Toast.makeText(this, getString(R.string.enter_valid_username_and_password), Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, DoctorHomeActivity::class.java)
            startActivity(intent)
        }
    }
}