package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.ClientApi;
import com.sngular.skilltree.api.model.ClientDTO;
import com.sngular.skilltree.api.model.PatchedClientDTO;
import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.application.updater.ClientUpdater;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final ClientService clientService;

    private final ClientUpdater clientUpdater;

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
    public ResponseEntity<ClientDTO> getClientByCode(String clientCode) {
        var result = clientMapper
                .toClientDTO(clientService
                        .findByCode(clientCode));
        log.info(result.toString());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteClient(String clientCode) {
        var result = clientService.deleteByCode(clientCode);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<ClientDTO> updateClient(String clientCode, ClientDTO clientDTO) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientUpdater
                        .update(clientCode, clientMapper
                                .toClient(clientDTO))));
    }

    @Override
    public ResponseEntity<ClientDTO> patchClient(String clientCode, PatchedClientDTO patchedClientDTO) {
        return ResponseEntity.ok(clientMapper
                .toClientDTO(clientUpdater
                        .patch(clientCode, clientMapper
                                .toClient(patchedClientDTO))));
    }
}
