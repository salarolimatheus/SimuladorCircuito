package sorocaba.peteca.com.circuitosimulacao;

import androidx.appcompat.app.AppCompatActivity;
import sorocaba.peteca.com.simuladorcircuito.IntefaceSimulador;
import sorocaba.peteca.com.simuladorcircuito.SimuladorCircuito;
import sorocaba.peteca.com.simuladorcircuito.circuitogerador.Circuito;
import sorocaba.peteca.com.simuladorcircuito.circuitogerador.Ponto;
import sorocaba.peteca.com.simuladorcircuito.graficosgerador.Serie;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements IntefaceSimulador {
    double[] valores, valoresA, valoresB, valoresX, valoresY, valoresZ;
    SimuladorCircuito simulador;
    private boolean animacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double[] valores_x = new double[256];
        valores = new double[256];
        valoresA = new double[256];
        valoresB = new double[256];
        valoresX = new double[256];
        valoresY = new double[256];
        valoresZ = new double[256];
        for (int i = 0; i <= 255; i++) {
            valores_x[i] = (2 * Math.PI * i) / 255;
            valores[i] = Math.sin(valores_x[i]);
            valoresA[i] = 0.4;
            valoresB[i] = 0.7;
            valoresX[i] = Math.sin(valores_x[i]);
            valoresY[i] = Math.sin(valores_x[i] - 2.0943951024);
            valoresZ[i] = Math.sin(valores_x[i] - (2*2.0943951024));
        }

        setContentView(R.layout.activity_main);
        simulador = findViewById(R.id.simulador);

        //REVISTOS
        simulador.setNomeEixosGraficoUm("V", "ωt");
        simulador.setNomeEixosGraficoDois("A", "ωt");

        simulador.setNumeroPeriodos(1);
        simulador.setCursorConfig(Color.BLUE, 3);
        simulador.setCursorStatus(true);
        simulador.setGradeStatus(true);
        simulador.setBetaStatus(true);
        simulador.setOndasSimultaneasGraficoUm(true);
        simulador.setOndasSimultaneasGraficoDois(true);

        simulador.setEixosWidth(5);
        simulador.setEixosHeigthMarcacoes(0.05f);
        simulador.setEixosTextSize(1.5f);
        simulador.setEixosSubTextSize(1.3f);

        simulador.setColorFonteUm(Color.RED);
        simulador.setColorFonteDois(Color.GREEN);
        simulador.setColorFonteTres(Color.MAGENTA);
        simulador.setColorCorrente(Color.RED);
        simulador.setCurvaWidth(5);

        simulador.addCurva(new Serie(valores, 250), 1);
        simulador.removeCurvasFundo(2);
        simulador.addCurvaFundo(new Serie(valoresX), 2);
        simulador.addCurva(new Serie(valores, 250), 2);

        simulador.setCircuitoColor(Color.BLUE);
        simulador.setCircuitSelecaoColor(Color.DKGRAY);
        simulador.setCircuitoWidth(4);
        simulador.setCircuitoGrade(true);

        simulador.setSimuladorListener(this);

        Button botaoAnimar = findViewById(R.id.botao_animar);
        botaoAnimar.setOnClickListener(view -> {
            if(animacao)
                simulador.pauseResumeAnimacao();
        });

        botaoAnimar.setOnLongClickListener(view -> {
            if (!animacao) {
                simulador.startAnimacao();
                botaoAnimar.setBackgroundColor(Color.GREEN);
                animacao = true;
            } else {
                simulador.stopAnimacao();
                botaoAnimar.setBackgroundColor(Color.RED);
                animacao = false;
            }
            return true;
        });
    }

    @Override
    public void componenteClickado(int componente) {
        if (componente == 1) {
            simulador.addCurva(new Serie(valores, 250), null, new Serie(valoresZ), 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresA, 250), 2);
        } else if (componente == 2) {
            simulador.addCurva(new Serie(valoresZ, 250), new Serie(valores), null, 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresA, 250), 2);
        } else {
            simulador.addCurva(new Serie(valoresY, 15), 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresY, 15), 2);
            simulador.addCurvaFundo(new Serie(valoresX), 2);
            simulador.addCurvaFundo(new Serie(valoresY), 2);
            simulador.addCurvaFundo(new Serie(valoresZ), 2);
        }
    }

    @Override
    public int carregaCircuito(Circuito circuito) {
        circuito.componente(new Ponto(9, 18), new Ponto(9, 12), 1, 1); // Fonte
        circuito.trilha(new Ponto(9, 12), new Ponto(9, 6), new Ponto(24, 6));
        circuito.componente(new Ponto(24, 6), new Ponto(30, 6), 2, 2); // Diodo
        circuito.trilha(new Ponto(30, 6), new Ponto(39, 6), new Ponto(39, 12));
        circuito.componente(new Ponto(39, 12), new Ponto(39, 18), 4, 3); // Carga
        circuito.trilha(new Ponto(39, 18), new Ponto(39, 24), new Ponto(12, 24));
        circuito.trilha(new Ponto(12, 24), new Ponto(30, 24));
        circuito.trilha(new Ponto(30, 24), new Ponto(9, 24), new Ponto(9, 18));
        return 1;
    }

    @Override
    protected void onPause() {
        simulador.cancelTimer();
        super.onPause();
    }
}
