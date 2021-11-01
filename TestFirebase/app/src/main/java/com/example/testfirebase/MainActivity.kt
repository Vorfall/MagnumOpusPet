package com.example.testfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("TestMessage")
        var edName : EditText = findViewById(R.id.name)
        var edText : EditText = findViewById(R.id.text)
        var save : Button = findViewById(R.id.save)
        save.setOnClickListener{

            var name = edName.text.toString()
            var text = edText.text.toString()
            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(text)){
                var test1: Message = Message(name,text)
                dbRef.push().setValue(test1)
                edText.setText("")
            }
            else{
                Toast.makeText(this, "Oops something empty", Toast.LENGTH_SHORT).show()
            }

        }


    }
}