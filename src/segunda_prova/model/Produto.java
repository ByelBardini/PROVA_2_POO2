package segunda_prova.model;

public class Produto {
    private int id;
    private String nome;
    private double valor;
    private int estoque;

    public Produto(){
    }
    public Produto(int i, String nm, double vl, int estq){
        this.setId(i);
        this.setNome(nm);
        this.setValor(vl);
        this.setEstoque(estq);
    }

    @Override
    public String toString() {
        return getId()+"-"+getNome()+"-"+getValor()+"-"+getEstoque();
    }

    //Gets e sets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
