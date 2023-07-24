package je.raweeroj.cinemo.models

import java.io.Serializable


data class Movie(
    val id: Int,
    val movieCode: List<String>,
    val title_en: String,
    val title_th: String,
    val rating: String,
    val rating_id: Int,
    val duration: Int,
    val release_date: String,
    val sneak_date: String,
    val synopsis_th: String,
    val synopsis_en: String,
    val director: String,
    val actor: String,
    val genre: String,
    val poster_ori: String,
    val poster_url: String,
    val trailer: String,
    val tr_ios: String,
    val tr_hd: String,
    val tr_sd: String,
    val tr_mp4: String,
    val priority: String,
    val now_showing: String,
    val advance_ticket: String,
    val date_update: String,
    val show_buyticket: String,
    val trailer_cms_id: String,
    val trailer_ivx_key: String
) : Serializable