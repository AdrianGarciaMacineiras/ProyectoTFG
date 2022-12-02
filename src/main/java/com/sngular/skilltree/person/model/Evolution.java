package com.sngular.skilltree.person.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import java.util.Date;

@Value
@RequiredArgsConstructor
public class Evolution{
         String title;
         String category;
         Date fromDate;
}
