package com.salesianostriana.dam.damkeep

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table
data class Nota (
        @Column( name = "title" )
        var title : String,
        @Column( name = "body" )
        var body : String,

        @JsonBackReference @ManyToOne var user : User? = null,

        @Column( name = "created_date", nullable = false, updatable = false )
        @CreatedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var timeCreated : LocalDate? = null,

        @Column( name = "last_modified_date", nullable = false )
        @LastModifiedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var lastUpdated : LocalDate? = null,

        @Column( name = "uuid_user" )
        var uuidUser : UUID? = null,

        @Column( name = "id" )
        @Id @GeneratedValue val id : UUID? = null
)



@Entity
data class User(

        @Column(nullable = false, unique = true)
        private var username: String,

        @Column(name = "password")
        private var password: String,

        @Column( name = "fullName" )
        var fullName : String,

        @JsonManagedReference
        @OneToMany( mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE] )
        var notas : List<Nota>? = null,

        @ElementCollection(fetch = FetchType.EAGER)
        val roles: MutableSet<String> = HashSet(),

        private val nonExpired: Boolean = true,

        private val nonLocked: Boolean = true,

        private val enabled: Boolean = true,

        private val credentialsNonExpired : Boolean = true,

        @Id @GeneratedValue val id : UUID? = null


) : UserDetails {

    constructor(username: String, password: String, fullName: String, notas: List<Nota>?, role: String) :
            this(username, password, fullName, notas, mutableSetOf(role), true, true, true, true)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun isEnabled() = enabled
    override fun getUsername() = username
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked


    /**
     * En JPA, dos entidades deberían considerarse iguales
     * sí tienen en mismo ID
     */

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is User)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }

}

