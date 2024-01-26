package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Client;
import com.tfg.skilltree.model.Office;

public class ClientFixtures {

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

    public static final Client CLIENT_BY_CODE_DELETED_TRUE =
            Client.builder()
                    .code("1")
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .deleted(true)
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

}
