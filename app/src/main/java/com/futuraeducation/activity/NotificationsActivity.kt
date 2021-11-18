package com.futuraeducation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.futuraeducation.R
import kotlinx.android.synthetic.main.content_notification.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Notifications"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        showEmptyMessage()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showEmptyMessage(){
        stateful.showEmpty()
        stateful.setEmptyText("No notification available")
        stateful.setEmptyImageResource(R.drawable.icon_error)
    }
}