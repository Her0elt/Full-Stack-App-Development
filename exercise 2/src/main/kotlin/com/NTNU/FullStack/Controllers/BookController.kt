package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.BookRepository
import com.NTNU.FullStack.Services.BookService
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/book/")
class BookController {

    @Autowired
    private lateinit var bookService: BookService

    @GetMapping
    fun getAll(@RequestParam  name:String?, @RequestParam  authorName: String?): ResponseEntity<*> =bookService.getAllBooks(name, authorName)

    @GetMapping("{bookName}/")
    fun get(@PathVariable bookName: String): ResponseEntity<*> = bookService.getBookByName(bookName);

    @PostMapping
    fun create(@Valid @RequestBody newBook: Book): ResponseEntity<*> =bookService.createNewBook(newBook)

    @PutMapping("{bookName}/")
    fun update(@PathVariable bookName: String, @Valid @RequestBody newBook: Book): ResponseEntity<*> = bookService.updateBookByName(bookName, newBook)

    @DeleteMapping("{bookName}/")
    fun delete(@PathVariable bookName: String): ResponseEntity<*> = bookService.deleteBookByName(bookName)
}