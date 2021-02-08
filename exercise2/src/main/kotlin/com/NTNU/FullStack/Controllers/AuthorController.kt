package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.AdressRepository
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/author")
class AuthorController(
        val authorRepository: AuthorRepository,
        val adressRepository: AdressRepository,
) {

    @GetMapping("/")
    fun getAllLeagues(): List<AuthorDtoList> = authorRepository.findAll().map { author -> author.toAuthorDto() }

    @GetMapping("/{authorName}/")
    fun getAuthorByName(@PathVariable authorName: String): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        return if (author != null) {
            ResponseEntity.ok(author.toAuthorResponse())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the team"))
        }
    }

    @PostMapping
    fun createNewAuthor(@Valid @RequestBody newAuthor: Author): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(newAuthor.name)
        return if (author != null) {
            ResponseEntity.ok(author.toAuthorResponse())
        } else {
            val adress =  Adress(0, newAuthor.adress.street, newAuthor.adress.postCode, newAuthor.adress.country, null)
            adressRepository.save(adress)
            val author = Author(0, newAuthor.name, newAuthor.age, adress)
            ResponseEntity.ok().body(authorRepository.save(author).toAuthorResponse())
        }
    }

    @PutMapping("/{authorName}")
    fun updateAuthorByName(@PathVariable authorName: String, @Valid @RequestBody newAuthor: Author): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        return if (author != null) {
            val updatedAuthor = author.copy(
                    name = newAuthor.name ?: author.name,
                    age = newAuthor.age ?: author.age,
            )
            ResponseEntity.ok().body(authorRepository.save(updatedAuthor).toAuthorResponse())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the author to update"))
        }
    }

    @DeleteMapping("/{authorName}")
    fun deleteAuthorByName(@PathVariable authorName: String): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        return if (author != null) {
            authorRepository.delete(author)
            ResponseEntity.ok<Any>(SuccessResponse("Author successfully deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the author to delete"))
        }
    }
}