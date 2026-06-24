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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El campo nombre no puede estar vacio")
    @NotBlank(message = "El campo nombre no puede contener unicamente espacios en blanco")
    @Size(min = 4, max = 30, message = "El nombre no cumple los requisitos (minimo 4 y maximo 30 caracteres)")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ][a-záéíóúüñ]+(\s)?)+$", message = "El nombre solo puede contener los caracteres de la A a la Z y su primer caracter a de ser una letra mayuscula (A-Z)")
    private String nombre;

    @NotNull(message = "El campo Primer Apellido no puede estar vacio")
    @NotBlank(message = "El campo Primer Apellido no puede contener unicamente espacios en blanco")
    @Size(min = 4, max = 30, message = "No cumples los requisitos (minimo 4 y maximo 30 caracteres)")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ][a-záéíóúüñ]+(\s)?)+$", message = "El Primer Apellido solo puede contener los caracteres de la A a la Z y su primer caracter a de ser una letra mayuscula (A-Z)")
    private String primerApellido;

    @NotNull(message = "El campo Segundo Apellido no puede contener unicamente espacios en blanco")
    @Size(max = 30, message = "No cumples los requisitos (minimo 4 y maximo 30 caracteres)")
    @Pattern(regexp = "^(|[A-ZÁÉÍÓÚÑ][a-záéíóúüñ]{2,}+(\s)?)+$", message = "El Segundo Apellido solo puede contener los caracteres de la A a la Z y su primer caracter a de ser una letra mayuscula (A-Z)")
    private String segundoApellido;

    //Especificar que para que el enum sea los valores escritos que no la posicion (HOMBRE, MUJER, OTRO en vez de 0, 1, 2)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    //Especificar como quieres que se guarde el formato de la fecha
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha de Alta no puede ser inferior a la fecha actual")
    private LocalDate fechaAlta;

    @DecimalMin(value = "1000.00", inclusive = true, message = "El salario no puede ser inferior a 1000")
    //@Positive(message = "El salario no puede ser negativo")
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

    private String foto;

}
