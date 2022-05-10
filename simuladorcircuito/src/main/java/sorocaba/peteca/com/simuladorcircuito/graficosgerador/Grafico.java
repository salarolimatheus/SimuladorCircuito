package sorocaba.peteca.com.simuladorcircuito.graficosgerador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Grafico extends View {
    // Variaveis relacionados com o gráfico sem modificações
    private float alturaPontoX, alturaPontoY, alturaTotal, cursor, betaX;
    private float larguraPontoX, larguraPontoY, larguraTotal;

    private String[] periodosString = {"π/2", "π", "3π/2", "2π"};
    private int pontoDoCursor = 0;
    private double valorMaximo = 0;

    private Rect rect;

    private Path pathEixos, pathGrade, pathEscala, pathCursor;
    private Paint paintEixos, paintGrade, paintTextos, paintCursor;

    private Path pathPontoCurvaEscolhida, pathCurvaEscolhida, pathCurvaPrimaria, pathCurvaSecundaria, pathCurvasTerciaria, pathCurvasFundo;
    private Paint paintCurvaEscolhida, paintCurvaPrimaria, paintCurvaSecundaria, paintCurvasTerciaria, paintCurvasFundo;

    // Variaveis disponiveis para o usuario fazer modificações
    private String nomeEixoX = "", nomeEixoY = "";
    public int periodos = 4, periodosReais = 1;
    private boolean cursorStatus = false, gradeStatus = false, betaStatus = false, ondasSimultaneasStatus = false;
    private float tamanhoMarcacoes = 0.05f, tamanhoText = 1.5f, subtamanhoText = 1.3f;
    public Serie serieEscolhida, seriePrimaria, serieSecundaria, serieTerciaria;

    public Grafico(Context context) {
        super(context);
        init();
    }
    public Grafico(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        paintEixos = new Paint();
        paintEixos.setStrokeWidth(5);
        paintEixos.setStyle(Paint.Style.FILL_AND_STROKE);
        paintEixos.setColor(Color.BLACK);
        paintEixos.setAntiAlias(true);

        paintGrade = new Paint();
        paintGrade.setStrokeWidth(2);
        paintGrade.setStyle(Paint.Style.STROKE);
        paintGrade.setColor(Color.LTGRAY);
        paintGrade.setPathEffect(new DashPathEffect(new float[]{5, 10, 15, 20}, 0));
        paintGrade.setAntiAlias(true);

        paintTextos = new Paint();
        paintTextos.setTextAlign(Paint.Align.CENTER);
        paintTextos.setAntiAlias(true);

        paintCursor = new Paint();
        paintCursor.setStrokeWidth(3);
        paintCursor.setStyle(Paint.Style.STROKE);
        paintCursor.setColor(Color.DKGRAY);
        paintCursor.setAntiAlias(true);

        // Paint das Curvas
        paintCurvaEscolhida = new Paint();
        paintCurvaEscolhida.setStrokeWidth(5);
        paintCurvaEscolhida.setStyle(Paint.Style.STROKE);
        paintCurvaEscolhida.setColor(Color.BLUE);
        paintCurvaEscolhida.setAntiAlias(true);

        paintCurvaPrimaria = new Paint();
        paintCurvaPrimaria.setStrokeWidth(4);
        paintCurvaPrimaria.setStyle(Paint.Style.STROKE);
        paintCurvaPrimaria.setColor(Color.RED);
        paintCurvaPrimaria.setAlpha(125);
        paintCurvaPrimaria.setAntiAlias(true);

        paintCurvaSecundaria = new Paint();
        paintCurvaSecundaria.setStrokeWidth(4);
        paintCurvaSecundaria.setStyle(Paint.Style.STROKE);
        paintCurvaSecundaria.setColor(Color.GREEN);
        paintCurvaSecundaria.setAntiAlias(true);

        paintCurvasTerciaria = new Paint();
        paintCurvasTerciaria.setStrokeWidth(4);
        paintCurvasTerciaria.setStyle(Paint.Style.STROKE);
        paintCurvasTerciaria.setColor(Color.MAGENTA);
        paintCurvasTerciaria.setAlpha(125);
        paintCurvasTerciaria.setAntiAlias(true);

        paintCurvasFundo = new Paint();
        paintCurvasFundo.setStrokeWidth(4);
        paintCurvasFundo.setStyle(Paint.Style.STROKE);
        paintCurvasFundo.setColor(Color.LTGRAY);
        paintCurvasFundo.setPathEffect(new DashPathEffect(new float[]{30, 5}, 0));
        paintCurvasFundo.setAntiAlias(true);

        pathEixos = new Path();
        pathGrade = new Path();
        pathCursor = new Path();
        pathEscala = new Path();
        pathCurvaEscolhida = new Path();
        pathPontoCurvaEscolhida = new Path();
        pathCurvaPrimaria = new Path();
        pathCurvaSecundaria = new Path();
        pathCurvasTerciaria = new Path();
        pathCurvasFundo = new Path();
        rect = new Rect();
    }

    private void atualizaEscala() {
        pathEscala.reset();
        for (int i = 1; i <= periodos; i++) {
            pathEscala.moveTo((i * (larguraPontoX - larguraPontoY) / periodos) + larguraPontoY, alturaPontoX * (1 - tamanhoMarcacoes));
            pathEscala.lineTo((i * (larguraPontoX - larguraPontoY) / periodos) + larguraPontoY, alturaPontoX * (1 + tamanhoMarcacoes));
        }
        atualizaGrade();
    }
    private void atualizaTextosEixos(Canvas canvas) {
        paintTextos.setColor(Color.BLACK);
        paintTextos.setTextSize(tamanhoText * alturaPontoY);
        canvas.drawText(nomeEixoY, larguraPontoY / 2, alturaPontoY * (1 + tamanhoText / 2), paintTextos);
        canvas.drawText(nomeEixoX, (larguraPontoX + larguraTotal) / 2, alturaPontoX + tamanhoText / 4 * alturaPontoY, paintTextos);

        if (betaStatus)
            canvas.drawText("β", betaX, alturaPontoX * (1 - tamanhoMarcacoes), paintTextos);

        paintTextos.setColor(Color.BLACK);
        paintTextos.setTextSize(subtamanhoText * alturaPontoY);
        for (int i = 1; i <= periodos; i++)
            canvas.drawText(periodosString[i - 1], (i * (larguraPontoX - larguraPontoY) / periodos) + larguraPontoY, alturaPontoX * (1 + tamanhoMarcacoes) + subtamanhoText * alturaPontoY, paintTextos);
    }
    private void atualizaDados() {
        pathCurvaEscolhida.reset();
        pathCurvaPrimaria.reset();
        pathCurvaSecundaria.reset();
        pathCurvasTerciaria.reset();
        float valorMaximo = descobreValorMaximo();

        if (serieEscolhida != null) {
            betaX = (((larguraPontoX - larguraPontoY) * serieEscolhida.beta) / (serieEscolhida.tamanho * periodosReais) + larguraPontoY);
            for (int periodo = 0; periodo < (periodosReais); periodo++) {
                float esquerda = ((larguraPontoX - larguraPontoY) * periodo) / periodosReais + larguraPontoY;
                float direita = ((larguraPontoX - larguraPontoY) * (periodo + 1)) / periodosReais + larguraPontoY;
                pathCurvaEscolhida.moveTo(esquerda, (float) ((serieEscolhida.valor[0] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                for (int i = 1; i < serieEscolhida.tamanho; i++)
                    pathCurvaEscolhida.lineTo((i * (direita - esquerda) / serieEscolhida.tamanho) + esquerda, (float) ((serieEscolhida.valor[i] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                if (seriePrimaria != null) {
                    pathCurvaPrimaria.moveTo(esquerda, (float) ((seriePrimaria.valor[0] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                    for (int i = 1; i < seriePrimaria.tamanho; i++)
                        pathCurvaPrimaria.lineTo((i * (direita - esquerda) / seriePrimaria.tamanho) + esquerda, (float) ((seriePrimaria.valor[i] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                }
                if (serieSecundaria != null) {
                    pathCurvaSecundaria.moveTo(esquerda, (float) ((serieSecundaria.valor[0] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                    for (int i = 1; i < serieSecundaria.tamanho; i++)
                        pathCurvaSecundaria.lineTo((i * (direita - esquerda) / serieSecundaria.tamanho) + esquerda, (float) ((serieSecundaria.valor[i] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                }
                if (serieTerciaria != null) {
                    pathCurvasTerciaria.moveTo(esquerda, (float) ((serieTerciaria.valor[0] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                    for (int i = 1; i < serieTerciaria.tamanho; i++)
                        pathCurvasTerciaria.lineTo((i * (direita - esquerda) / serieTerciaria.tamanho) + esquerda, (float) ((serieTerciaria.valor[i] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
                }
            }
        }
        invalidate();
    }
    private float descobreValorMaximo() {
        if(serieEscolhida != null) {
            valorMaximo = Math.abs(serieEscolhida.valor[serieEscolhida.max]);
        }
        if(seriePrimaria != null) {
            if (Math.abs(seriePrimaria.valor[seriePrimaria.max]) > valorMaximo)
                valorMaximo = Math.abs(seriePrimaria.valor[seriePrimaria.max]);
        }
        if(serieSecundaria != null) {
            if (Math.abs(serieSecundaria.valor[serieSecundaria.max]) > valorMaximo)
                valorMaximo = Math.abs(serieSecundaria.valor[serieSecundaria.max]);
        }
        if(serieTerciaria != null) {
            if (Math.abs(serieTerciaria.valor[serieTerciaria.max]) > valorMaximo)
                valorMaximo = Math.abs(serieTerciaria.valor[serieTerciaria.max]);
        }
        return ((float) valorMaximo);
    }

    private void atualizaGrade() {
        pathGrade.reset();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                pathGrade.moveTo(larguraPontoY, (alturaTotal - 2 * alturaPontoY) * i / 4.0f + alturaPontoY);
                pathGrade.lineTo(larguraPontoX, (alturaTotal - 2 * alturaPontoY) * i / 4.0f + alturaPontoY);
            }
        }
        for (int i = 1; i <= (periodos * 2); i++) {
            pathGrade.moveTo((larguraPontoX - larguraPontoY) * i / (2 * periodos) + larguraPontoY, alturaPontoY);
            pathGrade.lineTo((larguraPontoX - larguraPontoY) * i / (2 * periodos) + larguraPontoY, alturaTotal - alturaPontoY);
        }
    }
    private void atualizaPontoDados() {
        if (serieEscolhida != null) {
            pathPontoCurvaEscolhida.reset();
            pegaPonto();
            pathPontoCurvaEscolhida.addCircle(cursor, (float) ((serieEscolhida.valor[(pontoDoCursor % serieEscolhida.tamanho)] / valorMaximo) * (alturaPontoY - alturaPontoX) + alturaPontoX), paintCurvaEscolhida.getStrokeWidth() * 1.5f, Path.Direction.CW);
        }
    }
    private void pegaPonto() {
        float Vm = (larguraPontoX - larguraPontoY) / periodosReais;
        float x = cursor - larguraPontoY;
        pontoDoCursor = (int) ((x * serieEscolhida.tamanho) / Vm);
    }
    public void changeCursor(MotionEvent event) {
        cursor = event.getX();
        changeCursor();
        invalidate();
    }
    private void changeCursor() {
        if (cursor < larguraPontoY) cursor = larguraPontoY;
        else if (cursor > larguraPontoX) cursor = larguraPontoX;
        pathCursor.reset();
        pathCursor.moveTo(cursor, 0);
        pathCursor.lineTo(cursor, alturaTotal);
        atualizaPontoDados();
    }

    public void addSerie(Serie serie) {
        this.serieEscolhida = serie;
        this.seriePrimaria = null;
        this.serieSecundaria = null;
        this.serieTerciaria = null;
        atualizaDados();
        atualizaPontoDados();
    }
    public void addSerie(Serie serie, Serie serieDois) {
        this.serieEscolhida = serie;
        this.seriePrimaria = serieDois;
        this.serieSecundaria = null;
        this.serieTerciaria = null;
        atualizaDados();
        atualizaPontoDados();
    }
    public void addSerie(Serie serie, Serie serieDois, Serie serieTres) {
        this.serieEscolhida = serie;
        this.seriePrimaria = serieDois;
        this.serieSecundaria = serieTres;
        this.serieTerciaria = null;
        atualizaDados();
        atualizaPontoDados();
    }
    public void addSerie(Serie serie, Serie serieDois, Serie serieTres, Serie serieQuatro) {
        this.serieEscolhida = serie;
        this.seriePrimaria = serieDois;
        this.serieSecundaria = serieTres;
        this.serieTerciaria = serieQuatro;
        atualizaDados();
        atualizaPontoDados();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintEixos.setColor(Color.WHITE);
        canvas.drawRect(rect, paintEixos);
        paintEixos.setColor(Color.BLACK);
        canvas.drawPath(pathEixos, paintEixos);
        if (gradeStatus)
            canvas.drawPath(pathGrade, paintGrade);
        if (ondasSimultaneasStatus) {
            if (seriePrimaria != null)
                canvas.drawPath(pathCurvaPrimaria, paintCurvaPrimaria);
            if (serieSecundaria != null)
                canvas.drawPath(pathCurvaSecundaria, paintCurvaSecundaria);
            if (serieTerciaria != null)
                canvas.drawPath(pathCurvasTerciaria, paintCurvasTerciaria);
            canvas.drawPath(pathCurvasFundo, paintCurvasFundo);
        }

        if (serieEscolhida != null)
            canvas.drawPath(pathCurvaEscolhida, paintCurvaEscolhida);

        canvas.drawPath(pathEscala, paintEixos);
        atualizaTextosEixos(canvas);
        if (cursorStatus) {
            canvas.drawPath(pathCursor, paintCursor);
            paintCurvaEscolhida.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(pathPontoCurvaEscolhida, paintCurvaEscolhida);
            paintCurvaEscolhida.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        alturaTotal = height;
        larguraTotal = width;
        alturaPontoY = 0.05f * alturaTotal;
        larguraPontoY = 0.04f * larguraTotal;
        alturaPontoX = 0.50f * alturaTotal;
        larguraPontoX = 0.94f * larguraTotal;
        cursor = larguraPontoY;

        pathEixos.reset();
        pathGrade.reset();

        pathEixos.moveTo(larguraPontoY, alturaPontoY);
        pathEixos.lineTo(larguraPontoY, alturaTotal - alturaPontoY);
        pathEixos.moveTo(larguraPontoY, alturaPontoX);
        pathEixos.lineTo(larguraPontoX, alturaPontoX);

        rect = new Rect(0, 0, width, height);
        atualizaEscala();
        atualizaGrade();
        changeCursor();
        atualizaDados();
        super.onSizeChanged(width, height, oldw, oldh);
    }

    //region getters et setters
    public float getCursor() {
        return cursor;
    }
    public void setCursor(float cursor) {
        this.cursor = cursor;
        changeCursor();
        invalidate();
    }
    public double pegaValorAtual () {
        return serieEscolhida.valor[pontoDoCursor % serieEscolhida.tamanho];
    }
    public double pegaAnguloAtual() {
        return (pontoDoCursor*2*Math.PI)/ serieEscolhida.tamanho;
    }

    public void setNomeEixos(String nomeEixoY, String nomeEixoX) {
        this.nomeEixoX = nomeEixoX;
        this.nomeEixoY = nomeEixoY;
    }

    public void setNumeroPeriodos(int periodos) {
        this.periodosReais = periodos;
        if (periodos != 3) {
            periodosString = new String[4];
            switch (periodos) {
                case 1:
                    periodosString[0] = "π/2";
                    periodosString[1] = "π";
                    periodosString[2] = "3π/2";
                    periodosString[3] = "2π";
                    break;
                case 2:
                    periodosString[0] = "π";
                    periodosString[1] = "2π";
                    periodosString[2] = "3π";
                    periodosString[3] = "4π";
                    break;
                case 4:
                    periodosString[0] = "2π";
                    periodosString[1] = "4π";
                    periodosString[2] = "6π";
                    periodosString[3] = "8π";
                    break;
            }
            this.periodos = 4;
        } else {
            periodosString = new String[6];
            periodosString[0] = "π";
            periodosString[1] = "2π";
            periodosString[2] = "3π";
            periodosString[3] = "4π";
            periodosString[4] = "5π";
            periodosString[5] = "6π";
            this.periodos = 6;
        }
        atualizaEscala();
        atualizaDados();
        atualizaPontoDados();
    }
    public void setCursorConfig(int cursorColor, int cursorWidth) {
        paintCursor.setColor(cursorColor);
        paintCursor.setStrokeWidth(cursorWidth);
    }
    public void setCursorStatus(boolean cursorStatus) {
        this.cursorStatus = cursorStatus;
    }
    public void setGradeStatus(boolean gradeStatus) {
        this.gradeStatus = gradeStatus;
    }
    public void setBetaStatus(boolean betaStatus) {
        this.betaStatus = betaStatus;
    }
    public void setOndasSimultaneasStatus(boolean ondasSimultaneasStatus) {
        this.ondasSimultaneasStatus = ondasSimultaneasStatus;
    }

    public void setEixosWidth(int espessura) {
        paintEixos.setStrokeWidth(espessura);

    }
    public void setEixosHeigthMarcacoes(float altura) {
        this.tamanhoMarcacoes = altura;
    }
    public void setEixosTextSize(float altura) {
        this.tamanhoText = altura;
    }
    public void setEixosSubTextSize(float altura) {
        this.subtamanhoText = altura;
    }

    public void setColorEscolhido(int color) {
        paintCurvaEscolhida.setColor(color);
    }
    public void setColorPrimario(int color) {
        paintCurvaPrimaria.setColor(color);
        paintCurvaPrimaria.setAlpha(125);

    }
    public void setColorSecundario(int color) {
        paintCurvaSecundaria.setColor(color);
        paintCurvaSecundaria.setAlpha(125);

    }
    public void setColorTerciario(int color) {
        paintCurvasTerciaria.setColor(color);
        paintCurvasTerciaria.setAlpha(125);

    }
    public void setCurvaWidth(int curvaWidth) {
        paintCurvaEscolhida.setStrokeWidth(curvaWidth);
        paintCurvaPrimaria.setStrokeWidth(curvaWidth * 0.8f);
        paintCurvaSecundaria.setStrokeWidth(curvaWidth * 0.8f);
        paintCurvasTerciaria.setStrokeWidth(curvaWidth * 0.8f);
        paintCurvasFundo.setStrokeWidth(curvaWidth * 0.8f);
    }
    //endregion

    public void removePathFundo() {
        pathCurvasFundo.reset();
    }
    public void addCurvaFundo(Serie serieFundo) {
        for (int periodo = 0; periodo < (periodosReais); periodo++) {
            float esquerda = ((larguraPontoX - larguraPontoY) * periodo) / periodosReais + larguraPontoY;
            float direita = ((larguraPontoX - larguraPontoY) * (periodo + 1)) / periodosReais + larguraPontoY;
            pathCurvasFundo.moveTo(esquerda, (float) ((serieFundo.valor[0] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
            for (int i = 1; i < serieFundo.tamanho; i++)
                pathCurvasFundo.lineTo((i * (direita - esquerda) / serieFundo.tamanho) + esquerda, (float) ((serieFundo.valor[i] / valorMaximo) * (alturaPontoY - alturaPontoX)) + alturaPontoX);
        }
    }
}
