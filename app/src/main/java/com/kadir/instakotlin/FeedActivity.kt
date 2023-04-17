package com.kadir.instakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kadir.instakotlin.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var feedAdapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        postArrayList = ArrayList<Post>()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter= Adapter(postArrayList)
        binding.recyclerView.adapter=feedAdapter

    }
    private fun getData(){
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error !=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (value!= null){
                    if (!value.isEmpty){
                        val documents =value.documents

                        postArrayList.clear()
                        for (document in documents){

                            val comment = document.get("comment") as String
                            val useremail=document.get("userEmail") as String
                            val downloadUrl= document.get("downloadUrl") as String
                            println(comment)

                            val post = Post(useremail,comment,downloadUrl,)
                            postArrayList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.upload){
            val intent = Intent(this,UploadActivity::class.java)
            startActivity(intent)
            finish()
        }else if (item.itemId == R.id.profile){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else if (item.itemId == R.id.home){
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

}