package sorocaba.peteca.com.simuladorcircuito;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import sorocaba.peteca.com.simuladorcircuito.circuitogerador.Circuito;
import sorocaba.peteca.com.simuladorcircuito.graficosgerador.Grafico;
import sorocaba.peteca.com.simuladorcircuito.graficosgerador.Serie;

public class SimuladorCircuito extends LinearLayout implements Circuito.InterfaceCircuito {
    private Grafico graficoUm, graficoDois;
    private Circuito circuito;
    private Resultados resultados;
    IntefaceSimulador intefaceSimulador;

    private CountDownTimer timer = null;
    private boolean animacao = false;
    private boolean animacaoPausada = false;
    private long tempoAnimacao = 6000;

    public void setSimuladorListener(IntefaceSimulador intefaceSimulador) {
        this.intefaceSimulador = intefaceSimulador;
    }

    public SimuladorCircuito(Context context) {
        super(context);
        init(context);
    }
    public SimuladorCircuito(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_principal, this, true);
        graficoUm = view.findViewById(R.id.graficoUm);
        graficoDois = view.findViewById(R.id.graficoDois);
        resultados = view.findViewById(R.id.resultados);
        circuito = view.findViewById(R.id.circuito);
        circuito.setCircuitoListener(this);

        graficoDois.setColorEscolhido(Color.RED);
        resultados.setColorTensao(Color.BLUE);
        resultados.setColorCorrente(Color.BLUE);
        resultados.setColorAngulo(Color.BLACK);

        graficoUm.setOnTouchListener((v, event) -> {
            graficoUm.changeCursor(event);
            graficoDois.setCursor(graficoUm.getCursor());
            resultados.atualizarDados(graficoUm.pegaValorAtual(), graficoDois.pegaValorAtual(), graficoUm.pegaAnguloAtual());
            circuito.calcularAto(graficoUm.pegaAnguloAtual());
            return true;
        });

        graficoDois.setOnTouchListener((v, event) -> {
            graficoDois.changeCursor(event);
            graficoUm.setCursor(graficoDois.getCursor());
            resultados.atualizarDados(graficoUm.pegaValorAtual(), graficoDois.pegaValorAtual(), graficoDois.pegaAnguloAtual());
            circuito.calcularAto(graficoUm.pegaAnguloAtual());
            return true;
        });

