package sorocaba.peteca.com.simuladorcircuito.circuitogerador;

class Texto {
   float x, y;
   String informacao;

   public Texto(float x, float y, String informacao) {
      this.x = x;
      this.y = y;
      this.informacao = informacao;
   }

   public String getString() {
      return informacao;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }
}
