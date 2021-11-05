package com.example.testfirebase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        var edName: EditText = findViewById(R.id.name)
        var edText: EditText = findViewById(R.id.text)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerviewHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

      //  var txtHistory: TextView = findViewById(R.id.History)

        var rvHistrry: RecyclerView = findViewById(R.id.recyclerviewHistory)
        rvHistrry.layoutManager = LinearLayoutManager(this)

        var buttReadBd: Button = findViewById(R.id.ReadBD)

        buttReadBd.setOnClickListener {

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {


                    var historyMSG: MutableList<Message> = arrayListOf()

                    for (dataSnapshot in dataSnapshot.children) {
                        historyMSG.add(
                            Message(
                                dataSnapshot.child("name").value.toString(),
                                dataSnapshot.child("text").value.toString()
                            )
                        )
                    }
                    DisplayMessageHistoryOnTheScreen(historyMSG)
                }
                fun DisplayMessageHistoryOnTheScreen(historyMSG: MutableList<Message>) {
               /*     var txtTemp =""
                    historyMSG.forEach { txtTemp +=  it.name.toString() + ": " + it.text.toString() + "\n" }
                    txtHistory.setText(txtTemp)*/


                    recyclerView.adapter =  MessageAdapter(historyMSG)
                }

                override fun onCancelled(databaseError: DatabaseError) {

                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
            dbRef.addValueEventListener(postListener)

        }


        var save: Button = findViewById(R.id.save)
        save.setOnClickListener {


            var name = edName.text.toString()
            var text = edText.text.toString()
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(text)) {
                var test1: Message = Message(name, text)
                dbRef.push().setValue(test1)
                edText.setText("")
            } else {
                Toast.makeText(this, "Oops something empty", Toast.LENGTH_SHORT).show()
            }

        }


    }

}
