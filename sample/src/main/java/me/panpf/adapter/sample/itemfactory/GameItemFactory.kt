package me.panpf.adapter.sample.itemfactory

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import me.panpf.adapter.AssemblyItem
import me.panpf.adapter.AssemblyItemFactory
import me.panpf.adapter.sample.R
import me.panpf.adapter.sample.bean.Game
import me.panpf.adapter.sample.bindView

class GameItemFactory(context: Context) : AssemblyItemFactory<GameItemFactory.GameItem>() {

    init {
        setOnViewClickListener(R.id.image_gameListItem_icon) { view, position, positionInPart, data ->
            Toast.makeText(context, "瞅这游戏这臭逼样！", Toast.LENGTH_SHORT).show()
        }
        setOnViewClickListener(R.id.text_gameListItem_name) { view, position, positionInPart, data ->
            Toast.makeText(context, "原来你叫" + (data as Game).name + "啊！", Toast.LENGTH_SHORT).show()
        }
        setOnViewClickListener(R.id.text_gameListItem_like) { view, position, positionInPart, data ->
            Toast.makeText(context, "我也" + (data as Game).like + "这游戏！", Toast.LENGTH_SHORT).show()
        }
    }

    override fun isTarget(data: Any?): Boolean {
        return data is Game
    }

    override fun createAssemblyItem(parent: ViewGroup): GameItem {
        return GameItem(R.layout.list_item_game, parent)
    }

    inner class GameItem(itemLayoutId: Int, parent: ViewGroup) : AssemblyItem<Game>(itemLayoutId, parent) {
        private val iconImageView: ImageView by bindView(R.id.image_gameListItem_icon)
        private val nameTextView: TextView by bindView(R.id.text_gameListItem_name)
        private val likeTextView: TextView by bindView(R.id.text_gameListItem_like)

        override fun onConfigViews(context: Context) {
        }

        override fun onSetData(position: Int, game: Game?) {
            game ?: return
            iconImageView.setImageResource(game.iconResId)
            nameTextView.text = game.name
            likeTextView.text = game.like
        }
    }
}
