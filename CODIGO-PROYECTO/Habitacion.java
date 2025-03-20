public abstract class Habitacion {
    private int numero;
    private boolean ocupada;

    public Habitacion(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void reservar() throws ReservaException {
        if (ocupada) {
            throw new ReservaException("La habitación " + numero + " ya está reservada.");
        }
        ocupada = true;
    }

    public void liberar() throws ReservaException {
        if (!ocupada) {
            throw new ReservaException("La habitación " + numero + " ya está libre.");
        }
        ocupada = false;
    }

    public void reservarSinExcepcion() {
        this.ocupada = true;
    }

    public abstract String getTipo();
}
