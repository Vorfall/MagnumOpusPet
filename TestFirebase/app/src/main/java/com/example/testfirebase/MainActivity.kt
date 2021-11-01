package com.example.testfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("TestGroup")
        var but : Button = findViewById(R.id.button)
        but.setOnClickListener{
            var id = dbRef.getKey()
            var text = "Test TEXT".toString()
            var id1 = id ?: "0"
            var test1: TestData = TestData(id1,text)
            dbRef.push().setValue(test1)
        }


    }
}