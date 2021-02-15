package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Exception.AuthorNotFoundException
import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.AdressRepository
import com.NTNU.FullStack.Repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
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


    fun getAuthorByName(authorName: String): Author {
       authorRepository.findAuthorByName(authorName).run{
           if(this == null)throw AuthorNotFoundException("Could not find the Author")
           return this
       }
    }

    fun createNewAuthor(newAuthor: Author): Author {
        authorRepository.findAuthorByName(newAuthor.name).run {
            if (this != null) return this
            val adress =  Adress(0, newAuthor.adress.street, newAuthor.adress.postCode, newAuthor.adress.country, null)
            adressRepository.save(adress)
            val author = Author(0, newAuthor.name, newAuthor.age, adress)
            authorRepository.save(author)
            return  author
        }
    }

    fun updateAuthorByName(authorName: String, @Valid @RequestBody newAuthor: Author): Author {
        authorRepository.findAuthorByName(authorName).run{
            if(this == null)throw AuthorNotFoundException("Could not find the Author")
            val updatedAuthor = this.copy(
                    name = newAuthor.name ?: this.name,
                    age = newAuthor.age ?: this.age,
            )
            return authorRepository.save(updatedAuthor)
        }
    }

    fun deleteAuthorByName(authorName: String): Boolean {
        authorRepository.findAuthorByName(authorName).run{
            if(this == null)throw AuthorNotFoundException("Could not find the Author")
            authorRepository.delete(this)
        }
        return true
    }
}



