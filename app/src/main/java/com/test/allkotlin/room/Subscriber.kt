package com.test.allkotlin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "subscriber_data")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id : Int,

    @ColumnInfo(name = "subscriber_name")
    var name : String,

    @ColumnInfo(name = "subscriber_mobile")
    var mobile : String,

    @ColumnInfo(name = "subscriber_book")
    var book : String
)