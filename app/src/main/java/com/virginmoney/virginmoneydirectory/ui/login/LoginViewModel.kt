package com.virginmoney.virginmoneydirectory.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.repository.LoginRepository
import com.virginmoney.virginmoneydirectory.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    private lateinit var auth: FirebaseAuth;

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(
                    success = LoggedInUserView(
                        displayName = result.data.displayName,
                        userid = result.data.userId,
                        email = result.data.email
                    )
                )
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }




    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun register(email: String, password: String, firstName: String, lastName: String) {
        auth = Firebase.auth
        CoroutineScope(Dispatchers.Main).launch {
            val user = registerWithEmailAndPassword(email, password, firstName, lastName)
            if (user != null) {
                // Sign in success, update UI with the signed-in user's information
                // Do something with the user
                System.out.println("LoginViewModel " + "  Registration  Successfull")
                _loginResult.value =
                    LoginResult(
                        success = LoggedInUserView(
                            displayName = user?.displayName.toString(),
                            userid = user.uid,
                            email = user.email.toString()
                        )
                    )
            } else {
                // Sign in failed, display a message to the user
                System.out.println("LoginViewModel " + "  Login Not Successfull")
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    suspend fun registerWithEmailAndPassword(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): FirebaseUser? {
        auth = Firebase.auth

        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user
        } catch (e: Exception) {
            null
        }
    }


    fun signIn(email: String, password: String) {
        auth = Firebase.auth
        CoroutineScope(Dispatchers.Main).launch {
            val user = signInWithEmailAndPassword(email, password)
            if (user != null) {
                // Sign in success, update UI with the signed-in user's information
                // Do something with the user
                System.out.println("LoginViewModel " + "  Login Not Successfull")
                _loginResult.value =
                    LoginResult(
                        success = LoggedInUserView(
                            displayName = user?.displayName.toString(),
                            userid = user.uid,
                            email = user.email.toString()
                        ))
            } else {
                // Sign in failed, display a message to the user
                System.out.println("LoginViewModel " + "  Login Not Successfull")
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }

    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        auth = Firebase.auth

        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user
        } catch (e: Exception) {
            null
        }
    }

    // [START auth_with_google]
    fun firebaseAuthWithGoogle(idToken: String) {
        auth = Firebase.auth
        CoroutineScope(Dispatchers.Main).launch {
            val user = signInWithGoogle(idToken)
            if (user != null) {
                // Sign in success, update UI with the signed-in user's information
                // Do something with the user
                System.out.println("LoginViewModel " + "  Login Not Successfull")
                _loginResult.value =
                    LoginResult(
                        success = LoggedInUserView(
                            displayName = user?.displayName.toString(),
                            userid = user.uid,
                            email = user.email.toString()
                        ))
            } else {
                // Sign in failed, display a message to the user
                System.out.println("LoginViewModel " + "  Login Not Successfull")
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    suspend fun signInWithGoogle(idToken: String): FirebaseUser? {
        auth = Firebase.auth

        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            null
        }
    }


    suspend fun signInWithFacebook(token: AccessToken): FirebaseUser? {
        Log.d("LoginViewModel", "handleFacebookAccessToken:$token")

        return try {
            val credential = FacebookAuthProvider.getCredential(token.token)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            null
        }

    }



    fun facebookCallBack() =
        object : FacebookCallback<com.facebook.login.LoginResult> {
            override fun onSuccess(result: com.facebook.login.LoginResult) {
                // Facebook login successful, authenticate with Firebase
                val token = result.accessToken
                val credential = FacebookAuthProvider.getCredential(token.token)
                val mAuth = FirebaseAuth.getInstance()

                mAuth.signInWithCredential(credential)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user: FirebaseUser? = mAuth.currentUser
                            if (user != null) {
                                // User is signed in, you can access their information
                                _loginResult.value =
                                    LoginResult(
                                        success = LoggedInUserView(
                                            displayName = user?.displayName.toString(),
                                            userid = user.uid,
                                            email = user.email.toString()
                                        ))
                            } else
                                _loginResult.value = LoginResult(error = R.string.login_failed)

                            // Do something with the user
                        } else {
                            // If sign in fails, display a message to the user
                            _loginResult.value = LoginResult(error = R.string.login_failed)
                        }
                    }
            }

            override fun onCancel() {
                // Facebook login canceled
            }

            override fun onError(error: FacebookException) {
                // Error occurred during Facebook login

            }

        }

    companion object {
        private const val TAG = "LoginViewModel"
        private const val RC_SIGN_IN = 9001
    }
}