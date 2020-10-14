package com.sample.demo.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.demo.App
import com.sample.demo.R
import com.sample.demo.activity.MainActivity
import com.sample.demo.adapter.OnUserClickListener
import com.sample.demo.adapter.UsersAdapter
import com.sample.demo.api.NetworkApi
import com.sample.demo.databinding.FragmentUsersBinding
import com.sample.demo.dialog.ErrorDialog
import com.sample.demo.dialog.LoadingOverlayDialogController
import com.sample.demo.model.User
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class UsersFragment : Fragment(), Home.Controller, OnUserClickListener {

    companion object {
        fun getInstance(): UsersFragment {
            val fragment = UsersFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentUsersBinding
    private lateinit var userRepo: UserRepo
    private lateinit var userModel: UserModelImpl
    private lateinit var userPresenter: UserPresenterImpl
    private lateinit var usersAdapter: UsersAdapter
    private val service: NetworkApi by inject()

    private var usersList: List<User> = ArrayList()
    private var loadingDialog: LoadingOverlayDialogController? = null
    private var errorDialog: ErrorDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        loadingDialog = LoadingOverlayDialogController(requireActivity(), Runnable { })

        binding.lvUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        userRepo = UserRepoImpl(database = App.instance.roomDatabase, service = service)

        userModel = UserModelImpl(userRepo = userRepo)

        userPresenter = UserPresenterImpl(model = userModel, controller = this@UsersFragment)

        usersAdapter = UsersAdapter(requireContext())
        usersAdapter.setOnUserClickListener(this)
        binding.lvUsers.adapter = usersAdapter
    }

    override fun onResume() {
        super.onResume()
        userPresenter.initialize()
    }

    override fun onShowLoading(showLoading: Boolean) {
        if (showLoading) {
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    override fun onCountriesDataSuccess(usersRes: List<User>, numberOfAttempt: Int) {
        usersList = usersRes
        if (usersList.isNotEmpty()) {
            usersAdapter.setUsersData(usersList)
            onShowLoading(false)
        } else {
            if (usersList.isEmpty() && numberOfAttempt == 1) {
                onShowLoading(true)
            } else {
                onShowLoading(false)
            }
        }
    }

    override fun onCountriesDataFail(errorMsg: String, numberOfAttempt: Int) {
        onShowLoading(false)
        errorDialog = ErrorDialog(requireActivity(), getString(R.string.errorDialogTitle), errorMsg, Runnable { })
        errorDialog?.show()
    }

    override fun onCountryClicked(user: User) {
        if(activity is MainActivity) {
            (activity as MainActivity).setUserInMetaData(user)
        }
    }

    fun filterData(position: Int) {
        when(position) {
            0 -> {
                Collections.sort(usersList) { o1, o2 ->
                    o2.name.compareTo(o1.name)
                }
            }
            1 -> {
                Collections.sort(usersList) { o1, o2 ->
                    o2.email.compareTo(o1.email)
                }
            }
            2 -> {
                Collections.sort(usersList) { o1, o2 ->
                    o2.createdAt.compareTo(o1.createdAt)
                }
            }
            else -> {
                Collections.sort(usersList) { o1, o2 ->
                    o2.name.compareTo(o1.name)
                }
            }
        }
        usersAdapter.setUsersData(usersList)
    }
}