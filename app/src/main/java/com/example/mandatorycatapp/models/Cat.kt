package com.example.mandatorycatapp.models

import java.io.Serializable

data class Cat (
    val id: Int,
    val name: String,
    val description: String,
    val place: String,
    val reward: Int,
    val userId: String,
    val date: Long,
    val pictureUrl: String) : Serializable {

        override fun toString(): String {
            return "The Id: $id, the name: $name \n" +
                    "It lives at: $place, Date: $date"

    }
        fun toLongString(): String {
            return "The Id: $id, the name: $name, the descriptions of the cat: $description.\n" +
                    "It lives at: $place, the cat belongs to the user, with id: $userId " +
                    "The reward for returning the cat is $reward" + " Date: $date"
        }
}