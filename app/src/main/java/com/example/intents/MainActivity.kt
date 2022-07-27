package com.example.intents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener{
            var newAct= Intent(this,MainActivity2::class.java)
            newAct.putExtra("user1","Satyam")
            startActivity(newAct)
        }
        emailbtn.setOnClickListener{
            val email=editText.text.toString()
            val newAct=Intent()
            newAct.action=Intent.ACTION_SENDTO
            newAct.data= Uri.parse("mailto:$email")
            startActivity(newAct)
        }
        browsebtn.setOnClickListener {
            val address=editText.text.toString()
            val newAct=Intent();
            newAct.action=Intent.ACTION_VIEW
            newAct.data= Uri.parse("https://$address")
            startActivity(newAct)
        }
        dialbtn.setOnClickListener {
            val number=editText.text.toString()
            if(number.length<10)
            {
                Toast.makeText(this,"Invalid Number",Toast.LENGTH_SHORT).show()
            }
            else {
                val newAct = Intent()
                newAct.action = Intent.ACTION_DIAL
                newAct.data = Uri.parse("tel:$number")
                startActivity(newAct)
            }
        }
    }
}