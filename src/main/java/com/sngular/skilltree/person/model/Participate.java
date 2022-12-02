package com.sngular.skilltree.person.model;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
@Value
@RequiredArgsConstructor
public class Participate{
        String code;
        List<Roles> roles;
}