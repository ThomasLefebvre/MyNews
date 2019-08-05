package fr.thomas.lefebvre.mynews.model

import com.google.gson.annotations.SerializedName

data class MainResponseMostPopular (
    var copyright:String,
    var num_results:Int,
    var results:List<ViewedArticle>,
    var status:String
 )

data class ViewedArticle(
    var abstract:String,
    var adx_keywords:String,
    var asset_id:Long,
    var byline:String,
    var column:String,
    var id:Long,
    var media:List<Media>,
    var published_date:String,
    var section:String,
    var source:String,
    var title:String,
    var type:String,
    var uri:String,
    var url:String,
    var views:Int

)

data class Media(
    var approved_for_syndication:Int,
    var caption:String,
    var copyright:String,
    @SerializedName("media-metadata")
    var media_metadata:List<MediaMetadata>,
    var subtype:String,
    var type:String
)

data class MediaMetadata(
    var format:String,
    var height:Int,
    var url:String,
    var width:Int

)