package com.NTNU.FullStack.Controllers

import com.NTNU.FullStack.Model.*
import com.NTNU.FullStack.Repositories.AdressRepository
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.Services.AuthorService
import com.NTNU.FullStack.utils.ErrorResponse
import com.NTNU.FullStack.utils.SuccessResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/author/")
class AuthorController {


    @Autowired
    private lateinit var authorService: AuthorService

    @GetMapping
    fun getAll(): List<AuthorList> = authorService.getAllAuthors();

    @GetMapping("{authorName}/")
    fun get(@PathVariable authorName: String): ResponseEntity<*> = authorService.getAuthorByName(authorName);

    @PostMapping
    fun create(@Valid @RequestBody newAuthor: Author): ResponseEntity<*> = authorService.createNewAuthor(newAuthor)

    @PutMapping("{authorName}/")
    fun update(@PathVariable authorName: String, @Valid @RequestBody newAuthor: Author): ResponseEntity<*> =authorService.updateAuthorByName(authorName,newAuthor )

    @DeleteMapping("{authorName}")
    fun delete(@PathVariable authorName: String): ResponseEntity<*> =authorService.deleteAuthorByName(authorName);
}