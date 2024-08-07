package com.dss.xeapplication.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.databinding.ItemAdsBinding
import com.google.android.gms.ads.nativead.NativeAdView
import com.wavez.p27_pdf_scanner.data.local.SharedPref

abstract class BaseRecyclerViewAdsAdapter<T, V : ViewBinding>(
    var dataList: MutableList<WrapData<T>> = arrayListOf(),
    var isItemClick: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_NORMAL = 0

        const val TYPE_ADS = 1
    }

    open val maxAd = 6

    open val spaceAd = 8

    open val spanCount = 1

    var listPositionAds = listOf<Int>()

    var notShowAds = false

    var listNative = arrayListOf<NativeAdView>()

    var listDataNormal = arrayListOf<T>()

    inner class ViewHolder(val binding: V) :
        RecyclerView.ViewHolder(binding.root)

    inner class AdsViewHolder(val binding: ItemAdsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].t != null) {
            TYPE_NORMAL
        } else {
            TYPE_ADS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            ViewHolder(
                providesItemViewBinding(parent, viewType)
            )
        } else {
            AdsViewHolder(
                ItemAdsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    abstract fun providesItemViewBinding(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            bindData(
                (holder as BaseRecyclerViewAdsAdapter<*, V>.ViewHolder).binding,
                dataList[position].t,
                position,
                holder.itemView.context
            )
        } else {
            val binding = (holder as BaseRecyclerViewAdsAdapter<*, V>.AdsViewHolder).binding
            val data = dataList[position]
            val indexAds = data.type?.i ?: 0
            val context = holder.itemView.context

            if (listNative.isEmpty()) {
                bindAds(context, binding)
                return
            }

            if (listNative.size == maxAd) {
                bindAds(context, binding, listNative[indexAds])
            } else {
                bindAds(context, binding)
            }
        }
    }

    var nameScreen = ""

    fun setupAds(layout: Int, nameScreen: String) {
        layoutAds = layout
        this.nameScreen = nameScreen
    }

    private var layoutAds: Int? = null

    private fun bindAds(
        context: Context,
        binding: ItemAdsBinding,
        nativeAdView: NativeAdView? = null
    ) {
        layoutAds?.let {
            val ads = nativeAdView ?: NativeManager.getNativeAds(context, it, nameScreen)
            if (ads != null) {
                // Kiểm tra nếu ads đã có parent, loại bỏ nó khỏi parent trước
                ads.parent?.let { parent ->
                    (parent as ViewGroup).removeView(ads)
                }
                binding.root.removeAllViews()
                if (binding.root.parent != null) {
                    (binding.root.parent as ViewGroup).removeView(binding.root)
                }
                binding.root.addView(ads)
                if (nativeAdView == null) {
                    listNative.add(ads)
                }
            }
        }
    }


    abstract fun bindData(binding: V, data: T?, position: Int, context: Context)

    @SuppressLint("NotifyDataSetChanged")
    open fun set(dataList: ArrayList<T>, notShowAds: Boolean = this.notShowAds) {
        this.notShowAds = notShowAds
        if (SharedPref.isVip) {
            this.notShowAds = true
        }
        val clone = ArrayList(dataList)
        listDataNormal = ArrayList(dataList)
        val listData = arrayListOf<WrapData<T>>()

        clone.forEachIndexed { index, t ->
            listData.add(WrapData(t))
        }

        if (!this.notShowAds && listData.isNotEmpty()) {
            listPositionAds = getPositionAds(dataList.size)

            // thiết lập cho quảng cáo xuất hiện xen kẽ
            listPositionAds.forEachIndexed { index, i ->
                if (index % 2 == 0) {
                    if (i + spanCount <= listData.size) {
                        listData.add(i + spanCount, WrapData(type = AdsType(index)))
                    }
                } else {
                    listData.add(i, WrapData(type = AdsType(index)))
                }
            }
        }


        this.dataList.clear()
        this.dataList.addAll(listData)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun clear() {
        dataList.clear()
        notifyItemRangeRemoved(0, dataList.size)
    }

    override fun getItemCount(): Int = dataList.size

    fun getItemNormalCount(): Int {
        var i = 0
        dataList.forEach {
            if (it.t != null) {
                i++
            }
        }
        return i
    }

    fun getPositionAds(size: Int): List<Int> {
        val list = arrayListOf<Int>()
        for (i in 0..((maxAd - 1) * spaceAd) step spaceAd) {
            if (size >= i) {
                list.add(i)
            }
        }
        return list.toSet().toList()
    }


    open fun notifyItem(t: T) {
        try {
            val item = dataList.find { it.t == t }
            val pos = dataList.indexOf(item)
            notifyItemChanged(pos)
        } catch (e: Exception) {

        }
    }

    fun replaceItem(oldFile: T, newFile: T) {
        val itemOldPosition = dataList.indexOf(dataList.find { it.t == oldFile })
        if (itemOldPosition == -1) {
            notifyItem(newFile)
        } else {
            dataList[itemOldPosition] = WrapData(newFile)
            notifyItemChanged(itemOldPosition)
        }
    }

    fun removeItem(t: T) {
        try {
            val item = dataList.find { it.t == t }
            val pos = dataList.indexOf(item)
            dataList.removeAt(dataList.indexOf(item))
            notifyItemRemoved(pos)
            notifyItemRangeChanged(pos, itemCount)
        } catch (e: Exception) {

        }
    }

    fun closeAds() {
        set(listDataNormal, true)
    }

    fun updateAdsResume(isVip: Boolean = SharedPref.isVip) {
        if (isVip != notShowAds) {
            set(listDataNormal, isVip)
        }
    }

    fun setListNative(list: List<NativeAdView>) {
        listNative = ArrayList(list)
        if (listDataNormal.isNotEmpty())
            set(listDataNormal)
    }

    fun getManager(context: Context): GridLayoutManager {
        val gridManager = GridLayoutManager(context, 3)
        gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return try {
                    if (dataList[position].t != null) {
                        1
                    } else {
                        3
                    }
                } catch (e: Exception) {
                    1
                }
            }
        }
        return gridManager
    }
}