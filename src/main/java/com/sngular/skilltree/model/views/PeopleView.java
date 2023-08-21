package com.sngular.skilltree.model.views;

import java.util.List;

import com.sngular.skilltree.model.Knows;
import lombok.Builder;

@Builder(toBuilder = true)
public record PeopleView(String code, String employeeId, String name, String surname, String title,
                         String teamShortName, List<Knows> knows) {
}
