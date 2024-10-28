package com.amitravel.horario;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.amitravel.evento.Evento;
import com.amitravel.negocio.Negocio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "HORARIOS_EVENTOS")
public class Horario implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)", nullable = false)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = true)
    private Evento evento;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "negocio_id", nullable = true)
    private Negocio negocio;

    @Setter
    @Getter
    @Column(name = "abre", columnDefinition = "TIME", nullable = false)
    private LocalTime abre;

    @Setter
    @Getter
    @Column(name = "cierre", columnDefinition = "TIME", nullable = false)
    private LocalTime cierre;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", columnDefinition = "VARCHAR(150)", nullable = true)
    private TipoDia dia;
}
