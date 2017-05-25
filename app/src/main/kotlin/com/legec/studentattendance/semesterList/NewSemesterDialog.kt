package com.legec.studentattendance.semesterList

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R


class NewSemesterDialog(val createSemesterCallback: (String, String) -> Unit, val subjectName: String, val semesterName: String) : DialogFragment() {
    @BindView(R.id.subject_name_tv)
    lateinit var subjectTV: TextView
    @BindView(R.id.semester_name_tv)
    lateinit var semesterTV: TextView

    constructor(createSemesterCallback: (String, String) -> Unit) : this(createSemesterCallback, "", "")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.new_semester_dialog_view, null)
        ButterKnife.bind(this, view)
        builder.setView(view)
                .setPositiveButton("OK", { _, _ -> })
                .setNegativeButton("Cancel", { dialog, _ -> dialog.cancel() })
        subjectTV.text = subjectName
        semesterTV.text = semesterName
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        val D = dialog as AlertDialog
        val positive = D.getButton(Dialog.BUTTON_POSITIVE)
        positive.setOnClickListener {
            if (subjectTV.text.isEmpty() || semesterTV.text.isEmpty()) {
                Toast.makeText(activity, "You've to fill both text fields", Toast.LENGTH_SHORT).show()
            } else {
                createSemesterCallback(subjectTV.text.toString(), semesterTV.text.toString())
                dialog.cancel()
            }
        }
    }
}