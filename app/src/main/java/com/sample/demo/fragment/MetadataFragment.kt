package com.sample.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sample.demo.R
import com.sample.demo.databinding.FragmentMetadataBinding
import com.sample.demo.model.User

class MetadataFragment : Fragment() {

    companion object {
        fun getInstance(): MetadataFragment {
            val fragment = MetadataFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentMetadataBinding

    private var userLiveData: MutableLiveData<User> = MutableLiveData()

    fun setUserInMetaData(user: User) {
        this.userLiveData.value = user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMetadataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        userLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tvUserFullName.text = it.name
                binding.tvUserEmail.text = it.email

                if (it.gender == getString(R.string.male)) {
                    binding.ivUserGender.setImageResource(R.drawable.ic_man_avatar)
                } else {
                    binding.ivUserGender.setImageResource(R.drawable.ic_lady_avatar)
                }
                
                binding.tvUserLastUpdated.text = "${getString(R.string.last_updated)}  ${it.updatedAt}"
            }
        })
    }
}