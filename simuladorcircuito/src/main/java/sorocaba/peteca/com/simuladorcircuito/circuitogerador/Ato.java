package sorocaba.peteca.com.simuladorcircuito.circuitogerador;

import android.graphics.Path;

class Ato {
   public Path pathAto, pathSetas;
   public int numeroAto;

   public Ato(Path pathAto, int numeroAto) {
      pathSetas = new Path();
      this.pathAto = pathAto;
      this.numeroAto = numeroAto;
   }
}
