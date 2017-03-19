package com.legec.studentattendance.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.legec.studentattendance.R


class NewSemesterDialog(val createSemesterCallback: (String, String) -> Unit) : DialogFragment() {
    @BindView(R.id.subject_name_tv)
    lateinit var subjectTV : TextView
    @BindView(R.id.semester_name_tv)
    lateinit var semesterTV : TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.new_semester_dialog_view, null)
        ButterKnife.bind(this, view)
        builder.setView(view)
                .setPositiveButton("OK", { dialog, _ ->
                    createSemesterCallback(subjectTV.text.toString(), semesterTV.text.toString())
                    dialog.cancel()
                })
                .setNegativeButton("Cancel", { dialog, _ ->
                    dialog.cancel()
                })
        return builder.create()
    }
}