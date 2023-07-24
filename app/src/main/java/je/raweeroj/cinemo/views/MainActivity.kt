package je.raweeroj.cinemo.views

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.adapters.MovieListItemsAdapter
import je.raweeroj.cinemo.databinding.ActivityMainBinding
import je.raweeroj.cinemo.models.Movie
import je.raweeroj.cinemo.models.MovieResponse
import je.raweeroj.cinemo.network.MovieService
import je.raweeroj.cinemo.utils.Constants
import retrofit.*

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    private var mProgressDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()
        getMovieList()

    }

    fun getMovieList(){
        if(Constants.isNetworkAvailable(this)){
            val retrofit : Retrofit =  Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service : MovieService = retrofit
                .create<MovieService>(MovieService::class.java)

            val listCall : Call<MovieResponse> = service.getMovie()

            showCustomProgressDialog()

//            Log.i("list call data : ",listCall.toString())

            listCall.enqueue(object : Callback<MovieResponse> {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onResponse(response: Response<MovieResponse>?, retrofit: Retrofit?) {
                    if(response!!.isSuccess){
                        hideProgressDialog()

                        val movieList:MovieResponse = response.body()

                        val movieJsonString = Gson().toJson(movieList)

                        setupUI(movieList)
                        Log.i("Response result","$movieList")
                    }else{
                        val rc = response.code()
                        hideProgressDialog()
                        when (rc){
                            400->{
                                Log.e("Error 400","Bad Connection")
                            }
                            404->{
                                Log.e("Error 404","Not found")
                            }else ->{
                            Log.e("Other Error ","Other Error")
                        }
                        }
                    }
                }

                override fun onFailure(t: Throwable?) {
                    hideProgressDialog()
                    Log.e("Errorrrrrr ","Error")
                }

            })

        }else{
            Toast.makeText(this,"You have no internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUI(movieList : MovieResponse) {
        if(movieList != null){
            binding?.tvNoMoviesAvailable?.visibility = View.GONE
            binding?.rvMoviesList?.visibility = View.VISIBLE

            binding?.rvMoviesList?.layoutManager=
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            binding?.rvMoviesList?.setHasFixedSize(true)

            val adapter = MovieListItemsAdapter(this,movieList.movies.toList())
            binding?.rvMoviesList?.adapter = adapter

            adapter.setOnClickListener(object :MovieListItemsAdapter.OnClickListener{
                override fun onClick(position: Int, model: Movie) {
                    val intent = Intent(this@MainActivity,MovieDetailActivity::class.java)
                    intent.putExtra(Constants.MOVIE_ID,model)
                    intent.putExtra(Constants.FROM_SCREEN,Constants.FROM_MAIN)
                    startActivity(intent)
                }

            })

        }else{
            binding?.tvNoMoviesAvailable?.visibility = View.VISIBLE
            binding?.rvMoviesList?.visibility = View.GONE
        }
    }

    private fun  setupActionBar(){
        setSupportActionBar(binding?.toolbarMainActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = resources.getString(R.string.app_name)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_favorite -> {
                //TODO : Move to favorite screen
                var intent = Intent(this@MainActivity,MyFavoriteActivity::class.java)
                startActivity(intent)

                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCustomProgressDialog(){
        mProgressDialog = Dialog(this)

        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

        mProgressDialog!!.show()
    }

    private fun hideProgressDialog(){
        if (mProgressDialog!=null){
            mProgressDialog!!.dismiss()
        }
    }
}