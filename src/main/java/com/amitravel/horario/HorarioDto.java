package com.amitravel.horario;

import java.time.LocalTime;

import lombok.Data;

@Data
public class HorarioDto {
    private Long id;
    private LocalTime abre;
    private LocalTime cierre;
    private TipoDia dia;
}
