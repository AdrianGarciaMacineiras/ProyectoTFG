package com.sngular.skilltree.model.views;

import com.sngular.skilltree.model.Knows;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record PeopleView(String code, String employeeId, String name, String surname, String title,
                         String teamShortName, List<Knows> knows) {
}
