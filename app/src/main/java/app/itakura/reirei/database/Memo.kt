package app.itakura.reirei.database

import io.realm.RealmObject

open class Memo(
     open var answer:String = ""
) : RealmObject()