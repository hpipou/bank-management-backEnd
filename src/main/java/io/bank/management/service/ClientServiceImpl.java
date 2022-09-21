package io.bank.management.service;

import io.bank.management.entity.Client;
import io.bank.management.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addNewClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client editClient(Client client) {
        Client client1=clientRepository.findByEmail(client.getEmail());

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        client1.setFirstName(client.getFirstName());
        client1.setLastName(client.getLastName());
        client1.setCnp(uuidAsString);

        return clientRepository.save(client1);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client showOneClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> showAllClients() {
        return clientRepository.findAll();
    }
}
