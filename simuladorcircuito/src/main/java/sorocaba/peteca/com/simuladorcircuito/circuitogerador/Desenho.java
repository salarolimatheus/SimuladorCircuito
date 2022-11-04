package sorocaba.peteca.com.simuladorcircuito.circuitogerador;

import android.graphics.Path;
import android.graphics.RectF;

class Desenho {
    private static final float distDiodo = 0.15f;
    private static final float tamanhoDiodo = 0.35f;
    private static final float distTiristor = 0.15f;
    private static final float tamanhoTiristor = 0.35f;
    private static final float distResistor = 0f;
    private static final float tamanhoResistor = 0.23f;
    private static final float distRL = 0.05f;
    private static final float tamanhoRL = 0.23f;
    private static final float distRE = 0.05f;
    private static final float tamanhoBateria = 0.35f;
    private static final float distE = 0.40f;
    private static final float distTerra = 0.75f;
    private static final float distRLE = 0.1f;
    private final int tamanhoMinimo;

    Desenho(int tamanhoMinimo) {
        this.tamanhoMinimo = tamanhoMinimo;
    }

    Path Trilha(Ponto pontoUm, Ponto pontoDois) {
        Path path = new Path();
        path.moveTo(pontoUm.X * tamanhoMinimo, pontoUm.Y * tamanhoMinimo);
        path.lineTo(pontoDois.X * tamanhoMinimo, pontoDois.Y * tamanhoMinimo);
        return path;
    }
    Path Trilha(Ponto pontoUm, Ponto pontoDois, Ponto pontoTres) {
        Path path = new Path();
        path.moveTo(pontoUm.X * tamanhoMinimo, pontoUm.Y * tamanhoMinimo);
        path.lineTo(pontoDois.X * tamanhoMinimo, pontoDois.Y * tamanhoMinimo);
        path.lineTo(pontoTres.X * tamanhoMinimo, pontoTres.Y * tamanhoMinimo);
        return path;
    }
    Path Terra(Ponto pontoUm, Ponto pontoDois) {
        Orientacao orientacao = verificarOrientacao(pontoUm, pontoDois);
        if (orientacao != Orientacao.ERRO) {
            pontoUm.X *= tamanhoMinimo;
            pontoUm.Y *= tamanhoMinimo;
            pontoDois.X *= tamanhoMinimo;
            pontoDois.Y *= tamanhoMinimo;
            Path path = new Path();
            if (orientacao == Orientacao.CIMA) {
                int distancia = Math.abs(pontoUm.Y - pontoDois.Y)/5;
                path.moveTo(pontoUm.X,pontoUm.Y);
                path.lineTo(pontoUm.X, pontoUm.Y - 2* distancia);
                path.lineTo(pontoUm.X + (0.5f + 2* distTerra) * distancia, pontoUm.Y - 2 * distancia);
                path.lineTo(pontoUm.X - (0.5f + 2* distTerra) * distancia, pontoUm.Y - 2 * distancia);
                path.moveTo(pontoUm.X, pontoUm.Y - 3*distancia);
                path.lineTo(pontoUm.X + (0.5f + distTerra) * distancia, pontoUm.Y - 3 *distancia);
                path.lineTo(pontoUm.X - (0.5f + distTerra) * distancia, pontoUm.Y - 3 *distancia);
                path.moveTo(pontoUm.X, pontoUm.Y - 4*distancia);
                path.lineTo(pontoUm.X + 0.5f * distancia, pontoUm.Y - 4 *distancia);
                path.lineTo(pontoUm.X - 0.5f * distancia, pontoUm.Y - 4 *distancia);
            } else if (orientacao == Orientacao.BAIXO) {
                int distancia = Math.abs(pontoUm.Y - pontoDois.Y)/5;
                path.moveTo(pontoUm.X,pontoUm.Y);
                path.lineTo(pontoUm.X, pontoUm.Y + 2* distancia);
                path.lineTo(pontoUm.X + (0.5f + 2* distTerra) * distancia, pontoUm.Y + 2 * distancia);
                path.lineTo(pontoUm.X - (0.5f + 2* distTerra) * distancia, pontoUm.Y + 2 * distancia);
                path.moveTo(pontoUm.X, pontoUm.Y + 3*distancia);
                path.lineTo(pontoUm.X + (0.5f + distTerra) * distancia, pontoUm.Y + 3 *distancia);
                path.lineTo(pontoUm.X - (0.5f + distTerra) * distancia, pontoUm.Y + 3 *distancia);
                path.moveTo(pontoUm.X, pontoUm.Y + 4*distancia);
                path.lineTo(pontoUm.X + 0.5f * distancia, pontoUm.Y + 4 *distancia);
                path.lineTo(pontoUm.X - 0.5f * distancia, pontoUm.Y + 4 *distancia);
            } else if (orientacao == Orientacao.ESQUERDA) {
                int distancia = Math.abs(pontoUm.X - pontoDois.X)/5;
                path.moveTo(pontoUm.X,pontoUm.Y);
                path.lineTo(pontoUm.X + 2 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X + 2* distancia, pontoUm.Y + (0.5f + 2* distTerra) * distancia);
                path.lineTo(pontoUm.X + 2* distancia, pontoUm.Y - (0.5f +2* distTerra) * distancia);

                path.moveTo(pontoUm.X + 3 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X + 3 * distancia, pontoUm.Y + (0.5f + distTerra) * distancia);
                path.lineTo(pontoUm.X + 3 * distancia, pontoUm.Y - (0.5f + distTerra) * distancia);
                path.moveTo(pontoUm.X + 4 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X + 4 * distancia, pontoUm.Y + 0.5f * distancia);
                path.lineTo(pontoUm.X + 4 * distancia, pontoUm.Y - 0.5f * distancia);
            } else if (orientacao == Orientacao.DIREITA) {
                int distancia = Math.abs(pontoUm.X - pontoDois.X)/5;
                path.moveTo(pontoUm.X,pontoUm.Y);
                path.lineTo(pontoUm.X - 2 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X - 2* distancia, pontoUm.Y + (0.5f + 2* distTerra) * distancia);
                path.lineTo(pontoUm.X - 2* distancia, pontoUm.Y - (0.5f +2* distTerra) * distancia);

                path.moveTo(pontoUm.X - 3 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X - 3 * distancia, pontoUm.Y + (0.5f + distTerra) * distancia);
                path.lineTo(pontoUm.X - 3 * distancia, pontoUm.Y - (0.5f + distTerra) * distancia);
                path.moveTo(pontoUm.X - 4 * distancia, pontoUm.Y);
                path.lineTo(pontoUm.X - 4 * distancia, pontoUm.Y + 0.5f * distancia);
                path.lineTo(pontoUm.X - 4 * distancia, pontoUm.Y - 0.5f * distancia);
            }
            return path;
        }
        return null;
    }

