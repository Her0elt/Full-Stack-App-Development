package com.NTNU.FullStack.Controllers


import com.NTNU.FullStack.Exception.BookNotFoundExecption
import com.NTNU.FullStack.Services.BookService
import com.NTNU.FullStack.factories.BookFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(BookController::class)
class BookControllerTest {


        private val URL = "/api/book/"

        @Autowired
        private lateinit var objectMapper: ObjectMapper

        @Autowired
        private lateinit var mvc: MockMvc

        @MockBean
        private lateinit var bookService: BookService



        @Test
        fun `test author controller GET return OK`() {
            val author = BookFactory().`object`
            given(this.bookService.getBookByName(author.name))
                    .willReturn(author)

            this.mvc.perform(MockMvcRequestBuilders.get("$URL{name}", author.name))
                    .andExpect(MockMvcResultMatchers.status().isOk)
        }

        @Test
        fun `test author controller GET return NotFound`() {
            given(this.bookService.getBookByName("test"))
                    .willThrow(BookNotFoundExecption(""))
            this.mvc.perform(MockMvcRequestBuilders.get("$URL{name}", "test"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

        @Test
        fun `test author controller POST returns OK`() {
            val author = BookFactory().`object`
            given(this.bookService.createNewBook(author))
                    .willReturn(author)

            this.mvc.perform(MockMvcRequestBuilders.post(URL)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(author)))
                    .andExpect(MockMvcResultMatchers.status().isOk)
        }

        @Test
        fun `test author controller PUT returns OK`() {
            val book = BookFactory().`object`
            given(this.bookService.updateBookByName(book.name, book))
                    .willReturn(book)

            this.mvc.perform(MockMvcRequestBuilders.put("$URL{name}/", book.name)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(book)))
                    .andExpect(MockMvcResultMatchers.status().isOk)
        }

        @Test
        fun `test author controller PUT return NotFound`() {
            val book = BookFactory().`object`
            BDDMockito.given(this.bookService.updateBookByName("test", book))
                    .willThrow(BookNotFoundExecption(""))

            this.mvc.perform(MockMvcRequestBuilders.put("$URL{name}/", "test")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(book)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

        @Test
        fun `test author controller DELETE returns OK`() {
            val book = BookFactory().`object`
            given(this.bookService.deleteBookByName(book.name))
                    .willReturn(true)

            this.mvc.perform(MockMvcRequestBuilders.delete("$URL{name}/", book.name))
                    .andExpect(MockMvcResultMatchers.status().isOk)
        }

        @Test
        fun `test author controller DELETE Return NotFound`() {
            given(this.bookService.deleteBookByName("test"))
                    .willThrow(BookNotFoundExecption(""))
            this.mvc.perform(MockMvcRequestBuilders.delete("$URL{name}/", "test"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

}