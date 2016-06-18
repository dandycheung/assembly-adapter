>* ``新增``. 新增ContentSetter，可以更方便的设置各种内容，如下
    ```java
    @Override
    protected void onSetData(int position, Game game) {
        getSetter()
                .setImageResource(R.id.image_gameListItem_icon, game.iconResId)
                .setText(R.id.text_gameListItem_name, game.name)
                .setText(R.id.text_gameListItem_like, game.like);
    }
    ```
>* ``修改``. AbstractLoadMoreGroupItemFactory重命名为AssemblyLoadMoreGroupItemFactory
>* ``修改``. AbstractLoadMoreListItemFactory重命名为AssemblyLoadMoreItemFactory
>* ``修改``. AbstractLoadMoreRecyclerItemFactory重命名为AssemblyLoadMoreRecyclerItemFactory