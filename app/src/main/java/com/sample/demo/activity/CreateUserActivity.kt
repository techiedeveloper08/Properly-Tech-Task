package com.sample.demo.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.sample.demo.App
import com.sample.demo.R
import com.sample.demo.api.NetworkApi
import com.sample.demo.databinding.ActivityCreateUserBinding
import com.sample.demo.dialog.ErrorDialog
import com.sample.demo.fragment.user.UserRepo
import com.sample.demo.fragment.user.UserRepoImpl
import com.sample.demo.model.User
import com.sample.demo.utils.Validation
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import kotlin.random.Random


class CreateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUserBinding

    private lateinit var userRepo: UserRepo
    private var errorDialog: ErrorDialog? = null

    private val service: NetworkApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        title = getString(R.string.create_user_title)

        userRepo = UserRepoImpl(database = App.instance.roomDatabase, service = service)

        binding.btnCreateUser.setOnClickListener {
            if (isValid()) {

                val radioButton = findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
                val status = if (binding.userStatus.isChecked) binding.userStatus.textOn.toString() else binding.userStatus.textOff.toString()

                val user = User(
                    id = Random(4).nextInt(),
                    name = binding.etName.text.toString(),
                    email = binding.etEmailAddress.text.toString(),
                    gender = radioButton.text.toString(),
                    status = status,
                    updatedAt = "",
                    createdAt = "",
                    uploadedStatus = 0)

                val single: Single<Boolean> = ReactiveNetwork.checkInternetConnectivity()
                single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { isConnectedToInternet ->
                        if(isConnectedToInternet) {
                            //TODO online add user
                            userRepo.onCreateUser(user, {
                                Toast.makeText(this, getString(R.string.user_created_successfully), Toast.LENGTH_SHORT).show()
                            }, {
                                errorDialog = ErrorDialog(this, getString(R.string.errorDialogTitle), it.localizedMessage, Runnable { })
                                errorDialog?.show()
                            })
                        } else {
                            //TODO Offline add user
                            App.instance.roomDatabase.userDao().insert(user)
                            Toast.makeText(this, getString(R.string.user_created_successfully), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, getString(R.string.data_validation_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(): Boolean {
        val isName = binding.etName.text.toString().isNotBlank()
        val isEmail = binding.etEmailAddress.text.toString()
            .isNotBlank() && Validation.isValidEmail(binding.etEmailAddress.text.toString())
        val isGender = binding.radioGroup.checkedRadioButtonId != -1

        return isName && isEmail && isGender
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}