    Path Bobina(Ponto pontoUm, Ponto pontoDois) {
        Orientacao orientacao = verificarOrientacao(pontoUm, pontoDois);
        if (orientacao != Orientacao.ERRO) {
            pontoUm.X *= tamanhoMinimo;
            pontoUm.Y *= tamanhoMinimo;
            pontoDois.X *= tamanhoMinimo;
            pontoDois.Y *= tamanhoMinimo;
            Path path = new Path();
            if (orientacao == Orientacao.ESQUERDA) {
                int distanciaEsq = (pontoDois.X - pontoUm.X)/4;
                path.moveTo(pontoUm.X , pontoUm.Y);
                path.lineTo(pontoUm.X, pontoUm.Y + tamanhoMinimo);
                RectF rectF1 = new RectF(pontoUm.X, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoUm.X + distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF1, 180, 180);
                RectF rectF2 = new RectF(pontoUm.X + distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoUm.X + 2* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF2, 180, 180);
                RectF rectF3 = new RectF(pontoUm.X + 2*distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoUm.X + 3* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF3, 180, 180);
                RectF rectF4 = new RectF(pontoUm.X + 3*distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoUm.X + 4* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF4, 180, 180);
                path.moveTo(pontoUm.X + 4*distanciaEsq, pontoUm.Y);
                path.lineTo((pontoUm.X + 4*distanciaEsq), pontoUm.Y + tamanhoMinimo);
            } else if (orientacao == Orientacao.DIREITA) {
                int distanciaEsq = (pontoUm.X - pontoDois.X)/4;
                path.moveTo(pontoDois.X , pontoDois.Y);
                path.lineTo(pontoDois.X, pontoDois.Y - tamanhoMinimo);
                RectF rectF1 = new RectF(pontoDois.X, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoDois.X + distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF1, 0, 180);
                RectF rectF2 = new RectF(pontoDois.X + distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoDois.X + 2* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF2, 0, 180);
                RectF rectF3 = new RectF(pontoDois.X + 2*distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoDois.X + 3* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF3, 0, 180);
                RectF rectF4 = new RectF(pontoDois.X + 3*distanciaEsq, pontoUm.Y - 0.75f * distanciaEsq,
                        pontoDois.X + 4* distanciaEsq, pontoUm.Y + 0.75f * distanciaEsq);
                path.addArc(rectF4, 0, 180);
                path.moveTo(pontoDois.X + 4*distanciaEsq, pontoUm.Y);
                path.lineTo((pontoDois.X + 4*distanciaEsq), pontoUm.Y - tamanhoMinimo);
            }
            if (orientacao == Orientacao.CIMA) {
                int distanciaEsq = (pontoUm.Y - pontoDois.Y)/4;
                path.moveTo(pontoDois.X , pontoDois.Y);
                path.lineTo((pontoDois.X - tamanhoMinimo) , pontoDois.Y);
                RectF rectF1 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoDois.Y,
                        pontoUm.X + 0.75f * distanciaEsq, pontoDois.Y + distanciaEsq);
                path.addArc(rectF1, 90, -180);
                RectF rectF2 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoDois.Y + distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoDois.Y + 2*distanciaEsq);
                path.addArc(rectF2, 90, -180);
                RectF rectF3 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoDois.Y + 2*distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoDois.Y + 3* distanciaEsq);
                path.addArc(rectF3, 90, -180);
                RectF rectF4 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoDois.Y + 3*distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoDois.Y + 4*distanciaEsq);
                path.addArc(rectF4, 90, -180);
                path.moveTo(pontoUm.X, pontoDois.Y + 4*distanciaEsq);
                path.lineTo((pontoUm.X - tamanhoMinimo), pontoDois.Y + 4*distanciaEsq);
            } else if (orientacao == Orientacao.BAIXO) {
                int distanciaEsq = (pontoDois.Y - pontoUm.Y)/4;
                path.moveTo(pontoUm.X , pontoUm.Y);
                path.lineTo((pontoUm.X + tamanhoMinimo) , pontoUm.Y);
                RectF rectF1 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoUm.Y,
                        pontoUm.X + 0.75f * distanciaEsq, pontoUm.Y + distanciaEsq);
                path.addArc(rectF1, 90, 180);

                RectF rectF2 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoUm.Y + distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoUm.Y + 2*distanciaEsq);
                path.addArc(rectF2, 90, 180);

                RectF rectF3 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoUm.Y + 2*distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoUm.Y + 3* distanciaEsq);
                path.addArc(rectF3, 90, 180);

                RectF rectF4 = new RectF(pontoUm.X - 0.75f * distanciaEsq, pontoUm.Y + 3*distanciaEsq,
                        pontoUm.X + 0.75f * distanciaEsq, pontoUm.Y + 4*distanciaEsq);
                path.addArc(rectF4, 90, 180);
                path.moveTo(pontoUm.X, pontoUm.Y + 4*distanciaEsq);
                path.lineTo((pontoUm.X + tamanhoMinimo), pontoUm.Y + 4*distanciaEsq);
            }
            return path;
        }
        return null;
    }

    Path addComponente(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente) {
        Orientacao orientacao = verificarOrientacao(pontoUm, pontoDois);
        if (orientacao != Orientacao.ERRO) {
            pontoUm.X *= tamanhoMinimo;
            pontoUm.Y *= tamanhoMinimo;
            pontoDois.X *= tamanhoMinimo;
            pontoDois.Y *= tamanhoMinimo;
            switch (tipoDeComponente) {
                case 1:
                    return fonte(pontoUm, pontoDois, orientacao);
                case 2:
                    return diodo(pontoUm, pontoDois, orientacao);
                case 3:
                    return tiristor(pontoUm, pontoDois, orientacao);
                case 4:
                    return cargaR(pontoUm, pontoDois, orientacao);
                case 5:
                    return cargaRL(pontoUm, pontoDois, orientacao);
                case 6:
                    return cargaRLE(pontoUm, pontoDois, orientacao);
                case 7:
                    return cargaRE(pontoUm, pontoDois, orientacao);
            }
        }
        return null;
    }
    Texto addTexto(Ponto ponto, String textoString) {
        return new Texto(ponto.X * tamanhoMinimo, ponto.Y * tamanhoMinimo, textoString);
    }
    Path Seta(Ponto pontoUm, Ponto pontoDois) {
        Orientacao orientacao = verificarOrientacao(pontoUm, pontoDois);
        Path path = new Path();
        if (orientacao == Orientacao.CIMA || orientacao == Orientacao.BAIXO) {
            path.moveTo((pontoUm.X + 1) * tamanhoMinimo, pontoUm.Y * tamanhoMinimo);
            path.lineTo(pontoDois.X * tamanhoMinimo, pontoDois.Y * tamanhoMinimo);
            path.lineTo((pontoUm.X - 1) * tamanhoMinimo, pontoUm.Y * tamanhoMinimo);
            path.close();
        } else if (orientacao == Orientacao.ESQUERDA || orientacao == Orientacao.DIREITA) {
            path.moveTo(pontoUm.X  * tamanhoMinimo, (pontoUm.Y+ 1) * tamanhoMinimo);
            path.lineTo(pontoDois.X * tamanhoMinimo, pontoDois.Y * tamanhoMinimo);
            path.lineTo(pontoUm.X * tamanhoMinimo, (pontoUm.Y - 1) * tamanhoMinimo);
            path.close();
        }
        return path;
    }

    public Orientacao verificarOrientacao(Componente componente) {
        Ponto pontoUm = componente.pontoUm, pontoDois = componente.pontoDois;
        return verificarOrientacao(pontoUm, pontoDois);
    }
    private Orientacao verificarOrientacao(Ponto pontoUm, Ponto pontoDois) {
        int X = (pontoUm.X - pontoDois.X), Y = (pontoUm.Y - pontoDois.Y);
        if ((X == 0) && (Y != 0)) {
            return (Y > 0) ? Orientacao.CIMA : Orientacao.BAIXO;
        } else if ((Y == 0) && (X != 0)) {
            return (X > 0) ? Orientacao.DIREITA : Orientacao.ESQUERDA;
        }
        return Orientacao.ERRO;
    }
    public float[] getDimensoes(Ponto pontoUm, Ponto pontoDois, int tipoDeComponente) {
        Orientacao orientacao = verificarOrientacao(pontoUm, pontoDois);
        float distanciaSelecao;

        if ((orientacao == Orientacao.CIMA) || (orientacao == Orientacao.BAIXO)) {
            distanciaSelecao = Math.abs(pontoDois.Y - pontoUm.Y);
            switch (tipoDeComponente) {
                case 1: // fonte
                    return (new float[]{0.6f * distanciaSelecao, 0.1f * distanciaSelecao});
                case 2: // diodo
                    return (new float[]{0.55f * distanciaSelecao, 0.1f * distanciaSelecao});
                case 3: // tiristor
                    return (new float[]{0.6f * distanciaSelecao, 0.1f * distanciaSelecao});
                case 4: // carga R
                    return (new float[]{0.45f * distanciaSelecao, 0.15f * distanciaSelecao});
                case 5: // carga RL
                    return (new float[]{0.25f * distanciaSelecao, 0.1f * distanciaSelecao});
                case 6: // carga RLE
                    return (new float[]{0.25f * distanciaSelecao, 0.1f * distanciaSelecao});
                case 7: // carga RE
                    return (new float[]{0.25f * distanciaSelecao, 0.1f * distanciaSelecao});
            }
        }
        else if ((orientacao == Orientacao.ESQUERDA) || (orientacao == Orientacao.DIREITA)) {
            distanciaSelecao = Math.abs(pontoDois.X - pontoUm.X);
            switch (tipoDeComponente) {
                case 1: // fonte
                    return (new float[]{0.1f * distanciaSelecao, 0.6f * distanciaSelecao});
                case 2: // diodo
                    return (new float[]{0.1f * distanciaSelecao, 0.55f * distanciaSelecao});
                case 3: // tiristor
                    return (new float[]{0.1f * distanciaSelecao, 0.6f * distanciaSelecao});
                case 4: // carga R
                    return (new float[]{0.15f * distanciaSelecao, 0.45f * distanciaSelecao});
                case 5: // carga RL
                    return (new float[]{0.1f * distanciaSelecao, 0.25f * distanciaSelecao});
                case 6: // carga RLE
                    return (new float[]{0.1f * distanciaSelecao, 0.25f * distanciaSelecao});
                case 7: // carga RE
                    return (new float[]{0.1f * distanciaSelecao, 0.25f * distanciaSelecao});
            }
        }
        return (new float[]{0, 0});
    }

    private Path fonte(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA || orientacao == Orientacao.BAIXO) {
            int centroX = pontoUm.X, centroY = (pontoUm.Y + pontoDois.Y) / 2, raio = Math.abs((pontoDois.Y - pontoUm.Y) / 2);
            path.addCircle(centroX, centroY, raio, Path.Direction.CCW);
            path.addArc(new RectF(-0.7f * raio + centroX, -0.5f * raio + centroY, centroX, +0.5f * raio + centroY), 180, 180);
            path.addArc(new RectF(centroX, -0.5f * raio + centroY, +0.7f * raio + centroX, +0.5f * raio + centroY), 180, -180);
        } else if (orientacao == Orientacao.ESQUERDA || orientacao == Orientacao.DIREITA) {
            int centroX = (pontoUm.X + pontoDois.X) / 2, centroY = pontoUm.Y, raio = Math.abs((pontoDois.X - pontoUm.X) / 2);
            path.addCircle(centroX, centroY, raio, Path.Direction.CCW);
            path.addArc(new RectF(-0.5f * raio + centroX, centroY, 0.5f * raio + centroX, +0.7f * raio + centroY), 270, -180);
            path.addArc(new RectF(-0.5f * raio + centroX, -0.7f * raio + centroY, 0.5f * raio + centroX, centroY), 270, 180);
        }
        return path;
    }
    private Path diodo(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y + distDiodo * distancia);
            path.lineTo(pontoUm.X + tamanhoDiodo * distancia, pontoUm.Y - distDiodo * distancia);
            path.lineTo(pontoUm.X - tamanhoDiodo * distancia, pontoUm.Y - distDiodo * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y + distDiodo * distancia);
            path.lineTo(pontoUm.X + tamanhoDiodo * distancia, pontoDois.Y + distDiodo * distancia);
            path.lineTo(pontoUm.X - tamanhoDiodo * distancia, pontoDois.Y + distDiodo * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y - distDiodo * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y);

        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y - distDiodo * distancia);
            path.lineTo(pontoUm.X - tamanhoDiodo * distancia, pontoUm.Y + distDiodo * distancia);
            path.lineTo(pontoUm.X + tamanhoDiodo * distancia, pontoUm.Y + distDiodo * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y - distDiodo * distancia);
            path.lineTo(pontoUm.X - tamanhoDiodo * distancia, pontoDois.Y - distDiodo * distancia);
            path.lineTo(pontoUm.X + tamanhoDiodo * distancia, pontoDois.Y - distDiodo * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y + distDiodo * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y);
        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X - distDiodo * distancia, pontoDois.Y);
            path.lineTo(pontoUm.X + distDiodo * distancia, pontoUm.Y + tamanhoDiodo * distancia);
            path.lineTo(pontoUm.X + distDiodo * distancia, pontoUm.Y - tamanhoDiodo * distancia);
            path.lineTo(pontoDois.X - distDiodo * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X - distDiodo * distancia, pontoDois.Y + tamanhoDiodo * distancia);
            path.lineTo(pontoDois.X - distDiodo * distancia, pontoDois.Y - tamanhoDiodo * distancia);
            path.moveTo(pontoUm.X + distDiodo * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y);
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X + distDiodo * distancia, pontoDois.Y);
            path.lineTo(pontoUm.X - distDiodo * distancia, pontoUm.Y - tamanhoDiodo * distancia);
            path.lineTo(pontoUm.X - distDiodo * distancia, pontoUm.Y + tamanhoDiodo * distancia);
            path.lineTo(pontoDois.X + distDiodo * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X + distDiodo * distancia, pontoDois.Y - tamanhoDiodo * distancia);
            path.lineTo(pontoDois.X + distDiodo * distancia, pontoDois.Y + tamanhoDiodo * distancia);
            path.moveTo(pontoUm.X - distDiodo * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y);
        }
        return path;
    }
    private Path tiristor(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y + distTiristor * distancia);
            path.lineTo(pontoUm.X + tamanhoTiristor * distancia, pontoUm.Y - distTiristor * distancia);
            path.lineTo(pontoUm.X - tamanhoTiristor * distancia, pontoUm.Y - distTiristor * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y + distTiristor * distancia);
            path.lineTo(pontoDois.X - tamanhoTiristor / 2 * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X - (tamanhoTiristor + distTiristor) * distancia, pontoDois.Y);
            path.moveTo(pontoDois.X + tamanhoTiristor * distancia, pontoDois.Y + distTiristor * distancia);
            path.lineTo(pontoDois.X - tamanhoTiristor * distancia, pontoDois.Y + distTiristor * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y - distTiristor * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y);
        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y - distTiristor * distancia);
            path.lineTo(pontoUm.X - tamanhoTiristor * distancia, pontoUm.Y + distTiristor * distancia);
            path.lineTo(pontoUm.X + tamanhoTiristor * distancia, pontoUm.Y + distTiristor * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y - distTiristor * distancia);
            path.lineTo(pontoDois.X + tamanhoTiristor / 2 * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X + (tamanhoTiristor + distTiristor) * distancia, pontoDois.Y);
            path.moveTo(pontoDois.X - tamanhoTiristor * distancia, pontoDois.Y - distTiristor * distancia);
            path.lineTo(pontoDois.X + tamanhoTiristor * distancia, pontoDois.Y - distTiristor * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y + distTiristor * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y);
        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X - distTiristor * distancia, pontoDois.Y);
            path.lineTo(pontoUm.X + distTiristor * distancia, pontoUm.Y - tamanhoTiristor * distancia);
            path.lineTo(pontoUm.X + distTiristor * distancia, pontoUm.Y + tamanhoTiristor * distancia);
            path.lineTo(pontoDois.X - distTiristor * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y - tamanhoTiristor / 2 * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y - (tamanhoTiristor + distTiristor) * distancia);
            path.moveTo(pontoDois.X - distTiristor * distancia, pontoDois.Y - tamanhoTiristor * distancia);
            path.lineTo(pontoDois.X - distTiristor * distancia, pontoDois.Y + tamanhoTiristor * distancia);
            path.moveTo(pontoUm.X + distTiristor * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y);
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            path.moveTo(pontoDois.X, pontoDois.Y);
            path.lineTo(pontoDois.X + distTiristor * distancia, pontoDois.Y);
            path.lineTo(pontoUm.X - distTiristor * distancia, pontoUm.Y + tamanhoTiristor * distancia);
            path.lineTo(pontoUm.X - distTiristor * distancia, pontoUm.Y - tamanhoTiristor * distancia);
            path.lineTo(pontoDois.X + distTiristor * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y + tamanhoTiristor / 2 * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y + (tamanhoTiristor + distTiristor) * distancia);
            path.moveTo(pontoDois.X + distTiristor * distancia, pontoDois.Y + tamanhoTiristor * distancia);
            path.lineTo(pontoDois.X + distTiristor * distancia, pontoDois.Y - tamanhoTiristor * distancia);
            path.moveTo(pontoUm.X - distTiristor * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y);
        }
        return path;
    }
    private Path cargaR(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            float passo = (1 - 2 * distResistor) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y - distResistor * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - (distResistor + 1 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - (distResistor + 3 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - (distResistor + 5 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - (distResistor + 7 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - (distResistor + 9 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - (distResistor + 11 * passo) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y + distResistor * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y);
            float passo = (1 - 2 * distResistor) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X, pontoUm.Y + distResistor * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + (distResistor + 1 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + (distResistor + 3 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + (distResistor + 5 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + (distResistor + 7 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + (distResistor + 9 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + (distResistor + 11 * passo) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y - distResistor * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            float passo = (1 - 2 * distResistor) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + distResistor * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X + (distResistor + 1 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (distResistor + 3 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (distResistor + 5 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (distResistor + 7 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (distResistor + 9 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (distResistor + 11 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoDois.X - distResistor * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X);
            float passo = (1 - 2 * distResistor) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - distResistor * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X - (distResistor + 1 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (distResistor + 3 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (distResistor + 5 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (distResistor + 7 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (distResistor + 9 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (distResistor + 11 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoDois.X + distResistor * distancia, pontoDois.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        }
        return path;
    }
    private Path cargaRL(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/2;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - (1 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - (3 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y - (12 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y - distancia);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X - tamanhoRL* 1.5f * distancia, pontoUm.Y - distancia - (i+1) * distancia/4f,
                        pontoUm.X + tamanhoRL * 1.5f * distancia, pontoUm.Y - distancia - i * distancia/4f), 90, +180);
            }
        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/2;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + (1 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + (3 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y + (12 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y + distancia);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X - tamanhoRL* 1.5f * distancia, pontoUm.Y + distancia + i * distancia/4f,
                        pontoUm.X + tamanhoRL * 1.5f * distancia, pontoUm.Y + distancia + (i+1) * distancia/4f), 90, -180);
            }
        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/2;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + (1 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (3 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (5 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (7 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (9 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (11 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X+ (12 * passo) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X + distancia, pontoUm.Y);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X + distancia + i * distancia/4f, pontoUm.Y - tamanhoRL* 1.5f * distancia,
                        pontoUm.X  + distancia + (i+1) * distancia/4f, pontoUm.Y + tamanhoRL * 1.5f * distancia), 180, 180);
            }
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/2;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - (1 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (3 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (5 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (7 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (9 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (11 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (12 * passo) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X - distancia, pontoUm.Y);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X  - distancia - (i+1) * distancia/4f, pontoUm.Y - tamanhoRL* 1.5f * distancia,
                        pontoUm.X - distancia - i * distancia/4f, pontoUm.Y + tamanhoRL * 1.5f * distancia), 180, -180);
            }
        }
        return path;
    }
    private Path cargaRLE(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/3;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - (1 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - (3 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y - ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y - ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y - (12 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y - distancia);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X - tamanhoRL* 1.5f * distancia, pontoUm.Y - distancia - (i+1) * distancia/4.0f,
                        pontoUm.X + tamanhoRL * 1.5f * distancia, pontoUm.Y - distancia - i * distancia/4.0f), 90, +180);
            }
            path.moveTo(pontoUm.X, pontoUm.Y - 2 * distancia);
            path.lineTo(pontoUm.X , pontoUm.Y - (2 + distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria * distancia, pontoUm.Y - (2 + distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria * distancia, pontoUm.Y - (2 + distE) * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y - (3 - distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria/2 * distancia, pontoUm.Y - (3 - distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria/2 * distancia, pontoUm.Y - (3 - distE) * distancia);
            path.moveTo(pontoUm.X , pontoUm.Y - (3 - distE) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/3;
            float passo = (1 - distRLE) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + (1 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + (3 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoRL * distancia, pontoUm.Y + ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoRL * distancia, pontoUm.Y + ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y + (12 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y + distancia);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X - tamanhoRL* 1.5f * distancia, pontoUm.Y + distancia + i * distancia/4f,
                        pontoUm.X + tamanhoRL * 1.5f * distancia, pontoUm.Y + distancia + (i+1) * distancia/4f), 90, -180);
            }
            path.moveTo(pontoUm.X, pontoUm.Y + 2 * distancia);
            path.lineTo(pontoUm.X , pontoUm.Y + (2 + distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria * distancia, pontoUm.Y + (2 + distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria * distancia, pontoUm.Y + (2 + distE) * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y + (3 - distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria/2 * distancia, pontoUm.Y + (3 - distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria/2 * distancia, pontoUm.Y + (3 - distE) * distancia);
            path.moveTo(pontoUm.X , pontoUm.Y + (3 - distE) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);

        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/3;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + (1 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (3 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (5 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (7 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (9 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X + (11 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X+ (12 * passo) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X + distancia, pontoUm.Y);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X + distancia + i * distancia/4f, pontoUm.Y - tamanhoRL* 1.5f * distancia,
                        pontoUm.X  + distancia + (i+1) * distancia/4f, pontoUm.Y + tamanhoRL * 1.5f * distancia), 180, 180);
            }
            path.moveTo(pontoUm.X + 2 * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X + (2 + distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  + (2 + distE) * distancia, pontoUm.Y + tamanhoBateria * distancia);
            path.lineTo(pontoUm.X  + (2 + distE) * distancia, pontoUm.Y - tamanhoBateria * distancia);
            path.moveTo(pontoUm.X + (3 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  + (3 - distE) * distancia, pontoUm.Y + tamanhoBateria/2 * distancia);
            path.lineTo(pontoUm.X  + (3 - distE) * distancia, pontoUm.Y - tamanhoBateria/2 * distancia);
            path.moveTo(pontoUm.X + (3 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/3;
            float passo = (1 - distRL) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - (1 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (3 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (5 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (7 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (9 * passo) * distancia, pontoUm.Y + tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (11 * passo) * distancia, pontoUm.Y - tamanhoRL * distancia);
            path.lineTo(pontoUm.X - (12 * passo) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X - distancia, pontoUm.Y);
            for (int i = 0; i<4; i++) {
                path.addArc(new RectF(pontoUm.X  - distancia - (i+1) * distancia/4f, pontoUm.Y - tamanhoRL* 1.5f * distancia,
                        pontoUm.X - distancia - i * distancia/4f, pontoUm.Y + tamanhoRL * 1.5f * distancia), 180, -180);
            }
            path.moveTo(pontoUm.X - 2 * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X - (2 + distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  - (2 + distE) * distancia, pontoDois.Y + tamanhoBateria * distancia);
            path.lineTo(pontoUm.X  - (2 + distE) * distancia, pontoDois.Y - tamanhoBateria * distancia);
            path.moveTo(pontoUm.X - (3 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  - (3 - distE) * distancia, pontoDois.Y + tamanhoBateria/2 * distancia);
            path.lineTo(pontoUm.X  - (3 - distE) * distancia, pontoDois.Y - tamanhoBateria/2 * distancia);
            path.moveTo(pontoUm.X - (3 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        }
        return path;
    }
    private Path cargaRE(Ponto pontoUm, Ponto pontoDois, Orientacao orientacao) {
        Path path = new Path();
        if (orientacao == Orientacao.CIMA) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/2;
            float passo = (1 - distRE) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - (1 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - (3 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y - ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y - ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y - (12 * passo) * distancia);
            path.lineTo(pontoUm.X , pontoUm.Y - (1 + distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria * distancia, pontoUm.Y - (1 + distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria * distancia, pontoUm.Y - (1 + distE) * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y - (2 - distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria/2 * distancia, pontoUm.Y - (2 - distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria/2 * distancia, pontoUm.Y - (2 - distE) * distancia);
            path.moveTo(pontoUm.X , pontoUm.Y - (2 - distE) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.BAIXO) {
            int distancia = Math.abs(pontoDois.Y - pontoUm.Y)/2;
            float passo = (1 - distRE) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + (1 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + (3 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + ( 5 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + ( 7 * passo) * distancia);
            path.lineTo(pontoUm.X + tamanhoResistor * distancia, pontoUm.Y + ( 9 * passo) * distancia);
            path.lineTo(pontoUm.X - tamanhoResistor * distancia, pontoUm.Y + ( 11 * passo) * distancia);
            path.lineTo(pontoUm.X, pontoUm.Y + (12 * passo) * distancia);
            path.lineTo(pontoUm.X , pontoUm.Y + (1 + distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria * distancia, pontoUm.Y + (1 + distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria * distancia, pontoUm.Y + (1 + distE) * distancia);
            path.moveTo(pontoUm.X, pontoUm.Y + (2 - distE) * distancia);
            path.lineTo(pontoUm.X  + tamanhoBateria/2 * distancia, pontoUm.Y + (2 - distE) * distancia);
            path.lineTo(pontoUm.X  - tamanhoBateria/2 * distancia, pontoUm.Y + (2 - distE) * distancia);
            path.moveTo(pontoUm.X , pontoUm.Y + (2 - distE) * distancia);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.ESQUERDA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/2;
            float passo = (1 - distRE) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X + (1 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (3 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (5 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (7 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (9 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X + (11 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X+ (12 * passo) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X + (1 + distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  + (1 + distE) * distancia, pontoUm.Y + tamanhoBateria * distancia);
            path.lineTo(pontoUm.X  + (1 + distE) * distancia, pontoUm.Y - tamanhoBateria * distancia);
            path.moveTo(pontoUm.X + (2 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  + (2 - distE) * distancia, pontoUm.Y + tamanhoBateria/2 * distancia);
            path.lineTo(pontoUm.X  + (2 - distE) * distancia, pontoUm.Y - tamanhoBateria/2 * distancia);
            path.moveTo(pontoUm.X + (2 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        } else if (orientacao == Orientacao.DIREITA) {
            int distancia = Math.abs(pontoDois.X - pontoUm.X)/2;
            float passo = (1 - distRE) / 12;
            path.moveTo(pontoUm.X, pontoUm.Y);
            path.lineTo(pontoUm.X - (1 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (3 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (5 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (7 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (9 * passo) * distancia, pontoUm.Y + tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (11 * passo) * distancia, pontoUm.Y - tamanhoResistor * distancia);
            path.lineTo(pontoUm.X - (12 * passo) * distancia, pontoUm.Y);

            path.lineTo(pontoUm.X - (1 + distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  - (1 + distE) * distancia, pontoDois.Y + tamanhoBateria * distancia);
            path.lineTo(pontoUm.X  - (1 + distE) * distancia, pontoDois.Y - tamanhoBateria * distancia);

            path.moveTo(pontoUm.X - (2 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoUm.X  - (2 - distE) * distancia, pontoDois.Y + tamanhoBateria/2 * distancia);
            path.lineTo(pontoUm.X  - (2 - distE) * distancia, pontoDois.Y - tamanhoBateria/2 * distancia);
            path.moveTo(pontoUm.X - (2 - distE) * distancia, pontoUm.Y);
            path.lineTo(pontoDois.X, pontoDois.Y);
        }
        return path;
    }
}

