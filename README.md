# AssemblyAdapter

[![Android Arsenal][android_arsenal_icon]][android_arsenal_link]
[![Release][release_icon]][release_link]

AssemblyAdapter 是 Android 上的一个 Adapter 库，有了它你就不用再写 Adapter 了，支持组合式多 Item、支持添加 header 和 footer、支持加载更多尾巴

### 特性
>* ``Item 一处定义处处使用``. 你只需为每一个 item layout 写一个 ItemFactory，然后到处使用 ItemFactory 即可
>* ``便捷的组合式多 Item``. 可以组合式使用多个 ItemFactory，每个 ItemFactory 代表一种 itemType
>* ``支持 header 和 footer``. 使用 AssemblyAdapter 可以让 ExpandableListView、GridView、RecyclerView、ViewPager 等也支持 header 和 footer
>* ``随意隐藏、显示 header 或 footer``. header 和 footer 还支持通过其 setEnabled(boolean) 方法控制隐藏或显示
>* ``自带加载更多功能``. 自带滑动到列表底部触发加载更多功能，你只需定义一个专门用于加载更多的 ItemFactory 即可
>* ``支持常用 Adapter``. 支持 BaseAdapter、RecyclerView.Adapter、BaseExpandableListAdapter、PagerAdapter、FragmentPagerAdapter 和 FragmentStatePagerAdapter，涵盖了 Android 开发中常用的大部分 Adapter
> * ``支持 SpanSize``. AssemblyRecyclerItemFactory 支持 SpanSize，可轻松实现横跨多列功能
>* ``无性能损耗``. 没有使用任何反射相关的技术，因此无须担心性能问题

### 使用指南

