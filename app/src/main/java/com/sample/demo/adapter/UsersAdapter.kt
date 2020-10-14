package com.sample.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.demo.R
import com.sample.demo.databinding.UserItemBinding
import com.sample.demo.model.User

class UsersAdapter(private val context: Context) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var userList: List<User> = ArrayList()

    private var onUserClickListener: OnUserClickListener? = null

    fun setOnUserClickListener(onUserClickListener: OnUserClickListener) {
        this.onUserClickListener = onUserClickListener
    }

    fun setUsersData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItemBinding = UserItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(userItemBinding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(user = userList[position])
    }

    inner class ViewHolder(private val userItemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userItemBinding.root) {

        fun bind(user: User) {

            userItemBinding.tvUserFullName.text = user.name
            userItemBinding.tvUserEmail.text = user.email

            if(user.status == context.getString(R.string.active)) {
                userItemBinding.ivUserStatus.setImageResource(R.drawable.user_active)
            } else {
                userItemBinding.ivUserStatus.setImageResource(R.drawable.user_inactive)
            }

            if (user.gender == context.getString(R.string.male)) {
                userItemBinding.ivUserGender.setImageResource(R.drawable.ic_man_avatar)
            } else {
                userItemBinding.ivUserGender.setImageResource(R.drawable.ic_lady_avatar)
            }

            userItemBinding.root.setOnClickListener {
                onUserClickListener?.onCountryClicked(user)
            }
        }
    }
}

interface OnUserClickListener {
    fun onCountryClicked(user: User)
}