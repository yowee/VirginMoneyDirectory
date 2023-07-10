package com.virginmoney.virginmoneydirectory.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.virginmoney.virginmoneydirectory.MainActivity
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.SessionManager
import com.virginmoney.virginmoneydirectory.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private var hasAccount = true
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val email = binding.etUsername
        val password = binding.etPassword
        val login = binding.btnLogin
        var register = binding.tvRegister
        val loading = binding.loading
        val firstName = binding.etFistname
        val lastName = binding.etLastname
        val loginTextView = binding.tvLogin
        val google = binding.btGoogle
        val facebookLoginButton = binding.btFacebook as LoginButton

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                email.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.INVISIBLE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                updateUiWithUser(loginResult.success)
                // Save user session using SessionManager
                val sessionManager = SessionManager(applicationContext)
                sessionManager.saveUserSession(
                    loginResult.success.userid,
                    loginResult.success.email
                )
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        if (hasAccount)
                            loginViewModel.signIn(email.text.toString(), password.text.toString())
                        if (!hasAccount)
                            loginViewModel.register(
                                email.text.toString(),
                                password.text.toString(),
                                firstName.text.toString(),
                                lastName.text.toString()
                            )

                    }
//                        loginViewModel.login(
//                            email.text.toString(),
//                            password.text.toString()
//                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE

                if (hasAccount)
                    loginViewModel.signIn(email.text.toString(), password.text.toString())
                if (!hasAccount)
                    loginViewModel.register(
                        email.text.toString(),
                        password.text.toString(),
                        firstName.text.toString(),
                        lastName.text.toString()
                    )
            }


            register.setOnClickListener {
                if (hasAccount) {
                    register.text = "Sign in"
                    loginTextView.text = "Register"
                    login.text = "Register"
                    firstName?.visibility = View.VISIBLE
                    lastName?.visibility = View.VISIBLE
                    hasAccount = false
                } else {
                    register.text = "You don't have account? Register Here!"
                    loginTextView.text = "Login"
                    login.text = "Sign In"
                    firstName?.visibility = View.GONE
                    lastName?.visibility = View.GONE
                    hasAccount = true
                }
            }

            google.setOnClickListener {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                googleSignInClient = GoogleSignIn.getClient(context, gso)
                startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
            }


            // Set permissions for Facebook Login (optional)
            facebookLoginButton.setPermissions("email", "public_profile")
            FacebookSdk.setClientToken("beb229138df47f0e50019abbfc62a7e0")
            val callbackManager = CallbackManager.Factory.create()

            // Register a callback for Facebook Login
            FacebookSdk.sdkInitialize(context)
            facebookLoginButton.registerCallback(
                callbackManager,
                loginViewModel.facebookCallBack()
            )


        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                loginViewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })


}