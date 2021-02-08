package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.Adress
import com.NTNU.FullStack.Repositories.AdressRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/adress")
class AdressController(
        val adressRepository: AdressRepository,
) {



    @GetMapping("/{adressid}")
    fun getAdressById(@PathVariable adressid: Long): ResponseEntity<*> {
        val adress = adressRepository.findAdressById(adressid)
        return if (adress != null) {
            ResponseEntity.ok(adress)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the adress"))
        }
    }

    @PostMapping
    fun createNewAdress(@Valid @RequestBody newAdress: Adress): ResponseEntity<*> {
        val adress = adressRepository.findAdressById(newAdress.id)
        return if (adress != null) {
            ResponseEntity.ok(adress)
        } else {
            val adress = Adress(0, newAdress.street, newAdress.postCode, newAdress.country, newAdress.author)
            ResponseEntity.ok().body(adressRepository.save(adress))
        }
    }

    @PutMapping("/{adressId}")
    fun updateAdressById(@PathVariable adressId: Long, @Valid @RequestBody newAdress: Adress): ResponseEntity<*> {
        val adress = adressRepository.findAdressById(adressId)
        return if (adress != null) {
            val updatedAdress = adress.copy(
                    street = newAdress.street ?: adress.street,
                    postCode = newAdress.postCode ?: adress.postCode,
                    country = newAdress.country ?: adress.country,
                    author = newAdress.author ?: adress.author,
            )
            ResponseEntity.ok().body(adressRepository.save(updatedAdress))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the Adress to update"))
        }
    }

    @DeleteMapping("/{adressId}")
    fun deleteAdressById(@PathVariable adressId: Long): ResponseEntity<*> {
        val adress = adressRepository.findAdressById(adressId)
        return if (adress != null) {
            adressRepository.delete(adress)
            ResponseEntity.ok<Any>(SuccessResponse("Adress successfully deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the Adress to delete"))
        }
    }
}