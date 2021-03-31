package com.intern.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intern.bookhub.R
import com.intern.bookhub.database.BookEntity
import com.intern.bookhub.model.Book
import com.squareup.picasso.Picasso
import java.util.ArrayList


class FavouriteRecyclerAdapter (val context: Context, val bookList: List<BookEntity>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolderClass {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row, parent, false)
        return FavouriteViewHolderClass(view)

    }


    override fun onBindViewHolder(holder: FavouriteViewHolderClass, position: Int) {
        val book=bookList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)


    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class FavouriteViewHolderClass(view: View): RecyclerView.ViewHolder(view){

        val txtBookName:TextView=view.findViewById(R.id.txtFavBookTitle)
        val txtBookAuthor:TextView=view.findViewById(R.id.txtFavBookAuthor)
        val txtBookPrice:TextView=view.findViewById(R.id.txtFavBookPrice)
        val txtBookRating:TextView=view.findViewById(R.id.txtFavBookRating)
        val imgBookImage:ImageView=view.findViewById(R.id.imgFavBookImage)
        val llContent:LinearLayout=view.findViewById(R.id.llFavContent)

    }


}