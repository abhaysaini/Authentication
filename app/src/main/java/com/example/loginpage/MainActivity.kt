package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {
            if (check()) {
                val email1 = email.text.toString()
                val password = pass.text.toString()
                auth.signInWithEmailAndPassword(email1, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, Logged_In::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }


        register_button.setOnClickListener {
            var intent1 = Intent(this, Register::class.java)
            startActivity(intent1)
        }
    }

    private fun check(): Boolean {
        if (email.text.toString().trim { it <= ' ' }.isNotEmpty() && pass.text.toString()
                .trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}