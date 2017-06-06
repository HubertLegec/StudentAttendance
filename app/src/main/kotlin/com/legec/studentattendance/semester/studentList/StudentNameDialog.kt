package com.legec.studentattendance.semester.studentList

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R


class StudentNameDialog(val callback: (String) -> Unit, val name: String) : DialogFragment() {
    @BindView(R.id.student_name_edit)
    lateinit var nameTV: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.student_name_dialog, null)
        ButterKnife.bind(this, view)
        builder.setView(view)
                .setPositiveButton("OK", { _, _ -> })
                .setNegativeButton("Cancel", { dialog, _ -> dialog.cancel() })
        nameTV.text = name
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        val D = dialog as AlertDialog
        val positive = D.getButton(Dialog.BUTTON_POSITIVE)
        positive.setOnClickListener {
            if (nameTV.text.isEmpty()) {
                Toast.makeText(activity, "Name must not be empty", Toast.LENGTH_SHORT).show()
            } else {
                callback(nameTV.text.toString())
                dialog.cancel()
            }
        }
    }
}