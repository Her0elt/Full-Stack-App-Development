package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.Repositories.BookRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/author/{authorName}/book/")
class AuthorBookController(
        val authorRepository: AuthorRepository,
        val bookRepository: BookRepository,
) {


    @GetMapping()
    fun getAuthorBookByName(@PathVariable authorName: String): ResponseEntity<*> {
        val book = bookRepository.findBooksByNameContains(authorName)
        return if (book != null) {
            ResponseEntity.ok(book.map { book -> book.toBookList()  })
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the AuthorBook"))
        }
    }

    @PostMapping
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

    @DeleteMapping("{bookName}/")
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