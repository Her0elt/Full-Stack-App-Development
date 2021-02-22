package com.NTNU.FullStack.Services

import com.NTNU.FullStack.Model.Author
import com.NTNU.FullStack.Repositories.AuthorRepository
import com.NTNU.FullStack.factories.AuthorFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthorServiceTest {

    @Autowired
    private lateinit var authorService: AuthorService

    @Autowired
    private lateinit var authorRepo: AuthorRepository

    private lateinit var author: Author


    @BeforeEach
    fun setUp(){
        author = AuthorFactory().`object`
        author = authorRepo.save(author)
    }
    @Test
    fun `test author service get by name returns author`(){
        assert(this.authorService.getAuthorByName(author.name).id.equals(author.id))
    }

    @Test
    fun `test author service_create author creates and returns author`(){
        val newAuthor = AuthorFactory().`object`
        assert(this.authorService.createNewAuthor(newAuthor).name == newAuthor.name)
        assert(this.authorRepo.findAuthorByName(newAuthor.name)!!.name == newAuthor.name)
    }

    @Test
    fun `test author service update author updates and returns author`(){
        author.age = 11111
        assert(this.authorService.updateAuthorByName(author.name, author).id == author.id)
        assert(this.authorRepo.findAuthorByName(author.name)!!.age == (11111))
    }

    @Test
    fun `test author service delete author deletes and returns true`(){
        assert(this.authorService.deleteAuthorByName(author.name) == true)
        assert(this.authorRepo.findAuthorByName(author.name) == (null))
    }


}