package com.example.mealy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.mealy.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var storedVerificationId = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sendButton.setOnClickListener {
            if (!TextUtils.isEmpty(binding.phoneNumberInput.text.toString())) {
                val phoneNumber = binding.phoneNumberInput.text.toString()
                verifyPhoneNumber(phoneNumber)
                println("phonenumber $phoneNumber")
                binding.apply {
                    sendButton.isVisible = false
                    phoneNumberInput.isVisible = false
                    phoneNumberInputLayout.isVisible = false
                    otpInput.isVisible = true
                    otpInputLayout.isVisible = true
                    verifyButton.isVisible = true
                }
            }
        }


        binding.verifyButton.setOnClickListener {
            if (!TextUtils.isEmpty(binding.otpInput.text.toString())) {
                val otp = binding.otpInput.text.toString()
                println("OTP $otp")
                verifyCode(otp)
            }
        }



        mAuth = Firebase.auth

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                if (exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@LoginActivity, "Invalid Request", Toast.LENGTH_SHORT).show()
                } else if (exception is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        this@LoginActivity,
                        "SMS quota for this project has exceeded",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
            }

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this@LoginActivity, "Invalid OTP", Toast.LENGTH_SHORT).show()

                    }
                }
            }
    }

    private fun verifyPhoneNumber(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    private fun verifyCode(otp: String) {

        try {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
            signInWithPhoneAuthCredential(credential)
        } catch (e: Exception) {
            if (e is IllegalArgumentException) {
                Toast.makeText(this, "Cannot create credential", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
        }

    }

    fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}