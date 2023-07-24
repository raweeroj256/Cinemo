package je.raweeroj.cinemo.app

import android.app.Application
import je.raweeroj.cinemo.models.MovieDatabase

class MovieApplication : Application(){
    val db by lazy {
        MovieDatabase.getInstance(this)
    }
}