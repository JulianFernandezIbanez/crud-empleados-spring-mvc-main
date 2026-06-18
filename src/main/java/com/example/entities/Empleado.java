package com.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.models.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//Si quieres cambiarle el nombre a la tabla, si no la tabla se llamara igual que la clase
@Table(name="empleados")
//Dependencias de Lombok
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"emails", "telefonos"})
@Builder
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    //Hacer que un campo sea la primary key, auto incremental y not null en la tabla a crear
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    //Especificar que para que el enum sea los valores escritos que no la posicion (HOMBRE, MUJER, OTRO en vez de 0, 1, 2)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    //Especificar como quieres que se guarde el formato de la fecha
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaAlta;

    private BigDecimal salario;

    //Relacionar empleado con departamento
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Telefono> telefonos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Correo> emails = new HashSet<>();

}
