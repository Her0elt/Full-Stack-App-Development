package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Controllers.AuthorController
import com.NTNU.FullStack.Model.Book
import com.NTNU.FullStack.Model.toBookList
import com.NTNU.FullStack.Model.toBookResponse
import com.NTNU.FullStack.Repositories.BookRepository
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository

    companion object{
        var logger: Logger = LoggerFactory.getLogger(AuthorController::class.java)
    }

    fun getAllBooks( name:String?, authorName: String?): ResponseEntity<*> {
        val books: List<Book> = when {
            (name != null && authorName != null) -> {
                logger.info("Search for book by name: $name and author: $authorName")
                bookRepository.findByNameContainingAndAuthors_NameContains(name, authorName)
            }
            (name != null) -> {
                logger.info("Search for book by name: $name")
                bookRepository.findBooksByNameContains(name)
            }
            (authorName != null) -> {
                logger.info("Search for book by author: $authorName")
                bookRepository.findByAuthors_NameContains(authorName)
            }
            else -> {
                bookRepository.findAll()
            }
        }
        return ResponseEntity.ok(books.map{ book -> book.toBookList()})
    }

    fun getBookByName(bookName: String): ResponseEntity<*> {
        val book = bookRepository.findBookByName(bookName)
        return if (book != null) {
            ResponseEntity.ok(book.toBookResponse())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the book"))
        }
    }

    fun createNewBook(newBook: Book): ResponseEntity<*> {
        val book = bookRepository.findBookByName(newBook.name)
        return if (book != null) {
            ResponseEntity.ok(book)
        } else {
            val book = Book(0, newBook.name, newBook.authors)
            ResponseEntity.ok().body(bookRepository.save(book).toBookResponse())
        }
    }

    fun updateBookByName(bookName: String, newBook: Book): ResponseEntity<*> {
        val book = bookRepository.findBookByName(bookName)
        return if (book != null) {
            val updatedBook = book.copy(
                    name = newBook.name ?: book.name,
                    authors = newBook.authors ?: book.authors,
            )
            ResponseEntity.ok().body(bookRepository.save(updatedBook).toBookResponse())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the book to update"))
        }
    }

    fun deleteBookByName(bookName: String): ResponseEntity<*> {
        val book = bookRepository.findBookByName(bookName)
        return if (book != null) {
            bookRepository.delete(book)
            ResponseEntity.ok<Any>(SuccessResponse("book successfully deleted"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body<Any>(ErrorResponse("Could not find the book to delete"))
        }
    }

}