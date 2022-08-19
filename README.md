# SimuladorCircuito
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

[![](https://jitpack.io/v/salarolimatheus/EscolhaDinamica.svg)](https://github.com/salarolimatheus/SimuladorCircuito)

Uma maneira fácil de exibir curvas de ondas de um circuito eletrônico utilizando dois gráficos, um circuito e animações. O projeto ainda está em desenvolvimento, pois o objetivo é acrescentar vários componentes à biblioteca atual.

## 💡 Motivo
Ao decorrer do desenvolvimento de uma aplicativo educacional de eletrônica de potência, eu precisei de uma tela para apresentar curvas de onda de tensão e corrente de um circuito eletrônico. Com isso, a ideia é ter dois gráficos que apresentam duas variáveis e um circuito com seleção dinâmica para modificar os gráficos e outros.

Obs: O sistema possui uma certa flexibilidade com relação ao controle dos gráficos, sendo necessário indicar qual é a série a ser carregada e mostrada e qual o circuito a ser desenhado.

## 📱 Visualização

![tela-app](https://user-images.githubusercontent.com/34732144/185710857-4ef82893-b826-452b-aeb1-6fa6bb1f4175.gif)

## 🛠️ Como funciona


### XML
Para adicionar o objeto no XML, basta utilizar a seguinte estrutura:
```
    <sorocaba.peteca.com.simuladorcircuito.SimuladorCircuito
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:id="@+id/simulador"/>
```

### Java
Com relação ao código dinâmico, temos funções para configurar alguns valores e textos a serem impressos na tela e outros com relação às curvas.

Inicialização

``` java
public class MainActivity extends AppCompatActivity implements SimuladorCircuito.IntefaceSimulador {
	    SimuladorCircuito simulador;
	    ...
}
```
Iniciando as configurações no onCreate
``` java
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
```

Algumas funções devem ser implementadas para realizar o controle dos componentes:
``` java
@Override
public void componenteClickado(int componente) {
	if (componente == 1) {
	    simulador.addSerie(new Serie(valores, 250), 1);
	} else {
	    simulador.addSerie(new Serie(valoresY, 15), 1);
	}
}

@Override
protected void onPause() {
	simulador.stopAnimacao();
	animacao = false;
	super.onPause();
}
```

``` java
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
```
#### Funções
Lorem Ipsum

## 📁 Créditos

Lorem Ipsum
