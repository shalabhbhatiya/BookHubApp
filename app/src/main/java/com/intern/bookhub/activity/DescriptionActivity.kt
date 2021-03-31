package com.intern.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.intern.bookhub.R
import com.intern.bookhub.database.BookDatabase
import com.intern.bookhub.database.BookEntity
import com.intern.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var txtBookDesc: TextView

    lateinit var imgBookImage: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var btnAddtoFav: Button

    var bookId: String? = "100"
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)



        btnAddtoFav = findViewById(R.id.btnAddToFav)

        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookRating)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        imgBookImage = findViewById(R.id.imgBookImage)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"



        if (intent != null) {

            bookId = intent.getStringExtra("book_id")

        } else {
            Toast.makeText(
                this@DescriptionActivity,
                "Some Expected error occur",
                Toast.LENGTH_SHORT
            ).show()

        }
        if (bookId == "100") {
            finish()

            Toast.makeText(
                this@DescriptionActivity,
                "Some Expected error occur",
                Toast.LENGTH_SHORT
            ).show()


        }
        val queue = Volley.newRequestQueue(this@DescriptionActivity)

        val url = "http://13.235.250.119/v1/book/get_book/"
        if (ConnectionManager().isNetworkAvailable(this@DescriptionActivity)) {



            val jsonParams = JSONObject()
            jsonParams.put("book_id", bookId)

            val jsonRequest =
                object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {
                    try {

                        val success = it.getBoolean("success")
                        if (success) {
                            val bookJSONObject = it.getJSONObject("book_data")
                            progressLayout.visibility = View.GONE

                            val bookImageURL=bookJSONObject.getString("image")
                            Picasso.get().load(bookJSONObject.getString("image")).into(imgBookImage)
                            txtBookName.text = bookJSONObject.getString("name")
                            txtBookAuthor.text = bookJSONObject.getString("author")
                            txtBookPrice.text = bookJSONObject.getString("price")
                            txtBookRating.text = bookJSONObject.getString("rating")
                            txtBookDesc.text = bookJSONObject.getString("description")



                            val bookEntity=BookEntity( // this class had all these properties

    bookId?.toInt() as Int,
        txtBookName.text.toString(),
txtBookAuthor.text.toString(),
        txtBookPrice.text.toString(),
        txtBookRating.text.toString(),
        txtBookDesc.text.toString(),
bookImageURL

)
                            val checkFav=DBAsyncTask(applicationContext,bookEntity,1).execute()// this is a an object
                          val isFav=checkFav.get()// it will tell us if the result of the background process is true on not
                            // if yes so it will get true
                            // else return false

                            if(isFav){
btnAddtoFav.text="Remove From favourites"
    val favColor= ContextCompat.getColor(applicationContext,R.color.design_default_color_primary_dark)
                                btnAddtoFav.setBackgroundColor(favColor)
                            }
                            else {
                                btnAddtoFav.text="Add to favourites"
                                val nofavColor= ContextCompat.getColor(applicationContext,R.color.black)
                                btnAddtoFav.setBackgroundColor(nofavColor)

                            }
btnAddtoFav.setOnClickListener {

if(!DBAsyncTask(applicationContext,bookEntity,1).execute().get())
{

    val async=DBAsyncTask(applicationContext,bookEntity,2).execute()
    val result=async.get()

    if(result)
    {
Toast.makeText(this@DescriptionActivity,"Book added to favourites",Toast.LENGTH_SHORT).show()
        btnAddtoFav.text="Remove From favourites"
        val favColor= ContextCompat.getColor(applicationContext,R.color.design_default_color_primary_dark)
        btnAddtoFav.setBackgroundColor(favColor)

    }


    else{
        Toast.makeText(this@DescriptionActivity,"Some Error occur",Toast.LENGTH_SHORT).show()
    }
}
    else{
    val async=DBAsyncTask(applicationContext,bookEntity,3).execute()
    val result=async.get()
    if(result)
    {
        Toast.makeText(this@DescriptionActivity,"Remove From favourites",Toast.LENGTH_SHORT).show()
        btnAddtoFav.text="Add to  favourites"
        val nofavColor= ContextCompat.getColor(applicationContext,R.color.black)
        btnAddtoFav.setBackgroundColor(nofavColor)

    }
    else{
        Toast.makeText(this@DescriptionActivity,"Some Error occur",Toast.LENGTH_SHORT).show()
    }

    }

}


                        } else {


                            Toast.makeText(
                                this@DescriptionActivity,
                                "Some Error Occur",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Error Occur",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }, Response.ErrorListener {

                    Toast.makeText(this@DescriptionActivity, "Volley Error $it", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {

                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "56fa713574abf1"

                        return headers


                    }
                }
            queue.add(jsonRequest)
        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->

                val settingIntent = Intent(Settings.ACTION_SETTINGS)
                startActivity(settingIntent)
                finish()

            }
            dialog.setNegativeButton("Cancel") { text, listener ->

                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()

        }


    }


    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
       Mode 1 -> Check DB if the book is favourite or not
       Mode 2 -> Save the book into DB as favourite
       Mode 3 -> Remove the favourite book
       * */
val db= Room.databaseBuilder(context,BookDatabase::class.java, "books").build()// initialize the database class
        override fun doInBackground(vararg params: Void?): Boolean{



            when(mode){
1->{
    // check whether the book is present in favorites or not

    val book:BookEntity?=db.bookDao().getBookById(bookEntity.book_id.toString())// it is used to check whether the book entity with
    // the book id is present on not if present
    // it will give  the book id else gives null
db.close()
    return book!=null
}
2->{
db.bookDao().insertBook(bookEntity)
db.close()
return true

}
3->{
db.bookDao().deleteBook(bookEntity)
    db.close()
    return true

}

            }

            return false
        }

    }

}