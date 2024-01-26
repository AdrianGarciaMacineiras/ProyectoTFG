package com.tfg.skilltree.model.views;

import java.util.List;

import com.tfg.skilltree.model.Knows;
import lombok.Builder;

@Builder(toBuilder = true)
public record PeopleView(String code, String employeeId, String name, String surname, String title,
                         String teamShortName, List<Knows> knows) {
}
