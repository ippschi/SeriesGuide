package com.battlelancer.seriesguide.ui.shows

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.battlelancer.seriesguide.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Confirms before making all hidden shows visible again.
 */
class RemoveCurrentlyDisplayedShowsDialogFragment : AppCompatDialogFragment() {

    private lateinit var dialog: AlertDialog
    private val model: ShowsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ShowsViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage(getDialogMessage())
            .setPositiveButton(R.string.action_shows_remove_all_visible_confirm) { _, _ ->
                model.removeCurrentlyDisplayedShows()
                dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            updateVisibleShowCountAsync()
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private suspend fun updateVisibleShowCountAsync() {
        val count = model.showItemsLiveData.value?.size
        withContext(Dispatchers.Main) {
            dialog.setMessage(getDialogMessage(count))
        }
    }

    private fun getDialogMessage(count: Int? = null) =
        getString(
            R.string.description_remove_all_visible_format,
            count ?: "?"
        )
}
