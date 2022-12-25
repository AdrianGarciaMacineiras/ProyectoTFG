package com.sngular.skilltree.contract;

import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;
}
