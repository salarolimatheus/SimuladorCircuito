package sorocaba.peteca.com.simuladorcircuito.graficosgerador;

public class Serie {
    public double[] valor;
    public int beta = 0, max, tamanho;

    public Serie(double[] valorY) {
        this.valor = valorY;
        max = max();
        tamanho = valorY.length;
        this.beta = 0;
    }

    public Serie(double[] valorY, boolean mostrarBeta) {
        this.valor = valorY;
        this.max = max();
        this.tamanho = valorY.length;
        if(mostrarBeta)
            this.beta = beta();
        else
            this.beta = 0;
    }

    private int max() {
        int index = 0;
        double valorMax = Math.abs(valor[0]);
        for (int i = 1; i < valor.length; i++) {
            if (Math.abs(valorMax) < Math.abs(valor[i])) {
                valorMax = Math.abs(valor[i]);
                index = i;
            }
        }
        return index;
    }

    private int beta() {
        for (int i = 1; i < valor.length; i++) {
            if (valor[i] < valor[i - 1] && valor[i] == 0) {
                return i;
            }
        }
        return 0;
    }
}
