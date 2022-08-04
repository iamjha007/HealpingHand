package com.example.intents

import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_SPEECH_INPUT=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        btn.setOnClickListener{
//            var newAct= Intent(this,MainActivity2::class.java)
//            newAct.putExtra("user1","Satyam")
//            startActivity(newAct)
//        }
        micbtn.setOnClickListener{
            speak()
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
        msgbtn.setOnClickListener{
            val number=editText.text.toString()
            if(number.isEmpty()){
                startWhatsapp("9899506447")
            }else if(number.isDigitsOnly()){
                startWhatsapp(number)
            }else{
                Toast.makeText(this,"Please Check the Number", Toast.LENGTH_SHORT).show()
            }
        }
        youtubebtn.setOnClickListener{
            val name=editText.text.toString();
            val newAct=Intent()
            newAct.action=Intent.ACTION_VIEW
            newAct.data=Uri.parse("vnd.youtube://$name")
            startActivity(newAct);

        }

    }

    private fun speak() {
       val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak To Type")
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT)
        }catch (e:Exception){
           Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data )
        when(requestCode){
            REQUEST_CODE_SPEECH_INPUT->{
                if(resultCode== Activity.RESULT_OK && null!=data){
                    val result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    editText.setText(result?.get(0) ?: " didnt hear that")
                }
            }
        }
    }

    private fun startWhatsapp(num: String) {
        var number=num
        val intent=Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        if(number[0]=='+'){
            number=number.substring(1)
        }else if(number.length==10){
            number="91"+number
        }
        intent.data= Uri.parse("https://wa.me/$number")
        if(packageManager.resolveActivity(intent,0)!=null){
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"whatsapp not installed",Toast.LENGTH_SHORT).show()
        }
        finish()


    }
}