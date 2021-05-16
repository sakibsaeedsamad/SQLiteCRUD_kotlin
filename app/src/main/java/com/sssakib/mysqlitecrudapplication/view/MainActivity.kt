package com.sssakib.mysqlitecrudapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.sssakib.mysqlitecrudapplication.R
import com.sssakib.mysqlitecrudapplication.adapter.ListOfUserAdapter
import com.sssakib.mysqlitecrudapplication.model.DatabaseHelper
import com.sssakib.mysqlitecrudapplication.model.User
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListOfUserAdapter.RowClickListener {

    lateinit var listOfUserAdapter: ListOfUserAdapter
    lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)

        registerButton.setOnClickListener {
            val intent = Intent(
                this,
                RegisterActivity::class.java
            )
            startActivity(intent)
        }

        viewCustomerButton.setOnClickListener {

            displayUser()
        }
    }

    private fun displayUser() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            listOfUserAdapter = ListOfUserAdapter(this@MainActivity)
            adapter = listOfUserAdapter
        }
        val list = databaseHelper.readData()
        listOfUserAdapter.setListData(list as ArrayList<User>)
        listOfUserAdapter.notifyDataSetChanged()
    }

    override fun onItemClickListener(user: User) {

        val uId = user.id
        val uName = user.name
        val uPhone = user.phone
        val uGender = user.gender
        val uLocation = user.location
        val uImage = user.image


        val dialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.activity_dialog)

        dialog.userUpdateButton.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", uId)
            intent.putExtra("name", uName)
            intent.putExtra("phone", uPhone)
            intent.putExtra("gender", uGender)
            intent.putExtra("location", uLocation)
            intent.putExtra("image", uImage)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.userDeleteButton.setOnClickListener {

            databaseHelper.deleteUser(uId)
            dialog.dismiss()
            displayUser()
        }

        dialog.show()

    }


}