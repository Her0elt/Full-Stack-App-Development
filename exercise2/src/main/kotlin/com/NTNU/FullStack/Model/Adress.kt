package com.NTNU.FullStack.Model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "adress")
data class Adress(@Id
                  @GeneratedValue(strategy = GenerationType.IDENTITY)
                  var id: Long = 0,
                  @NotNull
                  var street:String,
                  @NotNull
                  var postCode:Int,
                  @NotNull
                  var country:String,
                  @OneToOne(mappedBy = "adress")
                  @JsonIgnore
                  @JsonBackReference
                  var author: Author?,
    )

data class AdressDto(val id: Long, val street: String, val postCode:Int, val country: String)

fun Adress.toAdressDto(): AdressDto {
    return AdressDto(
            this.id,
            this.street,
            this.postCode,
            this.country
    )
}