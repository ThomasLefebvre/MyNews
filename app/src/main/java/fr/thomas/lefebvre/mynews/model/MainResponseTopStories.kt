package fr.thomas.lefebvre.mynews.model

 data class MainResponseTopStories (
     var status:String,
     var copyright:String,
     var section: String,
     var last_updated:String,
     var num_results:Int,
     var results:List<Article>
 )

data class Article(
    var abstract:String,
    var byline:String,
    var created_date:String,
    var des_facet:List<String>,
    var geo_facet:List<String>,
    var item_type:String,
    var kicker:String,
    var material_type_facet:String,
    var multimedia:List<Multimedia>,
    var org_facet:List<String>,
    var per_facet:List<String>,
    var published_date:String,
    var section:String,
    var short_url:String,
    var subsection:String,
    var title:String,
    var updated_date:String,
    var url:String
)

data class Multimedia(
    var caption:String,
    var copyright:String,
    var format:String,
    var height:Int,
    var subtype:String,
    var type:String,
    var url:String,
    var width:Int

)