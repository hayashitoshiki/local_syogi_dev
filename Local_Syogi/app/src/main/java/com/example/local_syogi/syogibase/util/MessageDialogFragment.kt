package com.example.local_syogi.syogibase.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

class MessageDialogFragment : DialogFragment() {
    enum class DialogResult {
        OK,
        Cancel
    }

    private var title = ""
    private var message = ""
    private var positiveText = ""
    private var negativeText = ""

    private val channel = BroadcastChannel<DialogResult>(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(ARG_TITLE)
            message = it.getString(ARG_MESSAGE)
            positiveText = it.getString(ARG_POSITIVE)
            negativeText = it.getString(ARG_NEGATIVE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { _, _ -> GlobalScope.launch { channel.send(DialogResult.OK) } }
            .setNegativeButton(negativeText) { _, _ -> GlobalScope.launch { channel.send(DialogResult.Cancel) } }
        return builder.create()
    }

    suspend fun showAndSuspend(fm: FragmentManager, tag: String? = null): DialogResult {
        show(fm, tag)
        return channel.openSubscription().receive()
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_POSITIVE = "positive"
        private const val ARG_NEGATIVE = "negative"

        @JvmStatic
        fun newInstance(title: String, message: String, positiveText: String = "OK", negativeText: String = "キャンセル") =
            MessageDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                    putString(ARG_POSITIVE, positiveText)
                    putString(ARG_NEGATIVE, negativeText)
                }
            }
    }
}