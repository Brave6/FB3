package com.e.firebase.repository

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.firebase.databinding.ActivityHomeBinding
import com.e.firebase.datamodel.TaskModel
import com.e.firebase.viewmodel.TaskAdapter
import com.google.firebase.firestore.FirebaseFirestore

class FireStore:AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()




    }



    //for laoding all task from server
    fun loadAllData(userID: String) {

        val taksList = ArrayList<TaskModel>()

        var ref = db.collection("all_tasks")
        ref.whereEqualTo("userID", userID)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        Toast.makeText(this@FireStore, "No Task Found", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }
                    for (doc in it) {
                        val taskModel = doc.toObject(TaskModel::class.java)
                        taksList.add(taskModel)
                    }

                    binding.rvToDoList.apply {
                        layoutManager =
                                LinearLayoutManager(this@FireStore, RecyclerView.VERTICAL, false)
                        adapter = TaskAdapter(taksList, this@FireStore)
                    }

                }
    }
}

