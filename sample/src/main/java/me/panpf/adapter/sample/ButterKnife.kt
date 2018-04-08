package me.panpf.adapter.sample

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import me.panpf.adapter.AssemblyItem
import me.panpf.adapter.expandable.AssemblyChildItem
import me.panpf.adapter.expandable.AssemblyGroupItem
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import android.support.v4.app.DialogFragment as SupportDialogFragment
import android.support.v4.app.Fragment as SupportFragment

public fun <V : View> View.bindView(id: Int)
        : ReadOnlyProperty<View, V> = required(id, viewFinder)
public fun <V : View> Activity.bindView(id: Int)
        : ReadOnlyProperty<Activity, V> = required(id, viewFinder)
public fun <V : View> Dialog.bindView(id: Int)
        : ReadOnlyProperty<Dialog, V> = required(id, viewFinder)
public fun <V : View> DialogFragment.bindView(id: Int)
        : ReadOnlyProperty<DialogFragment, V> = requiredNoCache(id, viewFinder)
public fun <V : View> SupportDialogFragment.bindView(id: Int)
        : ReadOnlyProperty<SupportDialogFragment, V> = requiredNoCache(id, viewFinder)
public fun <V : View> Fragment.bindView(id: Int)
        : ReadOnlyProperty<Fragment, V> = requiredNoCache(id, viewFinder)
public fun <V : View> SupportFragment.bindView(id: Int)
        : ReadOnlyProperty<SupportFragment, V> = requiredNoCache(id, viewFinder)
public fun <V : View> ViewHolder.bindView(id: Int)
        : ReadOnlyProperty<ViewHolder, V> = required(id, viewFinder)

public fun <V : View> View.bindOptionalView(id: Int)
        : ReadOnlyProperty<View, V?> = optional(id, viewFinder)
public fun <V : View> Activity.bindOptionalView(id: Int)
        : ReadOnlyProperty<Activity, V?> = optional(id, viewFinder)
public fun <V : View> Dialog.bindOptionalView(id: Int)
        : ReadOnlyProperty<Dialog, V?> = optional(id, viewFinder)
public fun <V : View> DialogFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<DialogFragment, V?> = optionalNoCache(id, viewFinder)
public fun <V : View> SupportDialogFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<SupportDialogFragment, V?> = optionalNoCache(id, viewFinder)
public fun <V : View> Fragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<Fragment, V?> = optionalNoCache(id, viewFinder)
public fun <V : View> SupportFragment.bindOptionalView(id: Int)
        : ReadOnlyProperty<SupportFragment, V?> = optionalNoCache(id, viewFinder)
public fun <V : View> ViewHolder.bindOptionalView(id: Int)
        : ReadOnlyProperty<ViewHolder, V?> = optional(id, viewFinder)

public fun <V : View> View.bindViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = required(ids, viewFinder)
public fun <V : View> Activity.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = required(ids, viewFinder)
public fun <V : View> Dialog.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = required(ids, viewFinder)
public fun <V : View> DialogFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = requiredNoCache(ids, viewFinder)
public fun <V : View> SupportDialogFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<SupportDialogFragment, List<V>> = requiredNoCache(ids, viewFinder)
public fun <V : View> Fragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = requiredNoCache(ids, viewFinder)
public fun <V : View> SupportFragment.bindViews(vararg ids: Int)
        : ReadOnlyProperty<SupportFragment, List<V>> = requiredNoCache(ids, viewFinder)
public fun <V : View> ViewHolder.bindViews(vararg ids: Int)
        : ReadOnlyProperty<ViewHolder, List<V>> = required(ids, viewFinder)

public fun <V : View> View.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<View, List<V>> = optional(ids, viewFinder)
public fun <V : View> Activity.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Activity, List<V>> = optional(ids, viewFinder)
public fun <V : View> Dialog.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Dialog, List<V>> = optional(ids, viewFinder)
public fun <V : View> DialogFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<DialogFragment, List<V>> = optional(ids, viewFinder)
public fun <V : View> SupportDialogFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<SupportDialogFragment, List<V>> = optional(ids, viewFinder)
public fun <V : View> Fragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<Fragment, List<V>> = optional(ids, viewFinder)
public fun <V : View> SupportFragment.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<SupportFragment, List<V>> = optional(ids, viewFinder)
public fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<ViewHolder, List<V>> = optional(ids, viewFinder)

private val View.viewFinder: View.(Int) -> View?
    get() = { findViewById(it) }
private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }
private val Dialog.viewFinder: Dialog.(Int) -> View?
    get() = { findViewById(it) }
private val DialogFragment.viewFinder: DialogFragment.(Int) -> View?
    get() = { dialog?.findViewById(it) ?: view?.findViewById(it) }
private val SupportDialogFragment.viewFinder: SupportDialogFragment.(Int) -> View?
    get() = { dialog?.findViewById(it) ?: view?.findViewById(it) }
private val Fragment.viewFinder: Fragment.(Int) -> View?
    get() = { view.findViewById(it) }
private val SupportFragment.viewFinder: SupportFragment.(Int) -> View?
    get() = { view!!.findViewById(it) }
private val ViewHolder.viewFinder: ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id:Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY
    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it（No Cache）
private class NoCacheLazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        return initializer(thisRef, property)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> requiredNoCache(id: Int, finder: T.(Int) -> View?)
        = NoCacheLazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optionalNoCache(id: Int, finder: T.(Int) -> View?)
        = NoCacheLazy { t: T, desc -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> requiredNoCache(ids: IntArray, finder: T.(Int) -> View?)
        = NoCacheLazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optionalNoCache(ids: IntArray, finder: T.(Int) -> View?)
        = NoCacheLazy { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }

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