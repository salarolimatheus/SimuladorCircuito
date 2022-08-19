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
O simulador é dividido em três objetos: graficoUm, graficoDois e Circuito. Sendo que cada possui individualmente funções de configuração e modificações.

![image](https://user-images.githubusercontent.com/34732144/185712032-d1348881-bb7d-4c72-81d5-3e35d458cfc1.png)

### Implementação XML
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
Primeiramente instanciamos o simulador.

``` java
public class MainActivity extends AppCompatActivity implements SimuladorCircuito.IntefaceSimulador {
	    SimuladorCircuito simulador;
	    
	    @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			simulador = findViewById(R.id.simulador);
			simulador.setSimuladorListener(this);
			...
		}
		
	    ...
}
```
#### Configurando os gráficos e o circuito
Exemplo utilizando todas as configurações possíveis no onCreate (exemplo do projeto)
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
}
```
- Funções relacionadas com os gráficos

| Função                                                      	| Descrição                                                                                                  	|
|-------------------------------------------------------------	|------------------------------------------------------------------------------------------------------------	|
| setNomeEixosGraficoUm(String nomeEixoY, String nomeEixoX)   	| Modifica o nome dos eixos X e Y do graficoUm                                                               	|
| setNomeEixosGraficoDois(String nomeEixoY, String nomeEixoX) 	| Modifica o nome dos eixos X e Y do graficoDois                                                             	|
| setNumeroPeriodos(int periodos)                             	| Modifica o número de periodos a serem repetidos na tela  (não é necessário enviar uma série com repetição) 	|
| setCursorConfig(int cursorColor, int cursorWidth)           	| Modifica a cor e o a grossura do cursor                                                                    	|
| setCursorStatus(boolean status)                             	| Ativa ou desativa o cursor dos gráficos                                                                    	|
| setGradeStatus(boolean status)                              	| Ativa ou desativa as grades dos gráficos                                                                   	|
| setBetaStatus(boolean status)                               	| Mostra o ângulo Beta nos gráficos                                                                          	|
| setOndasSimultaneasGraficoUm (boolean status)               	| Ativa ou desativa múltiplas ondas do gráficoUm                                                             	|
| setOndasSimultaneasGraficoDois (boolean status)             	| Ativa ou desativa múltiplas ondas do gráficoDois                                                           	|
| setEixosWidth(int width)                                    	| Altera a grossura dos eixos (padrão: 5)                                                                    	|
| setEixosHeigthMarcacoes(float width)                        	| Altera o tamanho das marcações dos eixos de ambos os gráficos (padrão: 0.05f)                              	|
| setEixosTextSize(float width)                               	| Altera o tamanho dos textos dos eixos de ambos os  gráficos (padrão: 1.5f)                                 	|
| setEixosSubTextSize(float width)                            	| Altera o tamanho dos subtextos dos eixos de ambos os gráficos (padrão: 1.3f)                               	|
| setColorEscolhido(int color)                                	| Define a cor da curva escolhida do gráficoUm  (padrão: Color.BLUE)                                         	|
| setColorFonteUm(int color)                                  	| Define a cor da curva primária do gráficoUm  (padrão: Color.RED)                                           	|
| setColorFonteDois(int color)                                	| Define a cor da curva secundária do gráficoUm  (padrão: Color.GREEN)                                       	|
| setColorFonteTres(int color)                                	| Define a cor da curva terciária do gráficoUm  (padrão: Color.MAGENTA)                                      	|
| setColorCorrente(int color)                                 	| Defina a cor da curva padrão do gráficoDois  (padrão: Color.RED)                                           	|
| setCurvaWidth(int curvaWidth)                               	| Define a espessura da curva dos gráficos (padrão: 5)                                                       	|

| Função                                                                                  	| Descrição                                                                                                                    	|
|-----------------------------------------------------------------------------------------	|------------------------------------------------------------------------------------------------------------------------------	|
| addCurva(Serie serie, int grafico)                                                      	| Adiciona uma unica série para o gráfico escolhido (inteiro igual a 1 ou 2)                                                   	|
| addCurva(Serie serie, Serie serieDois, Serie serieTres, int grafico)                    	| Adiciona três séries para o gráfico escolhido (inteiro igual a 1 ou 2)                                                       	|
| addCurva(Serie serie, Serie serieDois, Serie serieTres, Serie serieQuatro, int grafico) 	| Adiciona quatro séries para o gráfico escolhido (inteiro igual a 1 ou 2)                                                     	|
| addCurvaFundo(Serie serie, int grafico)                                                 	| Adiciona uma curva de fundo à lista de curvas de fundo do gráfico escolhido,  esta função não remove as curvas já existentes 	|
| removeCurvasFundo(int grafico)                                                          	| Limpa todas as curvas existentes relacionadas ao gráfico escolhido                                                           	|
|                                                                                         	|                                                                                                                              	|


- Funções relacionadas com o circuito



#### Funções de Controle para fornecer a dinâmica dos componentes
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
