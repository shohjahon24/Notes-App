package com.shohjahon.notesapp.presentation.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.shohjahon.notesapp.databinding.DialogBaseBinding

class BaseDialog(
    val context: Context
) {
    private val dialog = AlertDialog.Builder(context).create()
    private var binding: DialogBaseBinding? =
        DialogBaseBinding.inflate(LayoutInflater.from(context), null, false)
    private val binding1 get() = binding!!
    fun setYesListener(block: (AlertDialog) -> Unit): BaseDialog {
        binding1.yesBtn.setOnClickListener {
            block.invoke(dialog)
        }
        return this
    }

    fun show() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    init {
        dialog.setView(binding?.root)
        dialog.setOnDismissListener {
            binding = null
        }
        dialog.setCancelable(true)
        binding1.noBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
}