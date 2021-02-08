package com.NTNU.FullStack.Repositories

import com.NTNU.FullStack.Model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findBookByName(name: String?): Book?
    fun findBooksByNameContains(name: String?): List<Book>
    fun findByAuthors_NameContains(name: String?): List<Book>
    fun findByNameContainingAndAuthors_NameContains(name :String?, authorName:String?): List<Book>
}