package com.sngular.skilltree.fixtures;

import java.util.List;

import com.sngular.skilltree.model.Client;
import com.sngular.skilltree.model.Office;
import com.sngular.skilltree.testutil.FileHelper;

public class ClientFixtures {

    public static final String CLIENT_BY_CODE_JSON = FileHelper.getContent("/client/client_by_code.json");

    public static final String UPDATED_CLIENT_BY_CODE_JSON = FileHelper.getContent("/client/updated_client_by_code.json");

    public static final String PATCH_CLIENT_BY_CODE_JSON = FileHelper.getContent("/client/patched_client_by_code.json");

    public static final String LIST_CLIENT_JSON = FileHelper.getContent("/client/list_client.json");

    public static final Office OFFICE =
            Office.builder()
                    .code("itxhq")
                    .address("Baños de Arteixo S/N")
                    .phone("555 89 90 09")
                    .geolocation("45.667776, 12.455555")
                    .name("Servicios Centrales")
                    .build();

    public static final Client CLIENT_BY_CODE =
            Client.builder()
                    .code("1")
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .build();

    public static final Client CLIENT2_BY_CODE =
            Client.builder()
                    .code("2")
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .build();

    public static final List<Client> CLIENT_LIST = List.of(CLIENT_BY_CODE, CLIENT2_BY_CODE);

    public static final Client UPDATED_CLIENT_BY_CODE =
            Client.builder()
                    .code("1")
                    .country("Spain")
                    .industry("TECH")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .build();
}
