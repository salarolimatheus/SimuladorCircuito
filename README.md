# SimuladorCircuito

	public class MainActivity extends AppCompatActivity implements SimuladorCircuito.IntefaceSimulador {
	    SimuladorCircuito simulador;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		simulador = findViewById(R.id.simulador);
		simulador.setNomeEixosGraficoUm("V", "ωt");
		simulador.setNomeEixosGraficoDois("A", "ωt");

		simulador.setNumeroPeriodos(1);
		simulador.setCursorConfig(Color.BLUE, 3);
		simulador.setCursorStatus(true);
		simulador.setGradeStatus(true);
		simulador.setBetaStatus(true);
		simulador.setOndasSimultaneasGraficoUm(false);
		simulador.setOndasSimultaneasGraficoDois(false);

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
		simulador.setCircuitoAnimacaoColor(Color.RED);
		simulador.setCircuitoWidth(4);
		simulador.setCircuitoGrade(false);
		simulador.setCircuitoTextSize(2f);
		simulador.setAnimacaoTime(6000);

		simulador.setSimuladorListener(this);
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
		circuito.setAnimacaoConfig(new double[]{Math.PI, 2 * Math.PI});

		circuito.componente( new Ponto(9, 18), new Ponto(9, 12), 1, 1, Color.RED, new Ponto(13, 16), "V1", 0); // Fonte
		circuito.trilha(new Ponto(9, 12), new Ponto(9, 6), new Ponto(24, 6), 0);
		circuito.seta(new Ponto(12, 6), new Ponto(14, 6), 0);
		circuito.componente(new Ponto(24, 6), new Ponto(30, 6), 2, 2, 0); // Diodo
		circuito.trilha(new Ponto(30, 6), new Ponto(39, 6), new Ponto(39, 12), 0);
		circuito.seta(new Ponto(35, 6), new Ponto(37, 6), 0);
		circuito.componente(new Ponto(39, 12), new Ponto(39, 18), 4, 3,0); // Carga
		circuito.trilha(new Ponto(39, 18), new Ponto(39, 24), new Ponto(12, 24), 0);
		circuito.trilha(new Ponto(12, 24), new Ponto(30, 24), 0);
		circuito.seta(new Ponto(24, 24), new Ponto(22, 24), 0);
		circuito.trilha(new Ponto(30, 24), new Ponto(9, 24), new Ponto(9, 18), 0);

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

