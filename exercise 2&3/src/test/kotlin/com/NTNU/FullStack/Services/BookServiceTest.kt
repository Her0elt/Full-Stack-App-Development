package com.NTNU.FullStack.Services


import com.NTNU.FullStack.Model.Book
import com.NTNU.FullStack.Repositories.BookRepository
import com.NTNU.FullStack.factories.BookFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest {

    @Autowired
    private lateinit var bookService: BookService

    @Autowired
    private lateinit var bookRepo: BookRepository

    private lateinit var book: Book


    @BeforeEach
    fun setUp(){
        book = BookFactory().`object`
        book = bookRepo.save(book)
    }
    @Test
    fun `test book service get by name returns book`(){
        assert(this.bookService.getBookByName(book.name).id == book.id)
    }

    @Test
    fun `test book_service_create_book_creates_and_returns_book`(){
        val newBook = BookFactory().`object`
        assert(this.bookService.createNewBook(newBook).name == newBook.name)
        assert(this.bookRepo.findBookByName(newBook.name)!!.name == newBook.name)
    }

    @Test
    fun test_book_service_update_author_updates_and_returns_book(){
        val name = book.name
        book.name = "tester"
        assert(this.bookService.updateBookByName(name, book).name == book.name)
        assert(this.bookRepo.findBookByName(book.name)!!.name == "tester")
    }

    @Test
    fun test_book_service_delete_author_deletes_and_returns_true(){
        assert(this.bookService.deleteBookByName(book.name))
    }


}