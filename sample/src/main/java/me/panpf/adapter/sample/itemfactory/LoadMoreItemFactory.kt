package me.panpf.adapter.sample.itemfactory

import android.view.View
import android.view.ViewGroup

import me.panpf.adapter.AssemblyLoadMoreListItemFactory
import me.panpf.adapter.OnListLoadMoreListener
import me.panpf.adapter.sample.R
import me.panpf.adapter.sample.bindView

class LoadMoreItemFactory(eventListenerList: OnListLoadMoreListener) : AssemblyLoadMoreListItemFactory(eventListenerList) {

    override fun createAssemblyItem(parent: ViewGroup): AssemblyLoadMoreListItem<*> {
        return LoadMoreItem(R.layout.list_item_load_more, parent)
    }

    inner class LoadMoreItem(itemLayoutId: Int, parent: ViewGroup) : AssemblyLoadMoreListItem<Int>(itemLayoutId, parent) {
        val loadingView: View by bindView(R.id.text_loadMoreListItem_loading)
        val errorView: View by bindView(R.id.text_loadMoreListItem_error)
        val endView: View by bindView(R.id.text_loadMoreListItem_end)

        override fun getErrorRetryView(): View {
            return errorView
        }

        override fun showLoading() {
            loadingView.visibility = View.VISIBLE
            errorView.visibility = View.INVISIBLE
            endView.visibility = View.INVISIBLE
        }

        override fun showErrorRetry() {
            loadingView.visibility = View.INVISIBLE
            errorView.visibility = View.VISIBLE
            endView.visibility = View.INVISIBLE
        }

        override fun showEnd() {
            loadingView.visibility = View.INVISIBLE
            errorView.visibility = View.INVISIBLE
            endView.visibility = View.VISIBLE
        }
    }
}
