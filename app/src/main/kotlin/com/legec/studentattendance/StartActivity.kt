package com.legec.studentattendance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.legec.studentattendance.semesterList.SemesterListActivity


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Handler().postDelayed({
            val intent = Intent(this, SemesterListActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}

