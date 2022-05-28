package sorocaba.peteca.com.simuladorcircuito.circuitogerador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Circuito extends View {
    // Variaveis relacionados com o circuito sem modificações
    private final List<Path> pathCircuito = new ArrayList<>();
    private final List<Path> pathComponentes = new ArrayList<>();
    private final List<Ato> pathAtosAnimacao = new ArrayList<>();
    private final List<Componente> componenteList = new ArrayList<>();
    private final List<Texto> textoComponenteList = new ArrayList<>();
    private final List<Texto> textoList = new ArrayList<>();
    private Desenho libraryDesenhos;

    private Path pathGrade, pathSelecao;
    private Paint paintGrade, paintSelecao, paintDesenhoCircuito, paintTextos, paintAnimacao;
    private RectF rectFundo;

    private int numDivisoesDimPrincipal = 30, numDivisoesDimSecundaria = 0, ultimoSelecionado = -1;
    private boolean orientacaoVertical = false;

    InterfaceCircuito interfaceCircuito;

    // Variaveis disponiveis para o usuario fazer modificações
    private boolean statusGrade = false, componentesColoridosStatus = false;
    private int circuitoColor = Color.BLUE;
    private float textSize = 2f;
    private int distanciaPadrao;

    private boolean animaCircuito = false;
    private int numeroAtoAtual = 0;
    private double[] valoresPeriodosAtos;

    public void setCircuitoListener(InterfaceCircuito interfaceCircuito) {
        this.interfaceCircuito = interfaceCircuito;
    }

    public interface InterfaceCircuito {
        int carregaCircuito();
    }

    public Circuito(Context context) {
        super(context);
        init();
    }
    public Circuito(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        paintGrade = new Paint();
        paintGrade.setColor(Color.WHITE);
        paintGrade.setStyle(Paint.Style.FILL);

        paintSelecao = new Paint();
        paintSelecao.setStrokeWidth(4);
        paintSelecao.setStyle(Paint.Style.STROKE);
        paintSelecao.setColor(Color.DKGRAY);

        paintDesenhoCircuito = new Paint();
        paintDesenhoCircuito.setStrokeWidth(4);
        paintDesenhoCircuito.setColor(Color.BLUE);
        paintDesenhoCircuito.setStyle(Paint.Style.STROKE);

        paintTextos = new Paint();
        paintTextos.setColor(Color.BLUE);
        paintTextos.setTextAlign(Paint.Align.LEFT);

        paintAnimacao = new Paint();
        paintAnimacao.setStrokeWidth(4);
        paintAnimacao.setColor(Color.RED);
        paintAnimacao.setStyle(Paint.Style.STROKE);

        pathGrade = new Path();
        pathSelecao = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rectFundo, paintGrade);

        if(statusGrade) {
            paintGrade.setColor(Color.BLACK);
            canvas.drawPath(pathGrade, paintGrade);
            paintGrade.setColor(Color.WHITE);
        }

        for (int componente = 0; componente < pathComponentes.size(); componente++) {
            Path path = this.pathComponentes.get(componente);
            if (path != null) {
                int componenteColor = componenteList.get(componente).componenteColor;
                if(componentesColoridosStatus && componenteColor !=0) {
                    paintDesenhoCircuito.setColor(componenteColor);
                } else {
                    paintDesenhoCircuito.setColor(circuitoColor);
                }
                canvas.drawPath(path, paintDesenhoCircuito);
            }
        }

        paintDesenhoCircuito.setColor(circuitoColor);
        for (int elemento = 0; elemento < pathCircuito.size(); elemento++) {
            Path path = this.pathCircuito.get(elemento);
            if (path != null)
                canvas.drawPath(path, paintDesenhoCircuito);
        }

        if ((ultimoSelecionado > 0) && (pathSelecao != null))
            canvas.drawPath(pathSelecao, paintSelecao);

        for (int textoIndex = 0; textoIndex < textoComponenteList.size(); textoIndex++) {
            Texto texto = textoComponenteList.get(textoIndex);

            int componenteColor = componenteList.get(textoIndex).componenteColor;
            if(componentesColoridosStatus && componenteColor !=0) {
                paintTextos.setColor(componenteColor);
            } else {
                paintTextos.setColor(circuitoColor);
            }

            canvas.drawText(texto.getString(), texto.getX(), texto.getY(), paintTextos);
        }

        paintTextos.setColor(circuitoColor);
        for (int textoIndex = 0; textoIndex < textoList.size(); textoIndex++) {
            Texto texto = textoList.get(textoIndex);
            canvas.drawText(texto.getString(), texto.getX(), texto.getY(), paintTextos);
        }

        if(animaCircuito && valoresPeriodosAtos != null) {
            Path path = this.pathAtosAnimacao.get(numeroAtoAtual).pathAto;
            if (path != null)
                canvas.drawPath(path, paintAnimacao);
        }

        super.onDraw(canvas);
    }
    @Override
    protected void onSizeChanged(int larguraTotal, int alturaTotal, int oldw, int oldh) {
        pathGrade.reset();
        pathComponentes.clear();

        ultimoSelecionado = -1;
        componenteList.clear();
        textoList.clear();

        // DEFINE AS DIMENSOES DO CIRCUITO
        if (larguraTotal > alturaTotal) { // retangulo deitado
            orientacaoVertical = true;
            distanciaPadrao = alturaTotal/ numDivisoesDimPrincipal;
            numDivisoesDimSecundaria = larguraTotal / distanciaPadrao;
            for (int linha = 1; linha < numDivisoesDimPrincipal; linha++) {
                for (int coluna = 1; coluna < numDivisoesDimSecundaria; coluna++) {
                    pathGrade.addCircle(coluna * distanciaPadrao, linha * distanciaPadrao, 2, Path.Direction.CCW);
                }
            }
        } else { // retangulo em pé
            distanciaPadrao = larguraTotal / numDivisoesDimPrincipal;
            orientacaoVertical = false;
            numDivisoesDimSecundaria = alturaTotal / distanciaPadrao;
            for (int linha = 1; linha < numDivisoesDimSecundaria; linha++) {
                for (int coluna = 1; coluna < numDivisoesDimPrincipal; coluna++) {
                    pathGrade.addCircle(coluna * distanciaPadrao, linha * distanciaPadrao, 2, Path.Direction.CCW);
                }
            }
        }

        libraryDesenhos = new Desenho(distanciaPadrao);
        rectFundo = new RectF(0, 0, larguraTotal, alturaTotal);

        ultimoSelecionado = interfaceCircuito.carregaCircuito();
        paintTextos.setTextSize(textSize * distanciaPadrao);

        for (Componente componente : componenteList) {
            if (componente.numeroComponente == ultimoSelecionado) {
                DesenhaSelecao(componente, libraryDesenhos.verificarOrientacao(componente));
            }
        }

        super.onSizeChanged(larguraTotal, alturaTotal, oldw, oldh);
    }

    public void ToqueNaTela(MotionEvent event) {
        for (int elemento = 0; elemento < componenteList.size(); elemento++) {
            Orientacao orientacao = libraryDesenhos.verificarOrientacao(componenteList.get(elemento));
            Componente componente = componenteList.get(elemento);
            if (orientacao == Orientacao.CIMA) {
                if ((event.getX() >= componente.pontoUm.X - componente.dimensoes[0])   && (event.getX() <= componente.pontoUm.X + componente.dimensoes[0]) &&
                        (event.getY() >= componente.pontoDois.Y - componente.dimensoes[1]) && (event.getY() <= componente.pontoUm.Y + componente.dimensoes[1])) {
                    DesenhaSelecao(componente, orientacao);
                    ultimoSelecionado = componente.numeroComponente;
                }
            } else if (orientacao == Orientacao.BAIXO) {
                if ((event.getX() >= componente.pontoUm.X - componente.dimensoes[0]) && (event.getX() <= componente.pontoUm.X + componente.dimensoes[0]) &&
                        (event.getY() >= componente.pontoUm.Y - componente.dimensoes[1]) && (event.getY() <= componente.pontoDois.Y + componente.dimensoes[1])) {
                    DesenhaSelecao(componente, orientacao);
                    ultimoSelecionado = componente.numeroComponente;
                }
            } else if (orientacao == Orientacao.ESQUERDA) {
                if ((event.getX() <= componente.pontoDois.X - componente.dimensoes[0]) && (event.getX() >= componente.pontoUm.X + componente.dimensoes[0])  &&
                        (event.getY() >= componente.pontoUm.Y - componente.dimensoes[1])   && (event.getY() <= componente.pontoUm.Y + componente.dimensoes[1])) {
                    DesenhaSelecao(componente, orientacao);
                    ultimoSelecionado = componente.numeroComponente;
                }
            } else if (orientacao == Orientacao.DIREITA) {
                if ((event.getX() <= componente.pontoUm.X - componente.dimensoes[0]) && (event.getX() >= componente.pontoDois.X + componente.dimensoes[0]) &&
                        (event.getY() >= componente.pontoUm.Y - componente.dimensoes[1]) && (event.getY() <= componente.pontoUm.Y + componente.dimensoes[1])) {
                    DesenhaSelecao(componente, orientacao);
                    ultimoSelecionado = componente.numeroComponente;
                }
            }
        }
        invalidate();
    }
    private void DesenhaSelecao(Componente componente, Orientacao orientacao) {
        pathSelecao.reset();
        if (orientacao == Orientacao.CIMA)
            pathSelecao.addRect(new RectF(componente.pontoUm.X - componente.dimensoes[0], componente.pontoDois.Y - componente.dimensoes[1], componente.pontoUm.X + componente.dimensoes[0], componente.pontoUm.Y + componente.dimensoes[1]), Path.Direction.CCW);
        else if (orientacao == Orientacao.BAIXO)
            pathSelecao.addRect(new RectF(componente.pontoUm.X - componente.dimensoes[0], componente.pontoUm.Y - componente.dimensoes[1], componente.pontoUm.X + componente.dimensoes[0], componente.pontoDois.Y + componente.dimensoes[1]), Path.Direction.CCW);
        else if (orientacao == Orientacao.ESQUERDA)
            pathSelecao.addRect(new RectF(componente.pontoUm.X - componente.dimensoes[0], componente.pontoUm.Y - componente.dimensoes[1], componente.pontoDois.X + componente.dimensoes[0], componente.pontoUm.Y + componente.dimensoes[1]), Path.Direction.CCW);
        else if (orientacao == Orientacao.DIREITA)
            pathSelecao.addRect(new RectF(componente.pontoDois.X - componente.dimensoes[0], componente.pontoUm.Y -componente.dimensoes[1], componente.pontoUm.X + componente.dimensoes[0], componente.pontoUm.Y + componente.dimensoes[1]), Path.Direction.CCW);

    }

    private boolean isValid(Ponto ponto) {
        return (orientacaoVertical) ? (ponto.X > 0 && ponto.X < numDivisoesDimSecundaria) && (ponto.Y > 0 && ponto.Y < numDivisoesDimPrincipal) : (ponto.X > 0 && ponto.X < numDivisoesDimPrincipal) && (ponto.Y > 0 && ponto.Y < numDivisoesDimSecundaria);
    }
    public int componenteSelecionado() {
        return ultimoSelecionado;
    }

    public void trilha(Ponto pontoUm, Ponto pontoDois) {
        if (isValid(pontoUm) && isValid(pontoDois))
            pathCircuito.add(libraryDesenhos.Trilha(pontoUm, pontoDois));
    }
    public void trilha(Ponto pontoUm, Ponto pontoDois, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.Trilha(pontoUm, pontoDois);
            pathCircuito.add(path);
            if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                Ato ato = pathAtosAnimacao.get(numeroAto);
                ato.pathAto.addPath(path);
            }
        }
    }
    public void trilha(Ponto pontoUm, Ponto pontoDois, Ponto pontoTres) {
        if (isValid(pontoUm) && isValid(pontoDois) && isValid(pontoTres)) {
            pathCircuito.add(libraryDesenhos.Trilha(pontoUm, pontoDois, pontoTres));
        }
    }
    public void trilha(Ponto pontoUm, Ponto pontoDois, Ponto pontoTres, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois) && isValid(pontoTres)) {
            Path path = libraryDesenhos.Trilha(pontoUm, pontoDois, pontoTres);
            pathCircuito.add(path);
            if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                Ato ato = pathAtosAnimacao.get(numeroAto);
                ato.pathAto.addPath(path);
            }
        }
    }
    public void terra(Ponto pontoUm, Ponto pontoDois) {
        if (isValid(pontoUm) && isValid(pontoDois))
            pathCircuito.add(libraryDesenhos.Terra(pontoUm, pontoDois));
    }
    public void terra(Ponto pontoUm, Ponto pontoDois, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)){
            Path path = libraryDesenhos.Terra(pontoUm, pontoDois);
            pathCircuito.add(path);
            if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                Ato ato = pathAtosAnimacao.get(numeroAto);
                ato.pathAto.addPath(path);
            }
        }
    }
    public void bobina(Ponto pontoUm, Ponto pontoDois) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            pathCircuito.add(libraryDesenhos.Bobina(pontoUm, pontoDois));
        }
    }
    public void bobina(Ponto pontoUm, Ponto pontoDois, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.Bobina(pontoUm, pontoDois);
            pathCircuito.add(path);
            if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                Ato ato = pathAtosAnimacao.get(numeroAto);
                ato.pathAto.addPath(path);
            }
        }
    }

    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
            }
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
                if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                    Ato ato = pathAtosAnimacao.get(numeroAto);
                    ato.pathAto.addPath(path);
                }
            }
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, Ponto pontoTexto, String texto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
            }
            textoComponenteList.add(libraryDesenhos.addTexto(pontoTexto, texto));
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, Ponto pontoTexto, String texto, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
                textoComponenteList.add(libraryDesenhos.addTexto(pontoTexto, texto));
                if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                    Ato ato = pathAtosAnimacao.get(numeroAto);
                    ato.pathAto.addPath(path);
                }
            }
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, int componenteColor, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois, componenteColor);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
                if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                    Ato ato = pathAtosAnimacao.get(numeroAto);
                    ato.pathAto.addPath(path);
                }
            }
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, int componenteColor, Ponto pontoTexto, String texto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois, componenteColor);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
            }
            textoComponenteList.add(libraryDesenhos.addTexto(pontoTexto, texto));
        }
    }
    public void componente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente, int numeroComponente, int componenteColor, Ponto pontoTexto, String texto, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois)) {
            Path path = libraryDesenhos.addComponente(pontoUm, pontoDois, tipoDeComponente); // Ele atualiza os valores dos pontos
            if (path != null) {
                pathComponentes.add(path);
                Componente componente = new Componente(numeroComponente, path, pontoUm, pontoDois, componenteColor);
                componente.setDimensoes(libraryDesenhos.getDimensoes(pontoUm, pontoDois, tipoDeComponente));
                componenteList.add(componente);
                textoComponenteList.add(libraryDesenhos.addTexto(pontoTexto, texto));
                if (numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
                    Ato ato = pathAtosAnimacao.get(numeroAto);
                    ato.pathAto.addPath(path);
                }
            }
        }
    }

    public void texto(Ponto ponto, String texto) {
        if (isValid(ponto)) {
            textoList.add(libraryDesenhos.addTexto(ponto, texto));
        }
    }
    public void seta(Ponto pontoUm, Ponto pontoDois, int numeroAto) {
        if (isValid(pontoUm) && isValid(pontoDois) && numeroAto < pathAtosAnimacao.size() && numeroAto >= 0) {
            Ato ato = pathAtosAnimacao.get(numeroAto);
            ato.pathAto.addPath(libraryDesenhos.Seta(pontoUm, pontoDois));
        }
    }

    public void setCircuitoSelecaoColor(int circuitoSelecaoColor) {
        paintSelecao.setColor(circuitoSelecaoColor);

    }
    public void setCircuitoColor(int circuitoColor) {
        this.circuitoColor = circuitoColor;
    }
    public void setCircuitoWidth(int circuitoWidth) {
        paintDesenhoCircuito.setStrokeWidth(circuitoWidth);
    }
    public void setGradeStatus(boolean gradeStatus) {
        this.statusGrade = gradeStatus;
    }
    public void setNumeroDivisoesPrincipais(int numeroDivisoesPrincipais) {
        this.numDivisoesDimPrincipal = numeroDivisoesPrincipais;
    }
    public void setComponentesColoridosStatus(boolean componentesColoridosStatus) {
        this.componentesColoridosStatus = componentesColoridosStatus;
    }
    public void setCircuitoTextSize(float textSize) {
        this.textSize = textSize;
        paintTextos.setTextSize(textSize * distanciaPadrao);
    }

    public void setAnimacao(boolean animacao) {
        this.animaCircuito = animacao;
        invalidate();
    }
    public void setAnimacaoColor(int animacaoColor) {
        paintAnimacao.setColor(animacaoColor);
    }
    public void setAnimacaoConfig(double[] valoresAtos) {
        this.numeroAtoAtual = 0;
        this.valoresPeriodosAtos = valoresAtos;
        for (int numeroAto = 0; numeroAto < valoresAtos.length; numeroAto++)
            pathAtosAnimacao.add(new Ato(new Path(), numeroAto));
    }
    public void calcularAto(double valorOmegaTempo) {
        if (valoresPeriodosAtos != null) {
            valorOmegaTempo = valorOmegaTempo % (2*Math.PI);
            if (valorOmegaTempo >= 0 && valorOmegaTempo < valoresPeriodosAtos[0])
                numeroAtoAtual = 0;
            else {
                for (int index = 1; index < valoresPeriodosAtos.length; index++) {
                    if (valorOmegaTempo >= valoresPeriodosAtos[index-1] && valorOmegaTempo < valoresPeriodosAtos[index])
                        numeroAtoAtual = index;
                }
            }
            invalidate();
        }
    }
}