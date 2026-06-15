package com.example.services;

import java.util.List;

import com.example.entities.Departamento;

public interface DepartamentoService {

    List<Departamento> getAllDepartamentos();
    Departamento saveDepartamento(Departamento departamento);

}
