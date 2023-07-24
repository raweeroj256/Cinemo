package je.raweeroj.cinemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Constants {
    const val BASE_URL : String = "https://www.majorcineplex.com/apis/"
    const val MOVIE_ID: String = "movieId"
    const val FROM_SCREEN : String = "fromScreen"
    const val FROM_MAIN : String = "fromMain"
    const val FROM_FAVORITE : String = "fromFavorite"

    fun isNetworkAvailable(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
                else -> false
            }

        }else{
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo!= null && networkInfo.isConnectedOrConnecting
        }


    }
}