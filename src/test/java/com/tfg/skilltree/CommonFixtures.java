package com.tfg.skilltree;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonFixtures {
    public static LocalDate date = LocalDate.parse("20-01-2023 00:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
}
