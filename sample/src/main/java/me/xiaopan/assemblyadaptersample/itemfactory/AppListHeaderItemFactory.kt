package me.xiaopan.assemblyadaptersample.itemfactory

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView

import me.xiaopan.assemblyadapter.AssemblyRecyclerItem
import me.xiaopan.assemblyadapter.AssemblyRecyclerItemFactory
import me.xiaopan.assemblyadaptersample.R
import me.xiaopan.assemblyadaptersample.bindView

class AppListHeaderItemFactory : AssemblyRecyclerItemFactory<AppListHeaderItemFactory.AppListHeaderItem>() {

    override fun isTarget(data: Any): Boolean {
        return data is String
    }

    override fun createAssemblyItem(viewGroup: ViewGroup): AppListHeaderItem {
        return AppListHeaderItem(R.layout.list_item_app_list_header, viewGroup)
    }

    inner class AppListHeaderItem(itemLayoutId: Int, parent: ViewGroup) : AssemblyRecyclerItem<String>(itemLayoutId, parent) {
        val textView: TextView by bindView(R.id.text_appListHeaderItem)

        override fun onConfigViews(context: Context) {

        }

        override fun onSetData(i: Int, s: String) {
            textView.text = s
        }
    }
}
