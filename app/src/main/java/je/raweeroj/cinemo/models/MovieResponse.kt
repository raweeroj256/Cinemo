package je.raweeroj.cinemo.models

import java.io.Serializable

data class MovieResponse (
   val movies : List<Movie>
        ) : Serializable