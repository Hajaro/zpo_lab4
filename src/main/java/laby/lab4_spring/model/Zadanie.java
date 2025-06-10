package laby.lab4_spring.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="zadanie")
public class Zadanie {
    @Id
    @GeneratedValue
    @Column(name="zadanie_id")
    private Integer zadanieId;
    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;
    @Column(nullable = false, length = 50)
    private String nazwa;
    @Column(nullable = false)
    private Integer kolejnosc;
    @Column(nullable = false, length = 1000)
    private String opis;
    @CreatedDate
    @Column(name = "dataczas_dodania", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Zadanie() {
    }

    public Zadanie(String nazwa, Integer kolejnosc, String opis) {
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
    }
}