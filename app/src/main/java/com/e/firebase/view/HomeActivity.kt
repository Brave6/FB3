package com.e.firebase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.firebase.databinding.ActivityHomeBinding
import com.e.firebase.datamodel.TaskModel
import com.e.firebase.viewmodel.TaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        //is user is alreay Login
        if (auth.currentUser == null) {
            goToLogin()
        }
        var currentUser = auth.currentUser



        binding.btnAdd.setOnClickListener {
            var task = binding.etTask.text.toString().trim()

            if (task.isEmpty()) {
                binding.etTask.setError("Task Can not be empty!")
                return@setOnClickListener
            }

            val taskData = TaskModel(task, false, currentUser!!.uid.toString())
            db.collection("all_tasks")
                .add(taskData)
                .addOnSuccessListener {
                    Toast.makeText(this@HomeActivity, "Task Saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@HomeActivity, "Task Not Saved", Toast.LENGTH_SHORT).show()
                    Log.e("HA", "Error saving : Err :" + it.message)
                }


        }



        binding.btnLogout.setOnClickListener {
            //for logout
            auth.signOut()
            goToLogin()
        }
    }

    fun goToLogin() {
        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        finish()
    }

    }

