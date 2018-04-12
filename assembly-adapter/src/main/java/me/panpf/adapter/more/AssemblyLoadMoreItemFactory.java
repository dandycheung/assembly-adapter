/*
 * Copyright (C) 2017 Peng fei Pan <sky@panpf.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.panpf.adapter.more;

import me.panpf.adapter.AssemblyItemFactory;

/**
 * 加载更多专用 {@link AssemblyItemFactory}
 */
@SuppressWarnings("unused")
public abstract class AssemblyLoadMoreItemFactory<ITEM extends AssemblyLoadMoreItem>
        extends AssemblyItemFactory<ITEM> implements LoadMoreItemFactoryBridle<ITEM> {

    boolean paused;
    boolean end;
    OnLoadMoreListener eventListener;
    AssemblyLoadMoreItem assemblyLoadMoreItem;

    public AssemblyLoadMoreItemFactory(OnLoadMoreListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void loadMoreFinished(boolean end) {
        this.paused = false;
        this.end = end;

        if (assemblyLoadMoreItem != null) {
            if (end) {
                assemblyLoadMoreItem.showEnd();
            } else {
                assemblyLoadMoreItem.showLoading();
            }
        }
    }

    @Override
    public void loadMoreFailed() {
        paused = false;
        if (assemblyLoadMoreItem != null) {
            assemblyLoadMoreItem.showErrorRetry();
        }
    }

    @Override
    public boolean isTarget(Object data) {
        return true;
    }
}