package ufjf.dcc196.trb2;

public class TarefaClass {

    private String titulo;
    private String descrição;
    private int grauDificuldade;
    private String dataLimite;
    private String dataUpDate;
    private String estados;
    private String tag;

    public TarefaClass() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public int getGrauDificuldade() {
        return grauDificuldade;
    }

    public void setGrauDificuldade(int grauDificuldade) {
        this.grauDificuldade = grauDificuldade;
    }

    public String getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }

    public String getDataUpDate() {
        return dataUpDate;
    }

    public void setDataUpDate(String dataUpDate) {
        this.dataUpDate = dataUpDate;
    }

    public String getEstados() {
        return estados;
    }

    public void setEstados(String estados) {
        this.estados = estados;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
