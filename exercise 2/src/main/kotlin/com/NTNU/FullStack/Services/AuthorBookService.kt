package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Model.Book
import com.NTNU.FullStack.Model.toAuthorResponse
import com.NTNU.FullStack.Model.toBookList
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.Repositories.BookRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service
class AuthorBookService {

    @Autowired
    private lateinit var authorRepository: AuthorRepository
    @Autowired
    private lateinit var  bookRepository: BookRepository

    fun getAuthorBookByName(@PathVariable authorName: String): ResponseEntity<*> {
        val book = bookRepository.findBooksByNameContains(authorName)
        return if (book != null) {
            ResponseEntity.ok(book.map { book -> book.toBookList()  })
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the AuthorBook"))
        }
    }

    fun createNewAuthorBook(@PathVariable authorName: String,@Valid @RequestBody newBook: Book): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        val book = bookRepository.findBookByName(newBook.name)
        return if (book != null && author != null) {
            author.books.add(book)
            ResponseEntity.ok().body(authorRepository.save(author).toAuthorResponse())
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Any>(ErrorResponse("The AuthorBook does not exist"))
        }
    }

    fun deleteAuthorByName(@PathVariable authorName: String, @PathVariable bookName: String): ResponseEntity<*> {
        val author = authorRepository.findAuthorByName(authorName)
        val book = bookRepository.findBookByName(bookName)
        return if (author != null && book != null) {
            author.books.remove(book)
            authorRepository.save(author)
            ResponseEntity.ok<Any>(SuccessResponse("AuthorBook successfully deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the AuthorBook to delete"))
        }
    }

}