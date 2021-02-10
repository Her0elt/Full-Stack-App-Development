package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.AdressRepository
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service
class AuthorService{

    @Autowired
    private lateinit var authorRepository: AuthorRepository
    @Autowired
    private lateinit var adressRepository: AdressRepository

    fun getAllAuthors(): List<AuthorList> = authorRepository.findAll().map { author -> author.toAuthorList() }

    fun getAuthorByName(authorName: String): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        return if (author != null) {
            ResponseEntity.ok(author.toAuthorResponse())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the team"))
        }
    }

    fun createNewAuthor(newAuthor: Author): ResponseEntity<*> {
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

    fun updateAuthorByName(authorName: String, @Valid @RequestBody newAuthor: Author): ResponseEntity<*> {
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

    fun deleteAuthorByName(authorName: String): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        return if (author != null) {
            authorRepository.delete(author)
            ResponseEntity.ok<Any>(SuccessResponse("Author successfully deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the author to delete"))
        }
    }
}



