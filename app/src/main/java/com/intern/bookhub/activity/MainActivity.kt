package com.intern.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.intern.bookhub.*
import com.intern.bookhub.fragment.*
import com.intern.bookhub.fragment.DashBoardFragment


class MainActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout:DrawerLayout
    lateinit var frameLayout: FrameLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationView: NavigationView

    var PreviousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar= findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar);
//        supportActionBar?.title = "hello";

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        frameLayout=findViewById(R.id.frame)
        navigationView=findViewById(R.id.navigationView)
setUpToolbar()
        openDashBoard()

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setCheckedItem(R.id.DashBoard)

        navigationView.setNavigationItemSelectedListener {

            if(PreviousMenuItem!=null)
            {
                PreviousMenuItem?.isChecked=false

            }

            it.isCheckable=true
            it.isChecked=true
            PreviousMenuItem=it


            // "it" will give us currently selected item means aggar hamburger icon hai too
            when(it.itemId){


R.id.DashBoard ->{
    openDashBoard()


    drawerLayout.closeDrawers()

}

                R.id.favourites ->{

                    Toast.makeText(this@MainActivity,"Clicked On Favourites",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                            .commit()
                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile ->{

                    Toast.makeText(this@MainActivity,"Clicked On Favourites",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())

                            .commit()

                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()

                }
                R.id.aboutApp ->{
                    Toast.makeText(this@MainActivity,"Clicked On About App",Toast.LENGTH_SHORT).show()

                    supportFragmentManager.beginTransaction()

                        .replace(R.id.frame, AboutAppFragment())
                        .commit()
                    supportActionBar?.title="About App"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        } }

fun setUpToolbar(){
setSupportActionBar(toolbar)
    supportActionBar?.title = "Toolbar Title";// sometimes we dont have a toolbar on the screen so it can be null
    supportActionBar?.setHomeButtonEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true) // for display a button
}

   // this function is necessary for hamburger icon for opening by click teh icon and open nav drawer left side of the screen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home) {//if  the id of hamburger is equal to the home means ki ham abhi home par hai and hamburger icon home par hai beacude it is placed in a home
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)

    }

fun openDashBoard(){
    val fragment= DashBoardFragment()
   val transaction= supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment)
            transaction.commit()
    supportActionBar?.title="DashBoard"

    navigationView.setCheckedItem(R.id.DashBoard)

}

    override fun onBackPressed() {
       val frag=supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashBoardFragment -> openDashBoard()
        else->super.onBackPressed()

        }


    }


}


