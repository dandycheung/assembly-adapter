package me.panpf.adapter.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fm_pager.*
import me.panpf.adapter.pager.AssemblyFragmentStatePagerAdapter
import me.panpf.adapter.sample.R
import me.panpf.adapter.sample.bean.Header
import me.panpf.adapter.sample.bean.Text
import me.panpf.adapter.sample.item.HeaderFragmentItemFactory
import me.panpf.adapter.sample.item.ImageFragmentItemFactory
import me.panpf.adapter.sample.item.TextFragmentItemFactory

class ViewPagerFragmentStatePagerAdapterSampleFragment : BaseFragment() {

    override fun onUserVisibleChanged(isVisibleToUser: Boolean) {
        val attachActivity = activity
        if (isVisibleToUser && attachActivity is AppCompatActivity) {
            attachActivity.supportActionBar?.subtitle = "ViewPager - FragmentStatePagerAdapter"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fm_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val header = Header("我是小额头呀！", "http://img4.imgtn.bdimg.com/it/u=16705507,1328875785&fm=21&gp=0.jpg")
        val dataArray = arrayOf(
                "http://image-qzone.mamaquan.mama.cn/upload/2015/03/20/9d4e2fda0e904bcca07a_w300X405_w196X264.jpeg",
                "http://img1.imgtn.bdimg.com/it/u=2055412405,1351161078&fm=21&gp=0.jpg",
                Text("华丽的分割线"),
                "http://img3.cache.netease.com/photo/0026/2013-06-06/90MP0B4N43AJ0026.jpg",
                "http://img3.imgtn.bdimg.com/it/u=533822629,3189843728&fm=21&gp=0.jpg"
        )
        val footer = Header("我是小尾巴呀！", "http://img2.imgtn.bdimg.com/it/u=4104440447,1888517305&fm=21&gp=0.jpg")

        val adapter = AssemblyFragmentStatePagerAdapter(childFragmentManager, dataArray)
        adapter.addHeaderItem(HeaderFragmentItemFactory(), header)
        adapter.addItemFactory(ImageFragmentItemFactory())
        adapter.addItemFactory(TextFragmentItemFactory())
        adapter.addFooterItem(HeaderFragmentItemFactory(), footer)
        pagerFm_pager.adapter = adapter
    }
}
