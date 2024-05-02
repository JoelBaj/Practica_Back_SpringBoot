package com.joel.ec.controller;

import com.joel.ec.model.dto.ClienteDto;
import com.joel.ec.model.entity.Cliente;
import com.joel.ec.model.payload.MensajeResponse;
import com.joel.ec.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("clientes")
    public ResponseEntity<?>  showAll(){
        List<Cliente> geList = clienteService.listAlll();

        if (geList == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registro")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(geList)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("cliente")
    public ResponseEntity<?>  create(@RequestBody ClienteDto clienteDto){
        Cliente clientesave = null;
        try {
            clientesave = clienteService.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(ClienteDto.builder()
                            .idCliente(clientesave.getIdCliente())
                            .nombre(clientesave.getNombre())
                            .apellido(clientesave.getApellido())
                            .correo(clientesave.getCorreo())
                            .fechaRegistro(clientesave.getFechaRegistro())
                            .build())
                    .build()
                    ,HttpStatus.CREATED
            );
        }catch (DataAccessException exDT ){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDT.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?>  update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id){
        Cliente clienteupdate = null;
        try {
            if( clienteService.existsById(id)){
                clienteDto.setIdCliente(id);
                clienteupdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(ClienteDto.builder()
                                .idCliente(clienteupdate.getIdCliente())
                                .nombre(clienteupdate.getNombre())
                                .apellido(clienteupdate.getApellido())
                                .correo(clienteupdate.getCorreo())
                                .fechaRegistro(clienteupdate.getFechaRegistro())
                                .build())
                        .build()
                        ,HttpStatus.CREATED
                );
            }else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("el registro que actualizar no se encuantra en la bases de datos")
                                .object(null)
                                .build()
                        , HttpStatus.METHOD_NOT_ALLOWED);
            }


        }catch (DataAccessException exDT ){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDT.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
         try {
             Cliente clienteDelete = clienteService.findById(id);
             clienteService.delete(clienteDelete);
             return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
         }catch (DataAccessException exDT ){
             return new ResponseEntity<>(
                     MensajeResponse.builder()
                             .mensaje(exDT.getMessage())
                             .object(null)
                             .build()
                     , HttpStatus.METHOD_NOT_ALLOWED);
         }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?>  showById(@PathVariable Integer id){
        Cliente cliente = clienteService.findById(id);

        if (cliente == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar no exite")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }
}
