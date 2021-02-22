package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Controllers.AuthorController
import com.NTNU.FullStack.Exception.BookNotFoundExecption
import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.BookRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
        val books: List<Book>? = when {
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
        if (books != null) {
            return ResponseEntity.ok(books.map{ book -> book.toBookList()})
        }
        throw BookNotFoundExecption("Could not find the Book")
    }


    fun getBookByName(bookName: String): Book {
        bookRepository.findBookByName(bookName).run{
            if(this == null)throw BookNotFoundExecption("Could not find the Book")
            return this
        }
    }

    fun createNewBook(newBook: Book): Book {
        bookRepository.findBookByName(newBook.name).run {
            if (this != null) return this
            val book = Book(0, newBook.name, newBook.authors)
            bookRepository.save(book)
            return  book
        }
    }

    fun updateBookByName(bookName: String, newBook: Book): Book {
        bookRepository.findBookByName(bookName).run{
            if(this == null)throw BookNotFoundExecption("Could not find the Book")
            val updatedBook = this.copy(
                    name = newBook.name,
                    authors = newBook.authors,
            )
            return bookRepository.save(updatedBook)
        }
    }

    fun deleteBookByName(bookName: String): Boolean{
        bookRepository.findBookByName(bookName).run{
            if(this == null)throw BookNotFoundExecption("Could not find the Book")
            bookRepository.delete(this)
        }
        return true

    }

}