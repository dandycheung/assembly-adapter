package me.panpf.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "unused"})
public class FixedItemInfo {

    @NonNull
    private ItemStorage itemStorage;
    @NonNull
    private ItemFactory itemFactory;
    @Nullable
    private Object data;
    private int position;
    private boolean header;

    private boolean enabled = true;

    public FixedItemInfo(@NonNull ItemStorage itemStorage, @NonNull ItemFactory itemFactory, @Nullable Object data, boolean header) {
        this.itemStorage = itemStorage;
        this.itemFactory = itemFactory;
        this.data = data;
        this.header = header;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    public void setData(@Nullable Object data) {
        this.data = data;

        AssemblyAdapter adapter = itemFactory.getAdapter();
        if (adapter != null && adapter.isNotifyOnChange()) {
            adapter.notifyDataSetChanged();
        }
    }

    @NonNull
    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        enableChanged();
    }

    protected void enableChanged() {
        if (header) {
            itemStorage.headerEnabledChanged(this);
        } else {
            itemStorage.footerEnabledChanged(this);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isHeader() {
        return header;
    }
}
