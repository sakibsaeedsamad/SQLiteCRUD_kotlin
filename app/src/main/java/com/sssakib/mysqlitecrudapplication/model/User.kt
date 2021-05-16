package com.sssakib.mysqlitecrudapplication.model

class User{

    var id : Int = 0
    var name : String? =null
    var phone : String? =null
    var gender : String? =null
    var location : String? =null
    var image : String? =null

    constructor(name:String?, phone: String?, gender: String?, location: String?, image: String?){
        this.name = name
        this.phone = phone
        this.gender = gender
        this.location = location
        this.image = image
    }

    constructor(){
    }

}