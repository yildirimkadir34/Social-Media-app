package com.kadir.instakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kadir.instakotlin.databinding.ActivityFeedBinding
import com.kadir.instakotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        auth= FirebaseAuth.getInstance()


        val currentUser=auth.currentUser

        if (currentUser != null){
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.textView.setOnClickListener{
            val intent = Intent(this@MainActivity,Register::class.java)
            startActivity(intent)
        }

    }

    fun signinclick(view: View){

        val email = binding.email.text.toString()
        val password= binding.password.text.toString()


        if(email.equals("") || password.equals("") ) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent=Intent(this@MainActivity,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }



}