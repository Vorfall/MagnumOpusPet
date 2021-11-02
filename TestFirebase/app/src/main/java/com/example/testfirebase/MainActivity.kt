package com.example.testfirebase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Transformations.map
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.DataSnapshot
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var adapter: ArrayAdapter<String>
        var listData: List<String>

        setContentView(R.layout.activity_main)
        val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("TestMessage")
        var edName : EditText = findViewById(R.id.name)
        var edText : EditText = findViewById(R.id.text)

        var buttReadBd : Button = findViewById(R.id.ReadBD)

        buttReadBd.setOnClickListener {

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {


                    for (dataSnapshot in dataSnapshot.children) {
                        val message = dataSnapshot.child("name").value as String?
                        val sender = dataSnapshot.child("text").value as String?
                    }

                    val text = dataSnapshot.value.toString()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
            dbRef.addValueEventListener(postListener)

        }

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