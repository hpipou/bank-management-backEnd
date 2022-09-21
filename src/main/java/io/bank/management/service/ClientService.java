package io.bank.management.service;

import io.bank.management.entity.Client;

import java.util.List;

public interface ClientService {
    Client addNewClient(Client client);
    Client editClient(Client client);
    void deleteClient(Long id);
    Client showOneClient(Long id);
    List<Client> showAllClients();
}