#### 1. 从 JCenter 导入 AssemblyAdapter
```groovy
dependencies {
    compile 'me.xiaopan:assemblyadapter:lastVersionName'
}
```
`lastVersionName`：[![Release Version](https://img.shields.io/github/release/xiaopansky/AssemblyAdapter.svg)](https://github.com/xiaopansky/AssemblyAdapter/releases)`（不带v）`

`最低兼容API 7`

#### 2. 简述
共有 6 种 Adapter：

|Adapter|父类|适用于|支持功能|
|:---|:---|:---|:---|
|AssemblyAdapter|BaseAdapter|ListView、GridView、Spinner、Gallery|多Item、header和footer、加载更多|
|AssemblyRecyclerViewAdapter|RecyclerView.Adapter|RecyclerView|多Item、header和footer、加载更多|
|AssemblyExpandableAdapter|BaseExpandableListAdapter|ExpandableListView|多Item、header和footer、加载更多|
|AssemblyPagerAdapter|PagerAdapter|ViewPager + View|多Item、header和footer|
|AssemblyFragmentPagerAdapter|FragmentPagerFragment|ViewPager + Fragment|多Item、header和footer|
|AssemblyFragmentStatePagerAdapter|FragmentStatePagerFragment|ViewPager + Fragment|多Item、header和footer|

`接下来以 AssemblyAdapter 为例讲解具体的用法，其它 Adapter 你只需照葫芦画瓢，然后 ItemFactory 和 Item 继承各自专属的类即可，详情请参考 sample 源码`

AssemblyAdapter 分为三部分：
>* Adapter：负责维护数据、itemType 以及加载更多的状态
>* ItemFactory：负责匹配数据和创建 Item
>* Item：负责创建 itemView、设置数据、设置并处理事件

AssemblyAdapter 与其它万能 Adapter 最根本的不同就是其把 item 相关的处理全部定义在了一个 ItemFactory 类里面，在使用的时候只需通过 Adapter 的 addItemFactory(AssemblyItemFactory) 方法将 ItemFactory 加到 Adapter 中即可

这样的好处就是真正做到了一处定义处处使用，并且可以方便的在一个页面通过多次调用 addItemFactory(AssemblyItemFactory) 方法使用多个 ItemFactory（每个 ItemFactory 就代表一种 ItemType），这正体现了 AssemblyAdapter 名字中 Assembly 所表达的意思

另外由于支持多 Item，一个 Adapter 又只有一个数据列表，所以数据列表的数据类型就得是 Object

#### 3. 定义 ItemFactory

在使用 AssemblyAdapter 之前得先定义 ItemFactory 和 Item，如下：
```java
public class UserItemFactory extends AssemblyItemFactory<UserItemFactory.UserItem> {

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof User;
    }

    @Override
    public UserListItem createAssemblyItem(ViewGroup parent) {
        return new UserListItem(R.layout.list_item_user, parent);
    }

    public class UserItem extends AssemblyItem<User> {
        private ImageView headImageView;
        private TextView nameTextView;
        private TextView sexTextView;
        private TextView ageTextView;
        private TextView jobTextView;

        public UserListItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews(View itemView) {
            headImageView = (ImageView) findViewById(R.id.image_userListItem_head);
            nameTextView = (TextView) findViewById(R.id.text_userListItem_name);
            sexTextView = (TextView) findViewById(R.id.text_userListItem_sex);
            ageTextView = (TextView) findViewById(R.id.text_userListItem_age);
            jobTextView = (TextView) findViewById(R.id.text_userListItem_job);
        }

        @Override
        protected void onConfigViews(Context context) {
            getItemView().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getConext(), "第" + (getPosition() + 1) + "条数据", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected void onSetData(int position, User user) {
            headImageView.setImageResource(user.headResId);
            nameTextView.setText(user.name);
            sexTextView.setText(user.sex);
            ageTextView.setText(user.age);
            jobTextView.setText(user.job);
        }
    }
}
```

详解：
>* `ItemFactory 的泛型`是为了限定其 createAssemblyItem(ViewGroup) 方法返回的类型
>* ItemFactory 的 `isTarget()` 方法是用来匹配数据列表中的数据的，Adapter  从数据列表中拿到当前位置的数据后会依次调用其所有的 ItemFactory 的 isTarget(Object) 方法，谁返回 true 就用谁处理当前这条数据
>* ItemFactory 的 `createAssemblyItem(ViewGroup)` 方法用来创建 Item，返回的类型必须跟你在ItemFactory 上配置的泛型一样
>* `Item的泛型`是用来指定对应的数据类型，会在 onSetData 和 getData() 方法中用到
>* Item 的 `onFindViews(View)` 和 `onConfigViews(Context)` 方法分别用来初始化 View 和配置 View，只会在创建 Item 的时候`调用一次`，另外在 onFindViews 方法中你可以直接使用 `findViewById(int)` 法获取 View
>* Item 的 `onSetData()` 方法是用来设置数据的，在 `每次 getView() 的时候都会调用`
>* 你可以通过 Item 的 `getPosition()` 和 `getData()` 方法可直接获取当前所对应的位置和数据，因此你在处理 click 的时候不再需要通过 setTag() 来绑定位置和数据了，直接获取即可
>* 你还可以通过过 Item 的 `getItemView()` 方法获取当前的 itemView

#### 4. 使用 ItemFactory

首先你要准备好数据并 new 一个 AssemblyAdapter，然后通过 Adapter 的 `addItemFactory(AssemblyItemFactory)`方法添加 ItemFactory 即可，如下：
```java
ListView listView = ...;

List<Object> dataList = new ArrayList<Object>;
dataList.add(new User("隔离老王"));
dataList.add(new User("隔壁老李"));

AssemblyAdapter adapter = new AssemblyAdapter(dataList);
adapter.addItemFactory(new UserItemFactory());

listView.setAdapter(adapter);
```

你还可以一次使用多个 ItemFactory，如下：

```java
ListView listView = ...;

List<Object> dataList = new ArrayList<Object>;
dataList.add(new User("隔离老王"));
dataList.add(new Game("英雄联盟"));
dataList.add(new User("隔壁老李"));
dataList.add(new Game("守望先锋"));

AssemblyAdapter adapter = new AssemblyAdapter(dataList);
adapter.addItemFactory(new UserItemFactory());
adapter.addItemFactory(new GameItemFactory());

listView.setAdapter(adapter);
```

#### 5. 使用 header 和 footer

所有 Adapter 均支持添加 header 和 footer，可以方便的固定显示内容在列表的头部或尾部，更重要的意义在于可以让 GridView、RecyclerView 等也支持 header 和 footer

##### 添加 header、footer

首先定义好一个用于 header 或 footer 的 ItemFactory

然后调用 `addHeaderItem(AssemblyItemFactory, Object)` 或 `addFooterItem(AssemblyItemFactory, Object)` 方法添加即可，如下：
```java
AssemblyAdapter adapter = new AssemblyAdapter(objects);

adapter.addHeaderItem(new HeaderItemFactory(), "我是小额头呀！");
...
adapter.addFooterItem(new HeaderItemFactory(), "我是小尾巴呀！");
```

addHeaderItem(AssemblyItemFactory, Object) 和 addFooterItem(AssemblyItemFactory, Object) 的第二个参数是 Item 需要的数据，直接传进去即可

##### 隐藏或显示header、footer
addHeaderItem() 或 addFooterItem() 都会返回一个用于控制 header 或 footer 的 FixedItemInfo 对象，如下：
```java
AssemblyAdapter adapter = new AssemblyAdapter(objects);

FixedItemInfo userFixedItemInfo = adapter.addHeaderItem(new HeaderItemFactory(), "我是小额头呀！");

// 隐藏
userFixedItemInfo.setEnabled(false);

// 显示
userFixedItemInfo.setEnabled(true);
```

由于有了 header 和 footer 那么 Item.getPosition() 方法得到的位置就是 Item 在 Adapter 中的位置，要想得到其在所属部分的真实位置可通过 Adapter 的 `getPositionInPart(int)` 获取


#### 6. 使用加载更多功能

首先你需要定义一个继承自 AssemblyLoadMoreItemFactory 的 ItemFactory， AssemblyLoadMoreItemFactory 已经将加载更多相关逻辑部分的代码写好了，你只需关心界面即可，如下：

```java
public class LoadMoreItemFactory extends AssemblyLoadMoreItemFactory {

    public LoadMoreListItemFactory(OnLoadMoreListener eventListener) {
        super(eventListener);
    }

    @Override
    public AssemblyLoadMoreItem createAssemblyItem(ViewGroup parent) {
        return new LoadMoreListItem(R.layout.list_item_load_more, parent);
    }

    public class LoadMoreItem extends AssemblyLoadMoreItem {
        private View loadingView;
        private View errorView;
        private View endView;

        public LoadMoreListItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews(View itemView) {
            loadingView = findViewById(R.id.text_loadMoreListItem_loading);
            errorView = findViewById(R.id.text_loadMoreListItem_error);
            endView = findViewById(R.id.text_loadMoreListItem_end);
        }

        @Override
        public View getErrorRetryView() {
            return errorView;
        }

        @Override
        public void showLoading() {
            loadingView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showErrorRetry() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showEnd() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.VISIBLE);
        }
    }
}
```

然后调用 Adapter 的 `setLoadMoreItem(AssemblyLoadMoreItemFactory)` 方法设置加载更多 ItemFactory 即可，如下：

```java
AssemblyAdapter adapter = ...;
adapter.setLoadMoreItem(new LoadMoreItemFactory(new OnLoadMoreListener(){
    @Override
    public void onLoadMore(AssemblyAdapter adapter) {
        // 访问网络加载数据
        ...

        boolean loadSuccess = ...;
        if (loadSuccess) {
            // 加载成功时判断是否已经全部加载完毕，然后调用Adapter的loadMoreFinished(boolean)方法设置加载更多是否结束
            boolean loadMoreEnd = ...;
            adapter.loadMoreFinished(loadMoreEnd);
        } else {
            // 加载失败时调用Adapter的loadMoreFailed()方法显示加载失败提示，用户点击失败提示则会重新触发加载更多
            adapter.loadMoreFailed();
        }
    }
}));
```

你还可以通过`setDisableLoadMore(boolean)`方法替代 setLoadMoreEnd(boolean) 来控制是否禁用加载更多功能，两者的区别在于 setLoadMoreEnd(boolean) 为 true 时会在列表尾部显示 end 提示，而 setDisableLoadMore(boolean) 则是完全不显示加载更多尾巴

#### 7. 在 RecyclerView 的 GridLayoutManager 中一个 Item 独占一行或任意列

通过 RecyclerView 的 GridLayoutManager 我们可以很轻松的实现网格列表，同时 GridLayoutManager 还为我们提供了 SpanSizeLookup 接口，可以让我们指定哪一个 Item 需要独占一行或多列，AssemblyRecyclerAdapter 自然对如此重要的功能也做了封装，让你可以更方便的使用它

首先注册 SpanSizeLookup，并通过 AssemblyRecyclerAdapter 的 getSpanSize(int) 方法获取每一个位置的 Item 的 SpanSize：

```java
GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
    @Override
    public int getSpanSize(int position) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || !(adapter instanceof AssemblyRecyclerAdapter)) {
            return 1;
        }
        return ((AssemblyRecyclerAdapter) adapter).getSpanSize(position);
    }
});
recyclerView.setLayoutManager(gridLayoutManager);
```

然后创建 AssemblyRecyclerAdapter、添加 ItemFactory 并设置 SpanSize

```java
AssemblyRecyclerAdapter adapter = new AssemblyRecyclerAdapter(dataList);
adapter.addItemFactory(new AppListHeaderItemFactory().setSpanSize(4));

recyclerView.setAdapter(adapter);
```

AppListHeaderItemFactory 继承自 AssemblyRecyclerItemFactory 因此其拥有 setSpanSize(int) 方法

你也可以直接使用 fullSpan(RecyclerView) 方法设置独占一行，fullSpan 方法会通过 RecyclerView 取出其GridLayoutManager 的 SpanCount 作为 SpanSize

```java
AssemblyRecyclerAdapter adapter = new AssemblyRecyclerAdapter(dataList);
adapter.addItemFactory(new AppListHeaderItemFactory().fullSpan(recyclerView));

recyclerView.setAdapter(adapter);
```

fullSpan() 方法如果检测到 RecyclerView 的 LayoutManager 是 StaggeredGridLayoutManager 的话，还会自动为 Item 设置 setFullSpan(true)，好让 Item 在 StaggeredGridLayoutManager 中可以独占一行


#### 8.在 Kotlin 中使用并兼容 Kotterknife

从 2.3.2 开始 AssemblyAdapter 支持在 Kotlin 中使用 [Kotterknife] 来注入 View，之前的版本不行，如果你用的还是旧版本请尽快升级

AssemblyRecyclerItem 继承自 RecyclerView.ViewHolder 因此通过 [Kotterknife] 其可以使用 bindView

AssemblyItem、AssemblyGroupItem、AssemblyChildItem 就需要自己动手扩展 [Kotterknife] 了，将如下代码加入 [Kotterknife] 的 [Botterknife.kt] 文件即可

```kotlin
public fun <V : View> AssemblyItem<*>.bindView(id: Int)
        : ReadOnlyProperty<AssemblyItem<*>, V> = required(id, viewFinder)

private val AssemblyItem<*>.viewFinder: AssemblyItem<*>.(Int) -> View?
    get() = { itemView.findViewById(it) }

public fun <V : View> AssemblyGroupItem<*>.bindView(id: Int)
        : ReadOnlyProperty<AssemblyGroupItem<*>, V> = required(id, viewFinder)

private val AssemblyGroupItem<*>.viewFinder: AssemblyGroupItem<*>.(Int) -> View?
    get() = { itemView.findViewById(it) }

public fun <V : View> AssemblyChildItem<*>.bindView(id: Int)
        : ReadOnlyProperty<AssemblyChildItem<*>, V> = required(id, viewFinder)

private val AssemblyChildItem<*>.viewFinder: AssemblyChildItem<*>.(Int) -> View?
    get() = { itemView.findViewById(it) }
```

详情可参考示例 app 中的 [ Sample Botterknife.kt] 文件


### License
    Copyright (C) 2016 Peng fei Pan <sky@xiaopan.me>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[android_arsenal_icon]: https://img.shields.io/badge/Android%20Arsenal-AssemblyAdapter-green.svg?style=true
[android_arsenal_link]: https://android-arsenal.com/details/1/4152
[release_icon]: https://img.shields.io/github/release/panpf/AssemblyAdapter.svg
[release_link]: https://github.com/panpf/AssemblyAdapter/releases
[Kotterknife]: https://github.com/JakeWharton/kotterknife
[Botterknife.kt]: https://github.com/JakeWharton/kotterknife/blob/master/src/main/kotlin/kotterknife/ButterKnife.kt
[ Sample Botterknife.kt]: https://github.com/panpf/AssemblyAdapter/blob/master/sample/src/main/java/me/xiaopan/assemblyadaptersample/ButterKnife.kt