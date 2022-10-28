package com.example.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.util.jar.Attributes

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        continue_button.setOnClickListener {
            if(check()){
                var regEmail = register_email.text.toString()
                var regPass = register_pass.text.toString()
                var regPhone = register_phone.text.toString()
                var regName = register_name.text.toString()

                val user = hashMapOf(
                    "Name" to regName,
                    "Phone" to regPhone,
                    "Email" to regEmail
                )

                val Users = db.collection("USERS")
                val query = Users.whereEqualTo("Email",regEmail).get()
                    .addOnSuccessListener {
                        tasks->
                        if(tasks.isEmpty){
                                auth.createUserWithEmailAndPassword(regEmail,regPass)
                                    .addOnCompleteListener(this){
                                        task->
                                        if(task.isSuccessful){
                                            Users.document(regEmail).set(user)
                                            var intent = Intent(this,Logged_In::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        else{
                                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                        }
                        else{
                            Toast.makeText(this, "User Already Register", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun check(): Boolean {
        if (register_name.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            register_email.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            register_pass.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            register_phone.text.toString().trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}


//auth.createUserWithEmailAndPassword(regEmail,regPass).addOnCompleteListener(this){
//    task->
//    if(task.isSuccessful){
//        Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show()
//        var intent3 = Intent(this,MainActivity::class.java)
//        startActivity(intent3)
//    }
//    else{
//        Toast.makeText(this, "Relod the Page", Toast.LENGTH_SHORT).show()
//    }
//}