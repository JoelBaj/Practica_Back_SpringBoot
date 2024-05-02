package com.joel.ec.service;

import com.joel.ec.model.dto.ClienteDto;
import com.joel.ec.model.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> listAlll();

    Cliente save(ClienteDto cliente);

    Cliente findById(Integer id);

    void delete(Cliente cliente);
    boolean existsById(Integer id);
}
