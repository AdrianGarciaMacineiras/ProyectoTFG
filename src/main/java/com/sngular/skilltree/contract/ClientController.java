package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.ClientApi;
import com.sngular.skilltree.api.model.ClientDTO;
import com.sngular.skilltree.api.model.PatchedClientDTO;
import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @Override
    public ResponseEntity<List<ClientDTO>> getClients() {
        var clientList = clientService.getAll();
        return ResponseEntity.ok(clientMapper.toClientsDTO(clientList));
    }

    @Override
    public ResponseEntity<ClientDTO> addClient(ClientDTO clientDTO) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientService
                        .create(clientMapper
                                .toClient(clientDTO))));
    }

    @Override
    public ResponseEntity<ClientDTO> getClientByCode(String clientcode) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientService
                        .findByCode(clientcode)));
    }

    @Override
    public ResponseEntity<Void> deleteClient(String clientcode) {
        var result = clientService.deleteBeCode(clientcode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<ClientDTO> updateClient(String clientcode, ClientDTO clientDTO) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientService
                        .update(clientcode, clientMapper
                                .toClient(clientDTO))));
    }

    @Override
    public ResponseEntity<ClientDTO> patchClient(String clientcode, PatchedClientDTO patchedClientDTO) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientService
                        .patch(clientcode, clientMapper
                                .toClient(patchedClientDTO))));
    }
}
