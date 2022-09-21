package io.bank.management.controller;

import io.bank.management.entity.Client;
import io.bank.management.repository.ClientRepository;
import io.bank.management.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientController {

    private ClientService clientService;
    private ClientRepository clientRepository;

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/clients")
    public String addNewClient(@RequestBody Client client){
        if(clientRepository.findByEmail(client.getEmail())==null){

            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            client.setCnp(uuidAsString);

            clientService.addNewClient(client);
            return "Compte crée avec succès";
        }else{
            return "Adresse email déjà utilisée";
        }
    }

    @PatchMapping("/clients")
    public String editClient(@RequestBody Client client){

        if(clientRepository.findByEmail(client.getEmail())==null){
            return "Aucun compte associé à cette adresse email";
        }else{

            Client client1 = clientRepository.findByEmail(client.getEmail());

            client1.setFirstName(client.getFirstName());
            client1.setLastName(client.getLastName());

            clientRepository.save(client1);
            return "Compte modifié avec succès";
        }

    }

    @DeleteMapping("/clients/{id}")
    public String deleteClient(@PathVariable Long id){
        if(clientService.showOneClient(id)==null){
            return "Le compte est introuvable";
        }else{
            clientService.deleteClient(id);
            return "Compte supprimé avec succès";
        }
    }

    @GetMapping("/clients/{id}")
    public Client showOneClient(@PathVariable Long id){
        return clientService.showOneClient(id);
    }

    @GetMapping("/clients")
    public List<Client> showAllClients(){
        return clientService.showAllClients();
    }
    
}
