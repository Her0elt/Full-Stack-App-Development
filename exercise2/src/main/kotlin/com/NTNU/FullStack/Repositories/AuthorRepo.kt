package com.NTNU.FullStack.Repositories

import com.NTNU.FullStack.Model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAuthorByName(name: String?): Author?
}