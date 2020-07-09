package com.battlelancer.seriesguide.ui.streams

import android.content.Context
import androidx.collection.SparseArrayCompat
import com.battlelancer.seriesguide.ui.shows.ShowTools

class EpisodeHistoryAdapter(
    context: Context,
    itemClickListener: OnItemClickListener
) : BaseHistoryAdapter(context, itemClickListener) {

    private var localShowPosters: SparseArrayCompat<String>? = null

    override fun submitList(list: MutableList<TraktEpisodeHistoryLoader.HistoryItem>?) {
        // TODO This should be done async (e.g. in view model).
        localShowPosters = ShowTools.getSmallPostersByTvdbId(context)
        super.submitList(list)
    }

    override fun onBindHistoryItemViewHolder(
        holder: HistoryItemViewHolder,
        item: TraktEpisodeHistoryLoader.HistoryItem
    ) {
        holder.bindToEpisode(item.historyEntry, localShowPosters)
    }
}