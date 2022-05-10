# SimuladorCircuito

public class MainActivity extends AppCompatActivity implements SimuladorCircuito.IntefaceSimulador {

    SimuladorCircuito simulador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        simulador = findViewById(R.id.simulador);

        //REVISTOS
        simulador.setNomeEixosGraficoUm("V", "ωt");
        simulador.setNomeEixosGraficoDois("A", "ωt");

        simulador.setNumeroPeriodos(3);
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

        simulador.setSimuladorListener(this);

        // NAO REVISTOS
        simulador.setCircuitoColor(Color.BLUE);
        simulador.setCircuitoWidth(4);
        simulador.setCircuitoGrade(true);
    }

    @Override
    public void componenteClickado(int componente) {
        if (componente == 1) {
            simulador.addSerie(new Serie(valores, 250), 1);
        } else {
            simulador.addSerie(new Serie(valoresY, 15), 1);
        }
    }

    @Override
    public int carregaCircuito(Circuito circuito) {
        circuito.componente(new Ponto(3, 6), new Ponto(3, 4), 1, 1); // Fonte
        circuito.trilha(new Ponto(3, 4), new Ponto(3, 2), new Ponto(8, 2));
        circuito.componente(new Ponto(8, 2), new Ponto(10, 2), 2, 2); // Diodo
        circuito.trilha(new Ponto(10, 2), new Ponto(13, 2), new Ponto(13, 4));
        circuito.componente(new Ponto(13, 4), new Ponto(13, 6), 4, 3); // Carga
        circuito.trilha(new Ponto(13, 6), new Ponto(13, 8), new Ponto(12, 8));
        circuito.trilha(new Ponto(12, 8), new Ponto(10, 8));
        circuito.trilha(new Ponto(10, 8), new Ponto(3, 8), new Ponto(3, 6));
        return 1;
    }


}

