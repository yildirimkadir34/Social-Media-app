package com.kadir.instakotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadir.instakotlin.databinding.RecyclerRowBinding
import com.squareup.picasso.Picasso

class Adapter (private val postList: ArrayList<Post>): RecyclerView.Adapter<Adapter.PostHolder>(){
    class PostHolder(val  binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding= RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerusername.text = postList.get(position).email
        holder.binding.recyclercommenttext.text= postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageview)

    }

    override fun getItemCount(): Int {
        return postList.size
    }


}