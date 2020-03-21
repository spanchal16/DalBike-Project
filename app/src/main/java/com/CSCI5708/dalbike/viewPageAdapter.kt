package com.CSCI5708.dalbike

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.custom_layout.view.*


class viewPageAdapter(private val context: Context ):PagerAdapter(){

    private var layoutInflater:LayoutInflater?=null
    private val images = arrayOf(R.drawable.bike1,R.drawable.bike2,R.drawable.bike3)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.custom_layout,null,false)
        val image = v.findViewById<View>(R.id.image_view) as ImageView
        image.setImageResource(images[position])
        val vp = container as ViewPager
        vp.addView(v,0)
//        v.detailsbutton.setOnClickListener { view ->
//            val intent = Intent(context, DetailBikeInfo::class.java)
//            intent.putExtra("bikeName",position.toString())
//            System.out.println("Switching to next activity "+ position)
//            context.startActivity(intent)
//        }
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }

    fun insideButton(view:View) {
        System.out.println("hello")
    }
}