        circuito.setOnTouchListener((v, event) -> {
            circuito.ToqueNaTela(event);
            intefaceSimulador.componenteClickado(circuito.componenteSelecionado());
            resultados.atualizarDados(graficoUm.pegaValorAtual(), graficoDois.pegaValorAtual(), graficoUm.pegaAnguloAtual());
            return true;
        });
    }

    @Override
    public int carregaCircuito() {
        int componente = intefaceSimulador.carregaCircuito(circuito);
        intefaceSimulador.componenteClickado(componente);
        return componente;
    }

    public void setNomeEixosGraficoUm(String nomeEixoY, String nomeEixoX) {
        graficoUm.setNomeEixos(nomeEixoY, nomeEixoX);
    }
    public void setNomeEixosGraficoDois(String nomeEixoY, String nomeEixoX) {
        graficoDois.setNomeEixos(nomeEixoY, nomeEixoX);
    }

    public void setNumeroPeriodos(int periodos) {
        graficoUm.setNumeroPeriodos(periodos);
        graficoDois.setNumeroPeriodos(periodos);
        if(graficoUm.serieEscolhida != null && graficoDois.serieEscolhida != null) {
            resultados.atualizarDados(graficoUm.pegaValorAtual(), graficoDois.pegaValorAtual(), graficoDois.pegaAnguloAtual());
            circuito.calcularAto(graficoUm.pegaAnguloAtual());
        }
    }
    public void setCursorConfig(int cursorColor, int cursorWidth) {
        graficoUm.setCursorConfig(cursorColor, cursorWidth);
        graficoDois.setCursorConfig(cursorColor, cursorWidth);
    }
    public void setCursorStatus(boolean status) {
        graficoUm.setCursorStatus(status);
        graficoDois.setCursorStatus(status);
        resultados.setStatus(status);
    }
    public void setGradeStatus(boolean status) {
        graficoUm.setGradeStatus(status);
        graficoDois.setGradeStatus(status);
    }
    public void setBetaStatus(boolean status) {
        graficoUm.setBetaStatus(status);
        graficoDois.setBetaStatus(status);
    }

    public void setOndasSimultaneasGraficoUm (boolean status) {
        graficoUm.setOndasSimultaneasStatus(status);
        circuito.setComponentesColoridosStatus(status);
    }
    public void setOndasSimultaneasGraficoDois (boolean status) {
        graficoDois.setOndasSimultaneasStatus(status);
    }

    public void setEixosWidth(int width) {
        graficoUm.setEixosWidth(width);
        graficoDois.setEixosWidth(width);
    }
    public void setEixosHeigthMarcacoes(float width) {
        graficoUm.setEixosHeigthMarcacoes(width);
        graficoDois.setEixosHeigthMarcacoes(width);
    }
    public void setEixosTextSize(float width) {
        graficoUm.setEixosTextSize(width);
        graficoDois.setEixosTextSize(width);
    }
    public void setEixosSubTextSize(float width) {
        graficoUm.setEixosSubTextSize(width);
        graficoDois.setEixosSubTextSize(width);
    }

    public void setColorEscolhido(int color) {
        graficoUm.setColorEscolhido(color);
        resultados.setColorTensao(color);
    }
    public void setColorFonteUm(int color) {
        graficoUm.setColorPrimario(color);
    }
    public void setColorFonteDois(int color) {
        graficoUm.setColorSecundario(color);
    }
    public void setColorFonteTres(int color) {
        graficoUm.setColorTerciario(color);
    }
    public void setColorCorrente(int color) {
        graficoDois.setColorEscolhido(color);
        resultados.setColorCorrente(color);
    }
    public void setCurvaWidth(int curvaWidth) {
        graficoUm.setCurvaWidth(curvaWidth);
        graficoDois.setCurvaWidth(curvaWidth);
    }

    public void addCurva(Serie serie, int grafico) {
        if (grafico == 1) {
            graficoUm.addSerie(serie);
            resultados.setTensaoMaxima(serie.valor[serie.max]);
        } else if (grafico == 2) {
            graficoDois.addSerie(serie);
            resultados.setCorrenteMaxima(serie.valor[serie.max]);
        }
    }
    public void addCurva(Serie serie, Serie serieDois, int grafico) {
        if (grafico == 1) {
            graficoUm.addSerie(serie, serieDois);
        } else if (grafico == 2) {
            graficoDois.addSerie(serie, serieDois);
        }
    }
    public void addCurva(Serie serie, Serie serieDois, Serie serieTres, int grafico) {
        if (grafico == 1) {
            graficoUm.addSerie(serie, serieDois, serieTres);
        } else if (grafico == 2) {
            graficoDois.addSerie(serie, serieDois, serieTres);
        }
    }
    public void addCurva(Serie serie, Serie serieDois, Serie serieTres, Serie serieQuatro, int grafico) {
        if (grafico == 1) {
            graficoUm.addSerie(serie, serieDois, serieTres, serieQuatro);
        } else if (grafico == 2) {
            graficoDois.addSerie(serie, serieDois, serieTres, serieQuatro);
        }
    }
    public void atualizaGraficos() {
        graficoUm.atualizaDados();
        graficoDois.atualizaDados();
    }

    public void addCurvaFundo(Serie serie, int grafico) {
        if (grafico == 1) {
            graficoUm.addCurvaFundo(serie);
        } else if (grafico == 2) {
            graficoDois.addCurvaFundo(serie);
        }
    }
    public void removeCurvasFundo(int grafico) {
        if (grafico == 1) {
            graficoUm.removePathFundo();
        } else if (grafico == 2) {
            graficoDois.removePathFundo();
        }
    }

    public void setCircuitSelecaoColor(int circuitoSelecaoColor) {
        circuito.setCircuitoSelecaoColor(circuitoSelecaoColor);
    }
    public void setCircuitoColor(int circuitoColor) {
        circuito.setCircuitoColor(circuitoColor);
    }
    public void setCircuitoWidth(int width) {
        circuito.setCircuitoWidth(width);
    }
    public void setCircuitoGrade(boolean gradeStatus) {
        circuito.setGradeStatus(gradeStatus);
    }
    public void setCircuitoDimensao(int numeroDivisoesPrincipais) {
        circuito.setNumeroDivisoesPrincipais(numeroDivisoesPrincipais);
    }
    public void setCircuitoTextSize(float textSize) {
        circuito.setCircuitoTextSize(textSize);
    }

    public void setAnimacaoTime(long tempoAnimacao) {
        this.tempoAnimacao = tempoAnimacao;
    }
    public void setAnimacaoConfig(double[] atos) {
        circuito.setAnimacaoConfig(atos);
    }
    public void setCircuitoAnimacaoColor(int animacaoColor) {
        circuito.setAnimacaoColor(animacaoColor);
    }

    public void startAnimacao() {
        if (this.animacao)
            return;

        this.animacao = true;
        this.animacaoPausada = false;
        graficoUm.setAnimacao(true);
        graficoDois.setAnimacao(true);
        circuito.setAnimacao(true);

        criarTimerSimulacao(tempoAnimacao);
    }
    public void pauseResumeAnimacao() {
        if (!this.animacao)
            return;

        cancelTimer();
        if (this.animacaoPausada) {
            graficoUm.setAnimacao(true);
            graficoDois.setAnimacao(true);
            long tempoInicial = (long) (tempoAnimacao * (1 - ((graficoUm.getCursor()-graficoUm.getDimensaoX1Cursor())/graficoUm.getDimensaoX2Cursor())));
            criarTimerSimulacao(tempoInicial);
        } else {
            graficoUm.setAnimacao(false);
            graficoDois.setAnimacao(false);
        }
        animacaoPausada = !animacaoPausada;
    }
    public void stopAnimacao() {
        if (!this.animacao)
            return;

        this.animacao = false;
        cancelTimer();
        graficoUm.setAnimacao(false);
        graficoDois.setAnimacao(false);
        circuito.setAnimacao(false);
    }
    private void criarTimerSimulacao(long tempoInicial) {
        long tempoTick = tempoAnimacao/((long) graficoUm.getSerieTamanho() * graficoUm.getNumeroPeriodos());
        if (tempoTick < 15) { // Ajuste do tempoTick para atualização da tela e da capacidade do olho humano de percepção de movimento
            tempoTick = 15;
        }
        timer = new CountDownTimer(tempoInicial, tempoTick) {

            public void onTick(long millisUntilFinished) {
                float cursor = (graficoUm.getDimensaoX1Cursor() + (1 - millisUntilFinished / (float)tempoAnimacao) * graficoUm.getDimensaoX2Cursor());
                atualizaAnimacao(cursor);
            }

            public void onFinish() {
                if (timer != null && animacao)
                    criarTimerSimulacao(tempoAnimacao);
            }
        }.start();
    }

    private void atualizaAnimacao(float cursor) {
        graficoUm.setCursor(cursor);
        graficoDois.setCursor(cursor);
        resultados.atualizarDados(graficoUm.pegaValorAtual(), graficoDois.pegaValorAtual(), graficoUm.pegaAnguloAtual());
        circuito.calcularAto(graficoUm.pegaAnguloAtual());
        if(!animacao)
            timer.cancel();
    }
    public void cancelTimer() {
        //You need to call cTtimer.cancel() whenever the onDestroy()/onDestroyView() in the owning Activity/Fragment is called
        if(timer != null) {
            timer.cancel();
        }
    }

}