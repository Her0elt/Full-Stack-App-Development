package com.NTNU.FullStack.Model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "author")
data class Author(
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Long = 0,
            @NotNull
            var name:String,
            @NotNull
            var age:Int,
            @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "adress_id", referencedColumnName = "id")
            @JsonManagedReference
            var adress: Adress,
            @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
            @JoinTable(
                    name = "author_book",
                    joinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")],
                    inverseJoinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
                    uniqueConstraints = [UniqueConstraint(columnNames = ["author_id", "book_id"])]
            )
            @JsonBackReference
            @JsonIgnore
            var books: MutableList<Book> = mutableListOf())

data class AuthorDtoList(val id: Long, val name: String)

fun Author.toAuthorDto(): AuthorDtoList {
    return AuthorDtoList(
            this.id,
            this.name,
    )
}
data class AuthorResponse(val id: Long, val name: String, val adress: AdressDto, val books: List<BookDtoList> )

fun Author.toAuthorResponse(): AuthorResponse {
    return AuthorResponse(
            this.id,
            this.name,
            this.adress.toAdressDto(),
            this.books.map { book -> book.toBookDto() },
    )
}