package com.example.eduputinf151873

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root



@Root(name = "item", strict = false)
data class GameResponse(
    @field:Attribute(name = "objectid", required = false)
    var objectid :  Int = 0,
    @field:Element(name = "name", required = false)
    var name : String? = null,
    @field:Element(name = "yearpublished", required = false)
    var yearpublished : String? = null,
    @field:Element(name = "image", required = false)
    var image : String? = null,

)

@Root(name = "items", strict = false)
data class GameListResponse(
    @field:ElementList(name = "item", required = false, inline = true)
    var games: ArrayList<GameResponse> = ArrayList()
)

@Root(name = "item", strict = false)
data class ExtensionResponse(
    @field:Attribute(name = "objectid", required = false)
    var objectid :  Int = 0,
    @field:Element(name = "name", required = false)
    var name : String? = null,
    @field:Element(name = "image", required = false)
    var image : String? = null,

    )
@Root(name = "items", strict = false)
data class ExtensionListResponse(
    @field:ElementList(name = "item", required = false, inline = true)
    var extensions: ArrayList<ExtensionResponse> = ArrayList()
)


@Root (name = "items", strict = false)
data class UserResponse(
    @field:Attribute(name = "totalitems")
    var totalitems : Int = 0,
    @field:Attribute(name = "pubdate" )
    var pubdate : String = ""

)
