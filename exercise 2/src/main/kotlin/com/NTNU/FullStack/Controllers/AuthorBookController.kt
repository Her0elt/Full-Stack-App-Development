package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Services.AuthorBookService
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/author/{authorName}/book/")
class AuthorBookController{

    @Autowired
    lateinit var authorBookService: AuthorBookService

    @GetMapping()
    fun get(@PathVariable authorName: String): ResponseEntity<*> = authorBookService.getAuthorBookByName(authorName)

    @PostMapping
    fun createNewAuthorBook(@PathVariable authorName: String,@Valid @RequestBody newBook: Book): ResponseEntity<*> = authorBookService.createNewAuthorBook(authorName,newBook)

    @DeleteMapping("{bookName}/")
    fun deleteAuthorByName(@PathVariable authorName: String, @PathVariable bookName: String): ResponseEntity<*> = authorBookService.deleteAuthorByName(authorName, bookName)
}