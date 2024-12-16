package com.example.proyectoAPISegura.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.Date

@Entity
@Table(name = "historial")
data class Historial(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    var usuario: Usuario? = null,

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "alimento_id")
    @JsonIgnore
    var alimento: Alimento? = null,

    @Column
    var fecha: Timestamp = Timestamp(Date().time)
)
