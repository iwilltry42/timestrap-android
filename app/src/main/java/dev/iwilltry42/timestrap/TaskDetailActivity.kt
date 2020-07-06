package dev.iwilltry42.timestrap

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_TASK_NAME = "dev.iwilltry42.timestrap.TASK_NAME"

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Custom Welcome if User is logged in
        findViewById<TextView>(R.id.text_task_title).text = intent.getStringExtra(EXTRA_TASK_NAME)

    }
}