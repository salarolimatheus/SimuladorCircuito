package sorocaba.peteca.com.circuitosimulacao;

import androidx.appcompat.app.AppCompatActivity;
import sorocaba.peteca.com.simuladorcircuito.IntefaceSimulador;
import sorocaba.peteca.com.simuladorcircuito.SimuladorCircuito;
import sorocaba.peteca.com.simuladorcircuito.circuitogerador.Circuito;
import sorocaba.peteca.com.simuladorcircuito.circuitogerador.Ponto;
import sorocaba.peteca.com.simuladorcircuito.graficosgerador.Serie;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IntefaceSimulador {
    double[] valores, valoresC, valoresX, valoresX2, valoresY, valoresZ;
    SimuladorCircuito simulador;
    private boolean animacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double[] valores_x = new double[256];
        valores = new double[256];

        valoresX = new double[256];
        valoresX2 = new double[256];
        valoresY = new double[256];
        valoresZ = new double[256];
        valoresC = new double[256];

        for (int iteracao = 0; iteracao < valores.length; iteracao++) {
            valores_x[iteracao] = iteracao * 2 * Math.PI/valores.length;
            valoresX[iteracao] = 0.5 * 100 * Math.sin(valores_x[iteracao]);
            valoresX2[iteracao] = 0.5 * 100 * Math.cos(valores_x[iteracao]);

            if ((valores_x[iteracao] >= 0) && (valores_x[iteracao] <= Math.PI)) {
                valoresY[iteracao] = valoresX[iteracao];
                valoresZ[iteracao] = 0;
                valoresC[iteracao] = valoresX[iteracao] / 2;
            } else {
                valoresY[iteracao] = 0;
                valoresZ[iteracao] = 0;//valoresX[iteracao];
                valoresC[iteracao] = 0;
            }
        }

        setContentView(R.layout.activity_main);
        simulador = findViewById(R.id.simulador);

        simulador.setSimuladorListener(this);

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

        simulador.addCurva(new Serie(valores, true), 1);
        simulador.removeCurvasFundo(2);
        simulador.addCurvaFundo(new Serie(valoresX), 2);
        simulador.addCurva(new Serie(valores, true), 2);

        simulador.setCircuitoColor(Color.BLUE);
        simulador.setCircuitSelecaoColor(Color.DKGRAY);
        simulador.setCircuitoAnimacaoColor(Color.RED);
        simulador.setCircuitoWidth(4);
        simulador.setCircuitoGrade(true);
        simulador.setCircuitoTextSize(2f);
        simulador.setAnimacaoTime(6000);

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
            simulador.removeCurvasFundo(1);
            simulador.addCurva(new Serie(valoresX), null, new Serie(valoresX2), 1);
//            simulador.addCurvaFundo(new Serie(valoresC), 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresC, true), 2);
        } else if (componente == 2) {
            simulador.removeCurvasFundo(1);
            simulador.addCurva(new Serie(valoresZ), new Serie(valores), null, 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresC, true), 2);
        } else {
            simulador.removeCurvasFundo(1);
            simulador.addCurva(new Serie(valoresY), 1);
            simulador.removeCurvasFundo(2);
            simulador.addCurva(new Serie(valoresC, true), 2);
            simulador.addCurvaFundo(new Serie(valoresY), 2);
            simulador.addCurvaFundo(new Serie(valoresX2), 2);
        }
        simulador.atualizaGraficos();
    }

    @Override
    public int carregaCircuito(Circuito circuito) {
        circuito.setAnimacaoConfig(new double[]{Math.PI, 2 * Math.PI});

        circuito.componente( new Ponto(9, 18), new Ponto(9, 12), 1, 1, Color.RED, new Ponto(13, 16), "V1", new int[]{0}); // Fonte
        circuito.trilha(new Ponto(9, 12), new Ponto(9, 6), new Ponto(24, 6), new int[]{0});
        circuito.seta(new Ponto(12, 6), new Ponto(14, 6), 0);
        circuito.componente(new Ponto(24, 6), new Ponto(30, 6), 2, 2, new int[]{0}); // Diodo
        circuito.trilha(new Ponto(30, 6), new Ponto(39, 6), new Ponto(39, 12), new int[]{0});
        circuito.seta(new Ponto(35, 6), new Ponto(37, 6), 0);
        circuito.componente(new Ponto(39, 12), new Ponto(39, 18), 4, 3,new int[]{0}); // Carga
        circuito.trilha(new Ponto(39, 18), new Ponto(39, 24), new Ponto(12, 24), new int[]{0});
        circuito.trilha(new Ponto(12, 24), new Ponto(30, 24), new int[]{0});
        circuito.seta(new Ponto(24, 24), new Ponto(22, 24), 0);
        circuito.trilha(new Ponto(30, 24), new Ponto(9, 24), new Ponto(9, 18), new int[]{0});

        circuito.texto(new Ponto(27, 11), "D1");
        circuito.texto(new Ponto(42, 16), "Carga");
        return 1;
    }

    @Override
    protected void onPause() {
        simulador.stopAnimacao();
        animacao = false;
        super.onPause();
    }
}
