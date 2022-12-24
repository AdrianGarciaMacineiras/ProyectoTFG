package com.sngular.skilltree.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;


@Builder
public record People(String code, String name, String surname, Date birthDate, EnumTitle enumTitle, Evolution evolution,
                     Knows knows, List<String> work_with, List<String> master, Participate participate,
                     List<String> interest) {

}
