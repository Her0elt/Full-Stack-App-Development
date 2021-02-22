package com.NTNU.FullStack.Controllers


import com.NTNU.FullStack.Controllers.AuthorController
import com.NTNU.FullStack.Exception.AuthorNotFoundException
import com.NTNU.FullStack.Services.AuthorService
import com.NTNU.FullStack.factories.AuthorFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.*
import org.springframework.boot.test.mock.mockito.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(AuthorController::class)
class AuthorControllerTest {

    private val URL = "/api/author/"

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var authorService: AuthorService



    @Test
    fun `test author controller GET returns OK`() {
        val author = AuthorFactory().`object`
        given(this.authorService.getAuthorByName(author.name))
                .willReturn(author)

        this.mvc.perform(get("$URL{name}", author.name))
                .andExpect(status().isOk)
    }

    @Test
    fun `test author controller GET returns not found`() {
        given(this.authorService.getAuthorByName("test"))
                .willThrow(AuthorNotFoundException(""))
        this.mvc.perform(get("$URL{name}","test"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `test author controller POST returns OK`() {
        val author = AuthorFactory().`object`
        given(this.authorService.createNewAuthor(author))
                .willReturn(author)

        this.mvc.perform(post(URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk)
    }

    @Test
    fun `test author controller PUT returns OK`() {
        val author = AuthorFactory().`object`
        given(this.authorService.updateAuthorByName(author.name, author))
                .willReturn(author)

        this.mvc.perform(put("$URL{name}/", author.name)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk)
    }

    @Test
    fun `test author controller PUT returns not found`() {
        val author = AuthorFactory().`object`
        given(this.authorService.updateAuthorByName("test", author))
                .willThrow(AuthorNotFoundException(""))

        this.mvc.perform(put("$URL{name}/","test")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `test author controller DELETE returns OK`() {
        val author = AuthorFactory().`object`
        given(this.authorService.deleteAuthorByName(author.name))
                .willReturn(true)

        this.mvc.perform(delete("$URL{name}/", author.name))
                .andExpect(status().isOk)
    }

    @Test
    fun `test author controller DELETE return NotFound`() {
        given(this.authorService.deleteAuthorByName("test"))
                .willThrow(AuthorNotFoundException(""))
        this.mvc.perform(delete("$URL{name}/","test"))
                .andExpect(status().isNotFound)
    }
}