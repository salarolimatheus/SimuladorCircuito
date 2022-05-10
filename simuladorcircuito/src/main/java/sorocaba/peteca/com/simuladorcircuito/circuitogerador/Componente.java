package sorocaba.peteca.com.simuladorcircuito.circuitogerador;

import android.graphics.Color;
import android.graphics.Path;

public class Componente {
    public int numeroComponente;
    public float[] dimensoes;
    public Ponto pontoUm, pontoDois;
    public Path path;
    public int componenteColor;

    public Componente(int numeroComponente, Path path, Ponto pontoUm, Ponto pontoDois) {
        this.numeroComponente = numeroComponente;
        this.path = path;
        this.pontoUm = pontoUm;
        this.pontoDois = pontoDois;
        this.dimensoes = new float[2];
        this.componenteColor = 0;
    }

    public Componente(int numeroComponente, Path path, Ponto pontoUm, Ponto pontoDois, int componenteColor) {
        this.numeroComponente = numeroComponente;
        this.path = path;
        this.pontoUm = pontoUm;
        this.pontoDois = pontoDois;
        this.dimensoes = new float[2];
        this.componenteColor = componenteColor;
    }

    public void setDimensoes(float[] dimensoes) {
        this.dimensoes = dimensoes;
    }
}